package app.domain.dto.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UserSoldWrapperDto implements Serializable {
    @XmlElement(name = "user")
    private List<UserSoldDto> users;

    public UserSoldWrapperDto() {
    }

    public UserSoldWrapperDto(List<UserSoldDto> users) {
        this.users = users;
    }

    public List<UserSoldDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldDto> users) {
        this.users = users;
    }
}
