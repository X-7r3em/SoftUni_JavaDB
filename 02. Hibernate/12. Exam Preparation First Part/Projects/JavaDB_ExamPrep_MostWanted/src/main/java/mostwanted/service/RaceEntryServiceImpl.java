package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceentries.RaceEntryImportDto;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import static mostwanted.common.Constants.*;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private final static String RACE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";

    private final RaceEntryRepository raceEntryRepository;
    private final RacerRepository racerRepository;
    private final CarRepository carRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, RacerRepository racerRepository,
                                CarRepository carRepository, FileUtil fileUtil, XmlParser xmlParser,
                                ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.raceEntryRepository = raceEntryRepository;
        this.racerRepository = racerRepository;
        this.carRepository = carRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException {
        StringBuilder output = new StringBuilder();
        RaceEntryImportRootDto raceEntryImportRootDto =
                this.xmlParser.parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);

        for (RaceEntryImportDto raceEntryImportDto : raceEntryImportRootDto.getRaceEntryImportDtos()) {
            RaceEntry raceEntry = this.modelMapper.map(raceEntryImportDto, RaceEntry.class);
            Racer racer = this.racerRepository.findByName(raceEntryImportDto.getRacerName());
            if (racer == null) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            raceEntry.setRacer(racer);

            Car car = this.carRepository.findById(raceEntryImportDto.getCarId()).orElse(null);
            if (car == null) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            raceEntry.setCar(car);
            raceEntry.setRace(null);

            if (!this.validationUtil.isValid(raceEntry)) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            this.raceEntryRepository.save(raceEntry);
            output.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, raceEntry.getClass().getSimpleName(), raceEntry.getId()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
