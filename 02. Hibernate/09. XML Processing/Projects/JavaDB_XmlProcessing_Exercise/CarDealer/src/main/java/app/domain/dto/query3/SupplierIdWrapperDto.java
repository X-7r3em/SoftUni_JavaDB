package app.domain.dto.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SupplierIdWrapperDto implements Serializable {
    @XmlElement(name = "supplier")
    private List<SupplierIdDto> supplierIdDtos;

    public SupplierIdWrapperDto() {
    }

    public SupplierIdWrapperDto(List<SupplierIdDto> supplierIdDtos) {
        this.supplierIdDtos = supplierIdDtos;
    }

    public List<SupplierIdDto> getSupplierIdDtos() {
        return supplierIdDtos;
    }

    public void setSupplierIdDtos(List<SupplierIdDto> supplierIdDtos) {
        this.supplierIdDtos = supplierIdDtos;
    }
}
