package app.services;

import app.domain.dto.UserDto;
import app.domain.dto.query2.UserSoldDto;
import app.domain.dto.query4.ProductResultQ4Dto;
import app.domain.dto.query4.ProductWrapperDto;
import app.domain.dto.query4.UserResultDto;
import app.domain.dto.query4.WrapperDto;
import app.domain.entities.Product;
import app.domain.entities.User;
import app.repositories.UserRepository;
import app.utilities.ValidatorUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static app.utilities.SystemMessages.ALREADY_SEEDED;
import static app.utilities.SystemMessages.SUCCESSFUL_SEED;


@Service
public class UserServiceImpl implements UserService {
    private final ValidatorUtility validatorUtility;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final Random random;

    @Autowired
    public UserServiceImpl(ValidatorUtility validatorUtility,
                           ModelMapper mapper, UserRepository userRepository, Random random) {
        this.validatorUtility = validatorUtility;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.random = random;
    }

    @Override
    public String seedUsers(UserDto[] usersDto) {
        if (this.userRepository.count() != 0) {
            return String.format(ALREADY_SEEDED, "Users");
        }

        StringBuilder output = new StringBuilder();
        for (UserDto userDto : usersDto) {
            if (!this.validatorUtility.isValid(userDto)) {
                output.append(this.validatorUtility.validationErrors(userDto))
                        .append(System.lineSeparator());
                continue;
            }

            User user = this.mapper.map(userDto, User.class);
            this.userRepository.saveAndFlush(user);
        }

        output.append(String.format(SUCCESSFUL_SEED, "Users"));
        output.append(System.lineSeparator()).append(this.seedFriends());

        return output.toString();
    }

    private String seedFriends() {
        List<User> users = this.userRepository.findAll();

        for (User user : users) {
            Set<User> friends = new HashSet<>();
            int numberOfFriends = 2 + this.random.nextInt(4);
            while (friends.size() < numberOfFriends) {
                User friend = users.get(this.random.nextInt(users.size()));
                friends.add(friend);
            }

            user.setFriends(friends);

            this.userRepository.save(user);
        }

        return String.format(SUCCESSFUL_SEED, "Friends");
    }

    @Override
    public List<UserSoldDto> successfullySoldProducts() {
        List<User> users = this.userRepository.findAllWithProductsSold();
        List<UserSoldDto> userSoldDtos = new ArrayList<>();

        for (User u : users) {
            userSoldDtos.add(this.mapper.map(u, UserSoldDto.class));
        }

        return userSoldDtos;
    }

    @Override
    public WrapperDto usersAndProducts() {
        List<User> users = this.userRepository.usersAndProducts();

        UserResultDto[] userResultDtos = new UserResultDto[users.size()];
        for (int i = 0; i < userResultDtos.length; i++) {
            User user = users.get(i);
            List<Product> soldProducts = new ArrayList<>(user.getSoldProducts());
            ProductResultQ4Dto[] productResultQ4Dtos = new ProductResultQ4Dto[soldProducts.size()];
            for (int j = 0; j < productResultQ4Dtos.length; j++) {
                Product soldProduct = soldProducts.get(j);
                productResultQ4Dtos[j] = new ProductResultQ4Dto(soldProduct.getName(), soldProduct.getPrice());
            }

            ProductWrapperDto productWrapperDto = new ProductWrapperDto(productResultQ4Dtos.length, productResultQ4Dtos);
            userResultDtos[i] = new UserResultDto(user.getFirstName(), user.getLastName(), user.getAge(), productWrapperDto);
        }

        return new WrapperDto(userResultDtos.length, userResultDtos);
    }
}
