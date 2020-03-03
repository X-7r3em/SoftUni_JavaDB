package app.services;

import app.domain.dto.GameAddDto;
import app.domain.entities.Game;
import app.domain.entities.User;
import app.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepository;
    private ModelMapper mapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public String addGame(GameAddDto gameAddDto) {
        Game game = this.mapper.map(gameAddDto, Game.class);

        if (!this.validateGame(game).isEmpty()) {
            return this.validateGame(game);
        }

        this.gameRepository.saveAndFlush(game);
        return "Added " + game.getTitle();
    }

    @Override
    public String editGame(String[] values) {
        Game game = this.gameRepository.findById(Integer.parseInt(values[1])).orElse(null);

        if (game == null) {
            return "No game with such ID.";
        }

        for (int i = 2; i < values.length; i++) {
            String field = values[i].split("=")[0];
            String value = values[i].split("=")[1];
            switch (field) {
                case "title":
                    game.setTitle(value);
                    break;
                case "trailer":
                    game.setTrailer(value);
                    break;
                case "imageThumbnailURL":
                    game.setImageThumbnailURL(value);
                    break;
                case "size":
                    game.setSize(new BigDecimal(value));
                    break;
                case "price":
                    game.setPrice(new BigDecimal(value));
                    break;
                case "description":
                    game.setDescription(value);
                    break;
                case "releaseDate":
                    game.setReleaseDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    break;
            }
        }

        if (!this.validateGame(game).isEmpty()) {
            return this.validateGame(game);
        }

        this.gameRepository.saveAndFlush(game);

        return "Edited " + game.getTitle();
    }

    @Override
    public String deleteGame(String id) {
        Game game = this.gameRepository.findById(Integer.parseInt(id)).orElse(null);

        if (game == null) {
            return "No such game.";
        }

        String name = game.getTitle();
        this.gameRepository.delete(game);

        return "Deleted " + name;
    }

    @Override
    public String viewAllGames() {
        StringBuilder output = new StringBuilder();
        this.gameRepository.findAll()
                .forEach(g -> output.append(String.format("%s %s", g.getTitle(), g.getPrice())).append(System.lineSeparator()));
        return output.toString().isEmpty() ? "No games were found" : output.toString();
    }

    @Override
    public String viewGameDetails(String[] tokens) {
        Game game = this.gameRepository.findByTitle(tokens[1]).orElse(null);

        if (game == null){
            return "No such game";
        }

        return game.toString();
    }

    private String validateGame(Game game) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Game>> violations = validator.validate(game);

        StringBuilder output = new StringBuilder();
        if (violations.size() > 0) {
            for (ConstraintViolation<Game> violation : violations) {
                output.append(violation.getMessage()).append(System.lineSeparator());
            }
        }

        return output.toString();
    }
}
