package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.teamImport.TeamImportDto;
import softuni.exam.domain.dtos.teamImport.TeamImportWrapperDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static softuni.exam.common.Constants.INVALID_MESSAGE;
import static softuni.exam.common.Constants.SUCCESSFUL_MESSAGE;

@Service
public class TeamServiceImpl implements TeamService {
    private static final String TEAM_IMPORT_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\xml\\teams.xml";

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository,
                           FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper,
                           ValidatorUtil validatorUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importTeams() throws JAXBException {
        StringBuilder output = new StringBuilder();
        TeamImportWrapperDto teamImportWrapperDto =
                this.xmlParser.fromXml(TeamImportWrapperDto.class, TEAM_IMPORT_PATH);

        for (TeamImportDto teamDto : teamImportWrapperDto.getTeams()) {
            Team team = this.modelMapper.map(teamDto, Team.class);

            if (teamDto.getPicture() == null
                    || teamDto.getPicture().getUrl() == null) {
                output.append(String.format(INVALID_MESSAGE, team.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            Picture picture = this.pictureRepository.findByUrl(teamDto.getPicture().getUrl());
            if (picture == null) {
                output.append(String.format(INVALID_MESSAGE, team.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            team.setPicture(picture);
            if (!this.validatorUtil.isValid(team)) {
                output.append(String.format(INVALID_MESSAGE, team.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            this.teamRepository.saveAndFlush(team);
            output.append(String.format(SUCCESSFUL_MESSAGE,
                    team.getClass().getSimpleName().toLowerCase(), team.getName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return this.fileUtil.readFile(TEAM_IMPORT_PATH);
    }
}
