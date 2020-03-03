package app.services;

import app.entities.*;
import app.repositories.EmployeeRepository;
import org.aspectj.weaver.ast.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
public class AppController implements CommandLineRunner {
    private ModelMapper mapper;
    private EmployeeRepository employeeRepository;

    public AppController(ModelMapper mapper, EmployeeRepository employeeRepository) {
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.simpleMapping();
//        this.advancedMapping();
//        this.projection();
        this.test();

    }

    private void simpleMapping() {
        Employee employee = new Employee() {{
            setFirstName("Vasil");
            setLastName("Egov");
            setSalary(new BigDecimal("500"));
            setAddress("Sofia");
            setBirthday(LocalDate.parse("2019-01-01"));
        }};

        EmployeeDto employeeDto = this.mapper.map(employee, EmployeeDto.class);
        System.out.println(employeeDto);
    }

    private void advancedMapping() {
        Employee kirilyc = new Employee() {{
            setFirstName("Kirilyc");
            setLastName("Lefi");
            setSalary(new BigDecimal("4400"));
            setBirthday(LocalDate.parse("2000-01-01"));
            setAddress("Sofia");
            setOnHoliday(false);
        }};
        Employee stephen = new Employee() {{
            setFirstName("Stephen");
            setLastName("Bjorn");
            setSalary(new BigDecimal("4300"));
            setBirthday(LocalDate.parse("2000-01-01"));
            setAddress("Sofia");
            setOnHoliday(false);
        }};
        Employee steve = new Employee() {{
            setFirstName("Steve");
            setLastName("Jobbsen");
            setSalary(new BigDecimal("2000"));
            setBirthday(LocalDate.parse("2000-01-01"));
            setAddress("Sofia");
            setOnHoliday(false);
            setEmployees(new ArrayList<>() {{
                add(kirilyc);
                add(stephen);
            }});
        }};

        Employee Jurgen = new Employee() {{
            setFirstName("Jurgen");
            setLastName("Straus");
            setSalary(new BigDecimal("1000.45"));
            setBirthday(LocalDate.parse("2000-01-01"));
            setAddress("Sofia");
            setOnHoliday(false);
        }};
        Employee Moni = new Employee() {{
            setFirstName("Moni");
            setLastName("Kozinac");
            setSalary(new BigDecimal("2030.99"));
            setBirthday(LocalDate.parse("2000-01-01"));
            setAddress("Sofia");
            setOnHoliday(false);
        }};
        Employee Kopp = new Employee() {{
            setFirstName("Kopp");
            setLastName("Spidok");
            setSalary(new BigDecimal("2000.21"));
            setBirthday(LocalDate.parse("2000-01-01"));
            setAddress("Sofia");
            setOnHoliday(false);
        }};

        Employee Carl = new Employee() {{
            setFirstName("Carl");
            setLastName("Kormac");
            setSalary(new BigDecimal("2000"));
            setBirthday(LocalDate.parse("2000-01-01"));
            setAddress("Sofia");
            setOnHoliday(false);
            setEmployees(new ArrayList<>() {{
                add(Moni);
                add(Kopp);
                add(Jurgen);
            }});
        }};

        ManagerDto managerSteve = this.mapper.map(steve, ManagerDto.class);
        System.out.println(managerSteve);
        ManagerDto managerCarl = this.mapper.map(Carl, ManagerDto.class);
        System.out.println(managerCarl);
    }

    private void projection() {
        List<Employee> employeeList = this.employeeRepository.
                findAllByBirthdayBeforeOrderBySalary(LocalDate.parse("1990-01-01"));
        List<EmployeeDto> employeeDtos = employeeList.stream()
                .map(e -> this.mapper.map(e, EmployeeDto.class))
                .collect(Collectors.toList());
        employeeDtos.forEach(System.out::println);
    }

    private void test(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("12.5");
        employeeDto.setLastName("Egov");
        employeeDto.setSalary(new BigDecimal("2"));

        this.mapper.createTypeMap(EmployeeDto.class, TestDto.class).addMappings(m ->
                m.map(EmployeeDto::getFirstName, TestDto::setFirstName)
        );

        TestDto emp = this.mapper.map(employeeDto, TestDto.class);
        System.out.println();

    }
}
