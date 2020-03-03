package application.services;

import application.models.Account;
import application.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;
    private AccountService accountService;


    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            this.accountService.transferMoney(BigDecimal.ONE,1L);
            this.accountService.withdrawMoney(BigDecimal.ONE,2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
