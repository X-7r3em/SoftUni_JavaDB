package app.domain.dto.query1;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CustomerResultIdWrapperDto {

    @XmlElement(name = "customer")
    private List<CustomerResultIdDto> customerResultIdDtos;

    public CustomerResultIdWrapperDto() {
    }

    public CustomerResultIdWrapperDto(List<CustomerResultIdDto> customerResultIdDtos) {
        this.customerResultIdDtos = customerResultIdDtos;
    }

    public List<CustomerResultIdDto> getCustomerResultIdDtos() {
        return customerResultIdDtos;
    }

    public void setCustomerResultIdDtos(List<CustomerResultIdDto> customerResultIdDtos) {
        this.customerResultIdDtos = customerResultIdDtos;
    }
}
