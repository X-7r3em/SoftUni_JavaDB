package app.domain.dto.query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductWrapperDto implements Serializable {
    @XmlAttribute(name = "count")
    private Integer count;

    @XmlElement(name = "product")
    private ProductResultQ4Dto[] products;

    public ProductWrapperDto() {
    }

    public ProductWrapperDto(Integer count, ProductResultQ4Dto[] products) {
        this.count = count;
        this.products = products;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ProductResultQ4Dto[] getProducts() {
        return products;
    }

    public void setProducts(ProductResultQ4Dto[] products) {
        this.products = products;
    }
}
