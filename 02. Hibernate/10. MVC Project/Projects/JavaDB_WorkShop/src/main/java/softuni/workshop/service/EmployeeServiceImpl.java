package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.exportDtos.employeeDto.EmployeeExportDto;
import softuni.workshop.domain.dtos.exportDtos.employeeDto.EmployeeExportRootDto;
import softuni.workshop.domain.dtos.exportDtos.jsonExport.EmployeeExportJsonDto;
import softuni.workshop.domain.dtos.importDtos.employeeDtos.EmployeeDto;
import softuni.workshop.domain.dtos.importDtos.employeeDtos.EmployeeRootDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEES_EXPORT_XML_PATH = "src\\main\\resources\\files\\xmls\\employees.xml";
    private static final String EMPLOYEES_EXPORT_JSON_PATH = "src\\main\\resources\\files\\json\\employees.json";

    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private XmlParser xmlParser;
    private Gson gson;
    private ValidatorUtil validatorUtil;
    private ModelMapper mapper;
    private FileUtil fileUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository,
                               XmlParser xmlParser, Gson gson, ValidatorUtil validatorUtil, ModelMapper mapper, FileUtil fileUtil) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.mapper = mapper;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeRootDto employeeRootDto = this.xmlParser.fromXml(EmployeeRootDto.class, EMPLOYEES_EXPORT_XML_PATH);

        for (EmployeeDto employeeDto : employeeRootDto.getEmployees()) {
            if (!this.validatorUtil.isValid(employeeDto)){
                System.out.println(this.validatorUtil.validationErrors(employeeDto));
                continue;
            }

            Employee employee = this.mapper.map(employeeDto, Employee.class);
            Project project = this.projectRepository.findByName(employeeDto.getProject().getName());

            employee.setProject(project);

            this.employeeRepository.saveAndFlush(employee);
        }
    }


    @Override
    public boolean areImported() {
       return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_EXPORT_XML_PATH);
    }

    @Override
    public String exportEmployeesWithAgeAbove() throws JAXBException {
        List<Employee> employees = this.employeeRepository.findAllByAgeGreaterThan(25);
        StringBuilder output = new StringBuilder();

        EmployeeExportRootDto employeeExportRootDto = new EmployeeExportRootDto();
        employeeExportRootDto.setEmployeeExportDtos(new ArrayList<>());

        for (Employee employee : employees) {
            EmployeeExportDto employeeExportDto = this.mapper.map(employee, EmployeeExportDto.class);
            output.append(employeeExportDto.toString()).append(System.lineSeparator());

            employeeExportRootDto.getEmployeeExportDtos().add(employeeExportDto);
        }

        this.xmlParser.toXml(employeeExportRootDto, "src\\main\\resources\\files\\xmls\\exportEmployees.xml");

        return output.toString();
    }

    @Override
    public void exportJsonEmployees() throws IOException {
        if (this.fileUtil.isFileEmpty(EMPLOYEES_EXPORT_JSON_PATH)){
            return;
        }

        List<Employee> employees = this.employeeRepository.findAll();
        List<EmployeeExportJsonDto> employeeExport = new ArrayList<>();

        for (Employee e : employees) {
            employeeExport.add(this.mapper.map(e, EmployeeExportJsonDto.class));
        }

        FileWriter fileWriter = new FileWriter(EMPLOYEES_EXPORT_JSON_PATH);
        this.gson.toJson(employeeExport, fileWriter);

        fileWriter.close();
    }
}
