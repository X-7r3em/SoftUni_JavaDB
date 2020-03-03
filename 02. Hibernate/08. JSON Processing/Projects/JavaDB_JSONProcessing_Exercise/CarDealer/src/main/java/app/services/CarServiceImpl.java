package app.services;

import app.domain.dto.CarDto;
import app.domain.dto.CarIdDto;
import app.domain.dto.query4.WrapperDto;
import app.domain.entities.Car;
import app.domain.entities.Part;
import app.repositories.CarRepository;
import app.repositories.PartRepository;
import app.utilities.ValidatorUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static app.utilities.SystemMessages.ALREADY_SEEDED;
import static app.utilities.SystemMessages.SUCCESSFUL_SEED;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ValidatorUtility validatorUtility;
    private final ModelMapper mapper;
    private final Random random;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository,
                          ValidatorUtility validatorUtility, ModelMapper mapper, Random random) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.validatorUtility = validatorUtility;
        this.mapper = mapper;
        this.random = random;
    }

    @Override
    @Transactional
    public String seedCars(CarDto[] carDtos) {
        if (this.carRepository.count() != 0) {
            return String.format(ALREADY_SEEDED, "Cars");
        }

        List<Part> parts = this.partRepository.findAll();

        StringBuilder output = new StringBuilder();
        for (CarDto carDto : carDtos) {
            if (!this.validatorUtility.isValid(carDto)) {
                output.append(this.validatorUtility.validationErrors(carDto))
                        .append(System.lineSeparator());
                continue;
            }


            Car car = this.mapper.map(carDto, Car.class);
            car.setParts(new HashSet<>());
            int numberOfParts = 11 + this.random.nextInt(10);
            for (int i = 0; i <  numberOfParts; i++) {
                Part part = parts.get(this.random.nextInt(parts.size()));
                if (car.getParts().contains(part)){
                    i--;
                    continue;
                }

                car.addPart(part);
            }

            this.carRepository.saveAndFlush(car);
        }

        return output.toString() + String.format(SUCCESSFUL_SEED, "Cars");
    }

    @Override
    public List<CarIdDto> findCarsByMake(String make) {
        return this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make).stream()
                .map(c -> this.mapper.map(c, CarIdDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<WrapperDto> findCarsAndParts() {
        List<Car> cars = this.carRepository.findAll();
        List<WrapperDto> wrapperDtos = new ArrayList<>();
        for (Car car : cars) {
            WrapperDto wrapperDto = this.mapper.map(car, WrapperDto.class);
            wrapperDtos.add(wrapperDto);
        }

        return wrapperDtos;
    }
}
