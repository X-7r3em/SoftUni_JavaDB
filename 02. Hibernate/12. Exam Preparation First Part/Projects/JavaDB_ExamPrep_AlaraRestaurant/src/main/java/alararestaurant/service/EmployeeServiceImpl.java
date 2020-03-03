package alararestaurant.service;

import alararestaurant.domain.dtos.JSONimport.EmployeeDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEE_FILE_PATH = "src\\main\\resources\\files\\employees.json";

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository,
                               FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        EmployeeDto[] employeeList = this.gson.fromJson(employees, EmployeeDto[].class);

        StringBuilder output = new StringBuilder();
        for (EmployeeDto employeeDto : employeeList) {
            Employee employee = this.mapper.map(employeeDto, Employee.class);
            if (!this.validationUtil.isValid(employee)) {
                output.append("Invalid data format.")
                        .append(System.lineSeparator());

                continue;
            }

            Position position = this.positionRepository.findByName(employeeDto.getPosition());

            if (position == null) {
                position = new Position();
                position.setName(employeeDto.getPosition());
                if (!this.validationUtil.isValid(position)) {
                    output.append("Invalid data format.")
                            .append(System.lineSeparator());

                    continue;
                }

                this.positionRepository.saveAndFlush(position);
            }

            employee.setPosition(position);

            this.employeeRepository.saveAndFlush(employee);
            output.append(String.format("Record %s successfully imported.", employee.getName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
