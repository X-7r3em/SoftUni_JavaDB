package app.domain.dto.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CarIdWrapperDto {

    @XmlElement(name = "car")
    private List<CarIdDto> cars;

    public CarIdWrapperDto() {
    }

    public CarIdWrapperDto(List<CarIdDto> cars) {
        this.cars = cars;
    }

    public List<CarIdDto> getCars() {
        return cars;
    }

    public void setCars(List<CarIdDto> cars) {
        this.cars = cars;
    }
}
