package app.domain.dto.query4;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class WrapperDto implements Serializable {
    @Expose
    private Integer usersCount;

    @Expose
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
