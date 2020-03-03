package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
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
public class RacerServiceImpl implements RacerService {

    private final static String RACERS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";

    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public RacerServiceImpl(RacerRepository racerRepository,
                            TownRepository townRepository, FileUtil fileUtil, Gson gson,
                            ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder output = new StringBuilder();
        RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);

        for (RacerImportDto racerImportDto : racerImportDtos) {
            Racer racer = this.modelMapper.map(racerImportDto, Racer.class);
            Town town = this.townRepository.findByName(racerImportDto.getHomeTown());
            if (town == null) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            racer.setTown(town);

            if (!this.validationUtil.isValid(racer)) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            if (this.racerRepository.findByName(racer.getName()) != null) {
                output.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            this.racerRepository.saveAndFlush(racer);
            output.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, racer.getClass().getSimpleName(), racer.getName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        StringBuilder output = new StringBuilder("Putki\n");
        List<Racer> racers = this.racerRepository.findAllRacers();
        for (Racer racer : racers) {
            output.append(String.format("Name: %s", racer.getName())).append(System.lineSeparator());
            if (racer.getAge() != null) {
                output.append(String.format("Age: %d", racer.getAge())).append(System.lineSeparator());
            }

            output.append("Cars:").append(System.lineSeparator());
            for (Car car : racer.getCars()) {
                output.append(String.format(" %s %s %d",
                        car.getBrand(), car.getModel(), car.getYearOfProduction()))
                        .append(System.lineSeparator());
            }

            output.append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
