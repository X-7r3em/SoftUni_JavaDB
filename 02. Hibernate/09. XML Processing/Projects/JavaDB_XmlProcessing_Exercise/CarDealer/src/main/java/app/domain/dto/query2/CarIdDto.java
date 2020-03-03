package app.domain.dto.query2;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class CarIdDto {
    @NotNull
    @XmlAttribute(name = "id")
    private Integer id;

    @NotNull
    @XmlAttribute(name = "make")
    private String make;

    @NotNull
    @XmlAttribute(name = "model")
    private String model;

    @NotNull
    @Min(0)
    @XmlAttribute(name = "travelled-distance")
    private Long travelledDistance;

    public CarIdDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
