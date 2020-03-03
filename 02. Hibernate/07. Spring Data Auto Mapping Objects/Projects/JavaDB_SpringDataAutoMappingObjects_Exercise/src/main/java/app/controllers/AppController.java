package app.controllers;

import app.domain.dto.GameAddDto;
import app.domain.dto.UserLoginDto;
import app.domain.dto.UserRegisterDto;
import app.domain.entities.Game;
import app.domain.entities.User;
import app.repositories.UserRepository;
import app.services.GameService;
import app.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
public class AppController implements CommandLineRunner {
    private UserService userService;
    private GameService gameService;
    private BufferedReader br;

    @Autowired
    public AppController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String[] tokens = this.br.readLine().split("\\|");
            String cmd = tokens[0];
            switch (cmd) {
                case "RegisterUser":
                    UserRegisterDto userRegisterDto =
                            new UserRegisterDto(tokens[1], tokens[2], tokens[3], tokens[4]);
                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    UserLoginDto userLoginDto =
                            new UserLoginDto(tokens[1], tokens[2]);
                    System.out.println(this.userService.loginUser(userLoginDto));
                    break;
                case "Logout":
                    System.out.println(this.userService.logoutUser());
                    break;
                case "AddGame":
                    if (!this.userService.isUserAdmin()) {
                        System.out.println("User is not admin.");
                        break;
                    }

                    GameAddDto gameAddDto = new GameAddDto(tokens[1], new BigDecimal(tokens[2]),
                            new BigDecimal(tokens[3]), tokens[4], tokens[5], tokens[6],
                            LocalDate.parse(tokens[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                    System.out.println(this.gameService.addGame(gameAddDto));
                    break;

                case "EditGame":
                    if (!this.userService.isUserAdmin()) {
                        System.out.println("User is not admin.");
                        break;
                    }

                    System.out.println(this.gameService.editGame(tokens));
                    break;
                case "DeleteGame":
                    if (!this.userService.isUserAdmin()) {
                        System.out.println("User is not admin.");
                        break;
                    }

                    System.out.println(this.gameService.deleteGame(tokens[1]));

                    break;
                case "AllGames":
                    System.out.println(this.gameService.viewAllGames());
                    break;
                case "DetailGame":
                    System.out.println(this.gameService.viewGameDetails(tokens));
                    break;
                case "OwnedGames":
                    System.out.println(this.userService.viewAllOwnedGames());
                    break;
                case "AddItem":
                    System.out.println(this.userService.addItem(tokens));
                    break;
                case "RemoveItem":
                    System.out.println(this.userService.removeItem(tokens));
                    break;
                case "BuyItem":
                    System.out.println(this.userService.buyItem());
                    break;
            }
        }
    }
}
