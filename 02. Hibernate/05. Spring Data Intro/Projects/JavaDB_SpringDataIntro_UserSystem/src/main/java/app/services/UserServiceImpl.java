package app.services;

import app.entities.User;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsersWithEmailProvider(String pattern) {
        return this.userRepository.findAllByEmailEndingWith(pattern);
    }

    @Override
    public int deactivateUsers(LocalDate date) {
        List<User> users = this.userRepository.findUserByLastTimeLoggedInBefore(date);
        users.forEach(u -> {
            u.setDeleted(true);
            this.userRepository.save(u);
        });


        return users.size();
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }
}
