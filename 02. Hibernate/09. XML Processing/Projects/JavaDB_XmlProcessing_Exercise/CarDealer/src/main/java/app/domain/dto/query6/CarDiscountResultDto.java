package app.domain.dto.query6;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.math.BigInteger;

@XmlAccessorType(value = XmlAccessType.FIELD)
public class CarDiscountResultDto implements Serializable {
    @XmlAttribute(name = "make")
    private String make;
    @XmlAttribute(name = "model")
    private String model;
    @XmlAttribute(name = "travel-distance")
    private BigInteger travelDistance;

    public CarDiscountResultDto() {
    }

    public CarDiscountResultDto(String make, String model, BigInteger travelDistance) {
        this.make = make;
        this.model = model;
        this.travelDistance = travelDistance;
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

    public BigInteger getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(BigInteger travelDistance) {
        this.travelDistance = travelDistance;
    }
}
