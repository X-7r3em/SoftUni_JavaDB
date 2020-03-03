package app.domain.dto.supplierimport;


import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SupplierWrapperDto implements Serializable {
    @XmlElement(name = "supplier")
    private List<SupplierDto> suppliers;

    public SupplierWrapperDto() {
    }

    public List<SupplierDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierDto> suppliers) {
        this.suppliers = suppliers;
    }
}
