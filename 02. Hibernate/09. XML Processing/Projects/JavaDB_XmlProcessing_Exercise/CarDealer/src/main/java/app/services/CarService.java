package app.services;

import app.domain.dto.carimport.CarDto;
import app.domain.dto.query2.CarIdWrapperDto;
import app.domain.dto.query4.ListWrapperDto;

import java.util.List;

public interface CarService {
    String seedCars(List<CarDto> carDtos);

    CarIdWrapperDto findCarsByMake(String make);

    ListWrapperDto findCarsAndParts();
}
