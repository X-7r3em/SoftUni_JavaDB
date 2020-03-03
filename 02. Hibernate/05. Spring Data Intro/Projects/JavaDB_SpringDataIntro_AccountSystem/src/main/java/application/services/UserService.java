package application.services;

import application.models.User;

public interface UserService {
    void registerUser(User user);
    User findUserByUsername(String username);
}
