package app.domain.dto.query4;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "users")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class WrapperDto implements Serializable {

    @XmlAttribute(name = "count")
    private Integer usersCount;

    @XmlElement(name = "user")
    private UserResultDto[] users;

    public WrapperDto() {
    }

    public WrapperDto(Integer usersCount, UserResultDto[] users) {
        this.usersCount = usersCount;
        this.users = users;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public UserResultDto[] getUsers() {
        return users;
    }

    public void setUsers(UserResultDto[] users) {
        this.users = users;
    }
}
