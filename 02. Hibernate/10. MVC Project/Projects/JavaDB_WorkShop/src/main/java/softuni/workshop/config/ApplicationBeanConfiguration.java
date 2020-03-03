package softuni.workshop.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import softuni.workshop.domain.dtos.exportDtos.employeeDto.EmployeeExportDto;
import softuni.workshop.domain.dtos.importDtos.projectDtos.ProjectDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.util.*;

import java.time.LocalDate;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<String, LocalDate> stringLocalDateConverter = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String date) {
                return LocalDate.parse(date);
            }
        };

        Converter<Employee, String> employeeFullNameConverter = new AbstractConverter<Employee, String>() {
            @Override
            protected String convert(Employee employee) {
                return employee.getFirstName() + " " + employee.getLastName();
            }

        };

        mapper.addMappings(new PropertyMap<ProjectDto, Project>() {
            @Override
            protected void configure() {
                using(stringLocalDateConverter).map(source.getStartDate()).setStartDate(null);
            }
        });

        mapper.addMappings(new PropertyMap<Employee, EmployeeExportDto>() {
            @Override
            protected void configure() {
                using(employeeFullNameConverter).map(source).setFullName(null);
                map().setProject(source.getProject().getName());
            }
        });

        return mapper;
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl();
    }

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }
}
