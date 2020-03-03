package softuni.workshop.domain.dtos.exportDtos.employeeDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class EmployeeExportDto implements Serializable {

    @XmlElement(name = "full-name")
    private String fullName;

    @XmlElement(name = "age")
    private String age;

    @XmlElement(name = "project")
    private String project;

    public EmployeeExportDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return String.format("Name: %s%n" +
                "    Age: %s%n" +
                "   Project Name: %s",
                this.getFullName(), this.getAge(), this.getProject());
    }
}
