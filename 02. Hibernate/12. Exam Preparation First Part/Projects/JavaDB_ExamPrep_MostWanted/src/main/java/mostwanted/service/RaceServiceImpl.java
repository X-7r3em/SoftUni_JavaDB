package mostwanted.service;

import mostwanted.domain.dtos.races.EntryImportDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static mostwanted.common.Constants.INCORRECT_DATA_MESSAGE;
import static mostwanted.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class RaceServiceImpl implements RaceService {

    private final static String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";

    private final RaceRepository raceRepository;
    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, DistrictRepository districtRepository,
                           RaceEntryRepository raceEntryRepository, FileUtil fileUtil,
                           XmlParser xmlParser, ModelMapper modelMapper,
                           ValidationUtil validationUtil) {
        this.raceRepository = raceRepository;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACES_XML_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException {
        StringBuilder output = new StringBuilder();
        RaceImportRootDto raceImportRootDto =
                this.xmlParser.parseXml(RaceImportRootDto.class, RACES_XML_FILE_PATH);

        outer:
        for (RaceImportDto raceDto : raceImportRootDto.getRaces()) {
            Race race = this.modelMapper.map(raceDto, Race.class);
            District district = this.districtRepository.findByName(raceDto.getDistrictName());
            if (district == null) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            race.setDistrict(district);

            Set<RaceEntry> entries = new HashSet<>();
            for (EntryImportDto entry : raceDto.getEntries().getEntries()) {
                RaceEntry raceEntry = this.raceEntryRepository.findById(entry.getId()).orElse(null);
                if (raceEntry == null) {
                    output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue outer;
                }

                entries.add(raceEntry);
            }

            if (!this.validationUtil.isValid(race)) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            this.raceRepository.saveAndFlush(race);
            for (RaceEntry entry : entries) {
                entry.setRace(race);
                this.raceEntryRepository.saveAndFlush(entry);
            }

            output.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, race.getClass().getSimpleName(), race.getId()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}