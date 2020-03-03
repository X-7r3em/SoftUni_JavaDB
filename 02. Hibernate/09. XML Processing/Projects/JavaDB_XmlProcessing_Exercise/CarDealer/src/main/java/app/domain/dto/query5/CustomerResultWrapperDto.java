package app.domain.dto.query5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CustomerResultWrapperDto implements Serializable {
    @XmlElement(name = "customer")
    private List<CustomerResultDto> customers;

    public CustomerResultWrapperDto() {
    }

    public CustomerResultWrapperDto(List<CustomerResultDto> customers) {
        this.customers = customers;
    }

    public List<CustomerResultDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerResultDto> customers) {
        this.customers = customers;
    }
}
