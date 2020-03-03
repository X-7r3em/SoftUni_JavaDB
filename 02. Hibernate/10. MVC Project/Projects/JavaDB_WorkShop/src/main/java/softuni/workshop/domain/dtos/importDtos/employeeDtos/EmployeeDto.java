package softuni.workshop.domain.dtos.importDtos.employeeDtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class EmployeeDto implements Serializable {

    @XmlElement(name = "first-name")
    @Size(min = 3, max = 15, message = "First name is not in specified range.")
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 3, max = 15, message = "Last name is not in specified range.")
    private String lastName;

    @XmlElement(name = "age")
    @Min(0)
    private Integer age;

    @XmlElement(name = "project")
    private ProjectDto project;

    public EmployeeDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }
}
