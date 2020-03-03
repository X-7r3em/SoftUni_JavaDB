package softuni.workshop.domain.dtos.importDtos.employeeDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class EmployeeRootDto implements Serializable {
    @XmlElement(name = "employee")
    private List<EmployeeDto> employees;

    public EmployeeRootDto() {
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }
}
