package app.services;

import app.domain.dto.GameAddDto;
import org.springframework.stereotype.Service;

public interface GameService {
    String addGame(GameAddDto gameAddDto);

    String editGame(String[] values);

    String deleteGame(String id);

    String viewAllGames();

    String viewGameDetails(String[] tokens);
}
