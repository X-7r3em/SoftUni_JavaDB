package softuni.workshop.domain.dtos.importDtos.projectDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProjectRootDto {
    @XmlElement(name = "project")
    private List<ProjectDto> companies;

    public ProjectRootDto() {
    }

    public List<ProjectDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<ProjectDto> companies) {
        this.companies = companies;
    }
}
