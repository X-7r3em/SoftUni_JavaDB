package app.services;

import app.domain.dto.UserDto;
import app.domain.dto.query2.UserSoldDto;
import app.domain.dto.query4.WrapperDto;

import java.util.List;

public interface UserService {
    String seedUsers(UserDto[] usersDto);

    List<UserSoldDto> successfullySoldProducts();

    WrapperDto usersAndProducts();

}
