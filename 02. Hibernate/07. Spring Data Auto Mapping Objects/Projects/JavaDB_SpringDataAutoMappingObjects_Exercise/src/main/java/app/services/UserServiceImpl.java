package app.services;

import app.domain.dto.UserLoginDto;
import app.domain.dto.UserRegisterDto;
import app.domain.entities.Game;
import app.domain.entities.Role;
import app.domain.entities.User;
import app.repositories.GameRepository;
import app.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private GameRepository gameRepository;
    private ModelMapper mapper;
    private User loggedUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        User user = this.mapper.map(userRegisterDto, User.class);

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            return "Passwords do not match";
        }

        if (this.userRepository.findUserByEmail(user.getEmail()).orElse(null) != null) {
            return "Email is taken.";
        }

        StringBuilder output = new StringBuilder();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (violations.size() > 0) {
            for (ConstraintViolation<User> violation : violations) {
                output.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else {
            output.append(String.format("%s was registered", user.getFullName()));
            if (this.userRepository.count() != 0) {
                user.setRole(Role.USER);
            } else {
                user.setRole(Role.ADMIN);
            }

            this.userRepository.saveAndFlush(user);
        }

        return output.toString();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        if (this.loggedUser != null) {
            return "There is somebody logged in already";
        }

        User user = this.userRepository.findUserByEmail(userLoginDto.getEmail()).orElse(null);

        if (user == null) {
            return "No registered user.";
        }

        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            return "Incorrect password";
        }

        this.loggedUser = user;
        return String.format("Successfully logged in %s", user.getFullName());
    }

    @Override
    public String logoutUser() {
        if (this.loggedUser == null) {
            return "Cannot log out. No user was logged in.";
        }

        String name = this.loggedUser.getFullName();

        this.loggedUser = null;

        return String.format("User %s successfully logged out", name);
    }

    @Override
    public String addItem(String[] tokens) {
        Game game = this.gameRepository.findByTitle(tokens[1]).orElse(null);

        if (game == null){
            return "No such game";
        }

        if (this.loggedUser.getShoppingCart().contains(game)){
            return "Already has the game in shopping cart.";
        }

        if (this.loggedUser.getGames().contains(game)){
            return "Already have the game.";
        }

        this.loggedUser.getShoppingCart().add(game);

        return String.format("%s added to cart.", game.getTitle());
    }

    @Override
    public String removeItem(String[] tokens) {
        Game game = this.gameRepository.findByTitle(tokens[1]).orElse(null);

        if (game == null){
            return "No such game";
        }

        if (!this.loggedUser.getShoppingCart().contains(game)){
            return "No such game in shopping cart.";
        }

        this.loggedUser.getShoppingCart().remove(game);

        return String.format("%s removed from cart.", game.getTitle());
    }

    @Override
    @Transactional
    public String buyItem() {
        Set<Game> cart = this.loggedUser.getShoppingCart();
        if (cart.isEmpty()){
            return "No games in shopping cart.";
        }

        StringBuilder output = new StringBuilder();
        output.append("Successfully bought games:");
        cart.forEach(g -> output.append(System.lineSeparator()).append(String.format(" -%s", g.getTitle())));

        this.loggedUser.getGames().addAll(cart);

        cart.clear();

        this.userRepository.saveAndFlush(this.loggedUser);

        return output.toString();
    }

    @Override
    public String viewAllOwnedGames() {
        if (this.loggedUser == null){
            return "No logged in user.";
        }

        StringBuilder output = new StringBuilder();
        this.loggedUser.getGames().forEach(g -> output.append(g.getTitle()).append(System.lineSeparator()));
        return output.toString().trim();
    }

    @Override
    public boolean isUserAdmin() {
        return this.loggedUser != null && this.loggedUser.getRole().equals(Role.ADMIN);
    }
}
