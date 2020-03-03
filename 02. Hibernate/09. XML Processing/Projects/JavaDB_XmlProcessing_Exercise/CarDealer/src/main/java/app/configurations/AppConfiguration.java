package app.configurations;

import app.domain.dto.customerimport.CustomerDto;
import app.domain.entities.Customer;
import app.utilities.ValidatorUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
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

        Converter<CustomerDto, LocalDateTime> stringLocalDateTimeConverter = new AbstractConverter<CustomerDto, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(CustomerDto c) {
                return LocalDateTime.parse(c.getBirthDate());
            }
        };

        mapper.addMappings(new PropertyMap<CustomerDto, Customer>() {
            @Override
            protected void configure() {
                using(stringLocalDateTimeConverter).map(source).setBirthDate(null);
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
