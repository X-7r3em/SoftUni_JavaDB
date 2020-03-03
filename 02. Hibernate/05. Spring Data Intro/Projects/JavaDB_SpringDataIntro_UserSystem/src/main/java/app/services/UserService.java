package app.services;

import app.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<User> findAllUsersWithEmailProvider(String pattern);
    int deactivateUsers(LocalDate date);
    void save(User user);
}
