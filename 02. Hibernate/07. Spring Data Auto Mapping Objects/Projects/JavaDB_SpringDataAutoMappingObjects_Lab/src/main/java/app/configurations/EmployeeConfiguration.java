package app.configurations;

import app.entities.Employee;
import app.entities.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfiguration {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(Employee.class, EmployeeDto.class).addMappings(
                new PropertyMap<Employee, EmployeeDto>() {
                    @Override
                    protected void configure() {
                        map().setFirstName(source.getFirstName());
                        map().setLastName(source.getLastName());
                        map().setSalary(source.getSalary());
                    }
                }
        );

        return mapper;
    }
}
