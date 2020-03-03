package app.domain.dto.query6;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigInteger;

public class CarDiscountResultDto implements Serializable {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
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
