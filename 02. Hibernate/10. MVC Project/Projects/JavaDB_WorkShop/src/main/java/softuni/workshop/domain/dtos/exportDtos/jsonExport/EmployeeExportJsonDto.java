package softuni.workshop.domain.dtos.exportDtos.jsonExport;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class EmployeeExportJsonDto implements Serializable {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer age;

    @Expose
    private ProjectExportJsonDto project;

    public EmployeeExportJsonDto() {
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

    public ProjectExportJsonDto getProject() {
        return project;
    }

    public void setProject(ProjectExportJsonDto project) {
        this.project = project;
    }
}
