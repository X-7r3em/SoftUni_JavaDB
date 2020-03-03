package app.domain.dto.query6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SaleResultWrapperDto implements Serializable {
    @XmlElement(name = "sale")
    private List<SaleResultDto> sales;

    public SaleResultWrapperDto() {
    }

    public SaleResultWrapperDto(List<SaleResultDto> sales) {
        this.sales = sales;
    }

    public List<SaleResultDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleResultDto> sales) {
        this.sales = sales;
    }
}
