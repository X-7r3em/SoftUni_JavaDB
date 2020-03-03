package app.services;

import app.domain.dto.UserImport.UserDto;
import app.domain.dto.query2.UserSoldDto;
import app.domain.dto.query2.UserSoldWrapperDto;
import app.domain.dto.query4.WrapperDto;

import java.util.List;

public interface UserService {
    String seedUsers(List<UserDto> usersDto);

    UserSoldWrapperDto successfullySoldProducts();

    WrapperDto usersAndProducts();

}
