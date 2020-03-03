package app.services;

import app.domain.dto.UserLoginDto;
import app.domain.dto.UserRegisterDto;
import app.domain.entities.User;

public interface UserService {
    String registerUser(UserRegisterDto userRegisterDto);

    String loginUser(UserLoginDto userLoginDto);

    String logoutUser();

    boolean isUserAdmin();

    String addItem(String[] tokens);

    String removeItem(String[] tokens);

    String buyItem();

    String viewAllOwnedGames();
}
