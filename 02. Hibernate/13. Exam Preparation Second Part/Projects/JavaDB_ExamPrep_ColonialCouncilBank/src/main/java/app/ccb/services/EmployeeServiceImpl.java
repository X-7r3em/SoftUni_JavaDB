package app.ccb.services;

import app.ccb.domain.dtos.employeeImport.EmployeeDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static app.ccb.common.Constants.ERROR_MESSAGE;
import static app.ccb.common.Constants.SUCCESS_MESSAGE;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEE_JSON_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\employees.json";

    private EmployeeRepository employeeRepository;
    private BranchRepository branchRepository;
    private FileUtil fileUtil;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchRepository branchRepository, FileUtil fileUtil,
                               Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return fileUtil.readFile(EMPLOYEE_JSON_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder output = new StringBuilder();
        EmployeeDto[] employeeDtos = gson.fromJson(employees, EmployeeDto[].class);
        for (EmployeeDto employeeDto : employeeDtos) {
            Employee employee = modelMapper.map(employeeDto, Employee.class);

            Branch branch = branchRepository.findByName(employeeDto.getBranchName());

            if (branch == null) {
                output.append(ERROR_MESSAGE).append(System.lineSeparator());
                continue;
            }

            employee.setBranch(branch);

            if (!validationUtil.isValid(employee)) {
                output.append(ERROR_MESSAGE).append(System.lineSeparator());
                continue;
            }

            employeeRepository.saveAndFlush(employee);
            output.append(String.format(SUCCESS_MESSAGE,
                    employee.getClass().getSimpleName(), employee.getFirstName() + " " + employee.getLastName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    @Override
    public String exportTopEmployees() {
        List<Employee> employees = employeeRepository.findAllEmployees();
        StringBuilder output = new StringBuilder();
        for (Employee e : employees) {
            output.append("Full Name: ").append(e.getFirstName())
                    .append(" ").append(e.getLastName()).append(System.lineSeparator())
                    .append("Salary: ").append(e.getSalary()).append(System.lineSeparator())
                    .append("Started On: ").append(e.getStartedOn()).append(System.lineSeparator())
                    .append("Clients: ").append(System.lineSeparator());

            for (Client c : e.getClients()) {
                output.append("    ").append(c.getFullName()).append(System.lineSeparator());
            }

            output.append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
