package softuni.workshop.domain.dtos.exportDtos.employeeDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class EmployeeExportRootDto implements Serializable {
    @XmlElement(name = "employee")
    private List<EmployeeExportDto> employeeExportDtos;

    public List<EmployeeExportDto> getEmployeeExportDtos() {
        return employeeExportDtos;
    }

    public void setEmployeeExportDtos(List<EmployeeExportDto> employeeExportDtos) {
        this.employeeExportDtos = employeeExportDtos;
    }
}
