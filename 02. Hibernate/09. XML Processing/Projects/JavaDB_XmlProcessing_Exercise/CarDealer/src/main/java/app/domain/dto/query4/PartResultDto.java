package app.domain.dto.query4;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class PartResultDto implements Serializable {
    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "price")
    private Double price;

    public PartResultDto() {
    }

    public PartResultDto(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
