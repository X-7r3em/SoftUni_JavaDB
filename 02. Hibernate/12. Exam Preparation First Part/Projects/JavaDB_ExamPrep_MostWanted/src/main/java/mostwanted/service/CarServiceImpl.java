package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class CarServiceImpl implements CarService {

    private final static String CARS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/cars.json";

    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil,
                          Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder output = new StringBuilder();
        CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);
        for (CarImportDto carImportDto : carImportDtos) {
            Car car = this.modelMapper.map(carImportDto, Car.class);

            Racer racer = this.racerRepository.findByName(carImportDto.getRacerName());
            if (racer == null) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            car.setRacer(racer);

            if (!this.validationUtil.isValid(car)) {
                output.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            this.carRepository.save(car);
            String carInfo = String.format("%s %s @ %s",
                    car.getBrand(), car.getModel(), car.getYearOfProduction());
            output.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, car.getClass().getSimpleName(), carInfo))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
