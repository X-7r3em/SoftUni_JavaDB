package app.domain.dto.query4;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class WrapperDto implements Serializable {
    @Expose
    private CarResultDto car;

    @Expose
    private List<PartResultDto> parts;

    public WrapperDto() {
    }

    public CarResultDto getCar() {
        return car;
    }

    public void setCar(CarResultDto car) {
        this.car = car;
    }

    public List<PartResultDto> getParts() {
        return parts;
    }

    public void setParts(List<PartResultDto> parts) {
        this.parts = parts;
    }
}