package app;

import app.entities.User;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private UserService userService;
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyy");
        LocalDate date = LocalDate.parse("12 Jun 2015", formatter);
        user.setDeleted(false);
        user.setFirstName("Tosho");
        user.setLastName("Gotosh");
        user.setAge(15);
        user.setEmail("Dasbl@gmail.com");
        user.setLastTimeLoggedIn(date);
        user.setRegisteredOn(date);
        user.setUsername("Username");
        user.setPassword("1bbbbbA@");

//        this.userService.save(user);
//        this.getUserByEmailProvider();
        this.removeInactiveUsers();
    }

    private void getUserByEmailProvider() {
        this.userService.findAllUsersWithEmailProvider(this.scanner.nextLine())
                .forEach(u -> System.out.println(u.getEmail()));
    }

    private void removeInactiveUsers() throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyy");
        LocalDate date = LocalDate.parse(this.scanner.nextLine(), formatter);
        int users = this.userService.deactivateUsers(date);

        if (users == 0) {
            System.out.println("No users have been deleted");
        } else {
            System.out.printf("%d user%s has been deleted%n", users, users > 1 ? "s" : "");
        }
    }
}
