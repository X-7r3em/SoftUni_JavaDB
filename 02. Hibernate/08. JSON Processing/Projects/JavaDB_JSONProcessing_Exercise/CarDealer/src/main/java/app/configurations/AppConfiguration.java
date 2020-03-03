package app.configurations;

import app.domain.dto.query4.CarResultDto;
import app.domain.dto.query4.WrapperDto;
import app.domain.dto.query5.CustomerResultDto;
import app.domain.entities.Car;
import app.utilities.ValidatorUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

@Configuration
public class AppConfiguration {

    @Bean
    public Gson initializeGSON() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper initializeModelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<Car, CarResultDto> carDtoConverter = new AbstractConverter<Car, CarResultDto>() {
            @Override
            protected CarResultDto convert(Car car) {
                CarResultDto carResultDto = new CarResultDto();
                carResultDto.setMake(car.getMake());
                carResultDto.setModel(car.getModel());
                carResultDto.setTravelledDistance(car.getTravelledDistance());

                return carResultDto;
            }
        };

        mapper.addMappings(new PropertyMap<Car, WrapperDto>() {
            @Override
            protected void configure() {
                using(carDtoConverter).map(source).setCar(null);
            }
        });

        return mapper;
    }

    @Bean
    public ValidatorUtility initializeValidatorUtility() {
        return new ValidatorUtility();
    }

    @Bean
    public Random initializeRandom() {
        return new Random();
    }

    @Bean
    public BufferedReader initializeBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
