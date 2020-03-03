package app.domain.dto.query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ListWrapperDto implements Serializable {
    @XmlElement(name = "car")
    private List<WrapperDto> cars;

    public ListWrapperDto() {
    }

    public ListWrapperDto(List<WrapperDto> cars) {
        this.cars = cars;
    }

    public List<WrapperDto> getCars() {
        return cars;
    }

    public void setCars(List<WrapperDto> cars) {
        this.cars = cars;
    }
}
