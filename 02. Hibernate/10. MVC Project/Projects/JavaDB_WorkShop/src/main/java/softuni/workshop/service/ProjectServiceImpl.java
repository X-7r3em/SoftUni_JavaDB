package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.exportDtos.jsonExport.ProjectExportJsonDto;
import softuni.workshop.domain.dtos.exportDtos.projectDtos.ProjectExportDto;
import softuni.workshop.domain.dtos.importDtos.projectDtos.ProjectDto;
import softuni.workshop.domain.dtos.importDtos.projectDtos.ProjectRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
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
public class ProjectServiceImpl implements ProjectService {
    private static final String PROJECTS_EXPORT_XML_PATH = "src\\main\\resources\\files\\xmls\\projects.xml";
    private static final String PROJECTS_EXPORT_JSON_PATH = "src\\main\\resources\\files\\json\\projects.json";

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, ValidatorUtil validatorUtil, XmlParser xmlParser, ModelMapper mapper, FileUtil fileUtil, Gson gson) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public void importProjects() throws JAXBException {
        ProjectRootDto projectRootDto = this.xmlParser.fromXml(ProjectRootDto.class, PROJECTS_EXPORT_XML_PATH);

        for (ProjectDto projectDto : projectRootDto.getCompanies()) {
            if (!this.validatorUtil.isValid(projectDto)) {
                System.out.println(this.validatorUtil.validationErrors(projectDto));
                continue;
            }

            Project project = this.mapper.map(projectDto, Project.class);
            Company company = this.companyRepository.findByName(projectDto.getCompany().getName());
            project.setCompany(company);

            this.projectRepository.saveAndFlush(project);
        }
    }

    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return this.fileUtil.readFile(PROJECTS_EXPORT_XML_PATH);
    }

    @Override
    public String exportFinishedProjects() {
        List<Project> projects = this.projectRepository.findByFinishedTrue();
        StringBuilder output = new StringBuilder();

        for (Project project : projects) {
            output.append(this.mapper.map(project, ProjectExportDto.class)).append(System.lineSeparator());
        }

        return output.toString();
    }

    @Override
    public void exportJsonCompanies() throws IOException {
        if (this.fileUtil.isFileEmpty(PROJECTS_EXPORT_JSON_PATH)){
            return;
        }

        List<Project> projects = this.projectRepository.findAll();
        List<ProjectExportJsonDto> projectsExport = new ArrayList<>();

        for (Project p : projects) {
            projectsExport.add(this.mapper.map(p, ProjectExportJsonDto.class));
        }

        FileWriter fileWriter = new FileWriter(PROJECTS_EXPORT_JSON_PATH);
        this.gson.toJson(projectsExport, fileWriter);

        fileWriter.close();
    }
}
