package app.services;

import app.domain.dto.CarDto;
import app.domain.dto.CarIdDto;
import app.domain.dto.query4.WrapperDto;

import java.util.List;

public interface CarService {
    String seedCars(CarDto[] carDtos);

    List<CarIdDto> findCarsByMake(String make);

    List<WrapperDto> findCarsAndParts();
}
