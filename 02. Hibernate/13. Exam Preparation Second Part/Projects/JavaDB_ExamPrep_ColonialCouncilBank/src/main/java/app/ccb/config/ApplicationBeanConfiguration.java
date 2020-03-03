package app.ccb.config;

import app.ccb.domain.dtos.bankAccountImport.BankAccountDto;
import app.ccb.domain.dtos.clientImport.ClientDto;
import app.ccb.domain.dtos.employeeImport.EmployeeDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDate> stringLocalDateConverter = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String s) {
                return LocalDate.parse(s);
            }
        };

        Converter<String, String> fullNameToFirstName = new AbstractConverter<String, String>() {
            @Override
            protected String convert(String s) {
                return s.substring(0, s.indexOf(" "));
            }
        };

        Converter<String, String> fullNameToLastName = new AbstractConverter<String, String>() {
            @Override
            protected String convert(String s) {
                return s.substring(s.indexOf(" ") + 1);
            }
        };

        Converter<ClientDto, String> firstAndLastToFullName = new AbstractConverter<ClientDto, String>() {
            @Override
            protected String convert(ClientDto c) {
                return c.getFirstName() + " " + c.getLastName();
            }
        };

        Converter<String, BigDecimal> stringBigDecimalConverter = new AbstractConverter<String, BigDecimal>() {
            @Override
            protected BigDecimal convert(String s) {
                return new BigDecimal(s);
            }
        };

        modelMapper.addMappings(new PropertyMap<EmployeeDto, Employee>() {
            @Override
            protected void configure() {
                using(fullNameToFirstName).map(source.getFullName()).setFirstName(null);
                using(fullNameToLastName).map(source.getFullName()).setLastName(null);
                using(stringLocalDateConverter).map(source.getStartedOn()).setStartedOn(null);
            }
        });

        modelMapper.addMappings(new PropertyMap<BankAccountDto,BankAccount>() {
            @Override
            protected void configure() {
                using(stringBigDecimalConverter).map(source.getBalance()).setBalance(null);
            }
        });

        modelMapper.addMappings(new PropertyMap<ClientDto, Client>() {
            @Override
            protected void configure() {
                using(firstAndLastToFullName).map(source).setFullName(null);
            }
        });

        return modelMapper;
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting().create();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}
