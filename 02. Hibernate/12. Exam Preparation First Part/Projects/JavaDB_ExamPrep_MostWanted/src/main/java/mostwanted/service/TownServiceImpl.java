package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static mostwanted.common.Constants.*;


@Service
public class TownServiceImpl implements TownService {
    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";

    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder output = new StringBuilder();
        TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent, TownImportDto[].class);

        for (TownImportDto townImportDto : townImportDtos) {
            Town town = this.modelMapper.map(townImportDto, Town.class);
            if (!this.validationUtil.isValid(town)) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            if (this.townRepository.findByName(town.getName()) != null) {
                output.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            this.townRepository.saveAndFlush(town);
            output.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                    town.getClass().getSimpleName(), town.getName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        StringBuilder output = new StringBuilder();
        List<Town> towns = this.townRepository.findAllTowns();
        for (Town town : towns) {
            output.append(String.format("Name: %s%nRacers: %d", town.getName(), town.getRacers().size()))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
