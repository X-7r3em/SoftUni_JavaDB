package app.domain.dto.query1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductWrapperResultDto implements Serializable {
    @XmlElement(name = "product")
    private List<ProductResultDto> productResultDtos;

    public ProductWrapperResultDto() {
    }

    public ProductWrapperResultDto(List<ProductResultDto> productResultDtos) {
        this.productResultDtos = productResultDtos;
    }

    public List<ProductResultDto> getProductResultDtos() {
        return productResultDtos;
    }

    public void setProductResultDtos(List<ProductResultDto> productResultDtos) {
        this.productResultDtos = productResultDtos;
    }
}
