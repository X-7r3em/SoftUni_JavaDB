package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.playerImport.PlayerImportDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static softuni.exam.common.Constants.INVALID_MESSAGE;
import static softuni.exam.common.Constants.SUCCESSFUL_MESSAGE;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYER_IMPORT_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\players.json ";
    private static final String TEAM_NAME = "North Hub";
    private static final BigDecimal MINIMUM_SALARY = new BigDecimal("100000");

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository,
                             PictureRepository pictureRepository, FileUtil fileUtil,
                             Gson gson, ModelMapper modelMapper,
                             ValidatorUtil validatorUtil) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPlayers() throws IOException {
        String jsonAsText = this.readPlayersJsonFile();
        StringBuilder output = new StringBuilder();
        PlayerImportDto[] playerImportDtos = this.gson.fromJson(jsonAsText, PlayerImportDto[].class);

        for (PlayerImportDto playerImportDto : playerImportDtos) {
            Player player = this.modelMapper.map(playerImportDto, Player.class);
            if (playerImportDto.getPicture() == null
                    || playerImportDto.getPicture().getUrl() == null
                    || playerImportDto.getTeam() == null
                    || playerImportDto.getTeam().getPicture() == null
                    || playerImportDto.getTeam().getPicture().getUrl() == null
                    || playerImportDto.getTeam().getName() == null) {
                output.append(String.format(INVALID_MESSAGE, player.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            Picture playerPicture = this.pictureRepository.findByUrl(playerImportDto.getPicture().getUrl());
            Picture teamPicture = this.pictureRepository.findByUrl(playerImportDto.getTeam().getPicture().getUrl());
            if (playerPicture == null || teamPicture == null) {
                output.append(String.format(INVALID_MESSAGE, player.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            Team team = this.teamRepository.findByName(playerImportDto.getTeam().getName());
            if (team == null) {
                output.append(String.format(INVALID_MESSAGE, player.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            if (!team.getPicture().getUrl().equals(playerImportDto.getTeam().getPicture().getUrl())) {
                output.append(String.format(INVALID_MESSAGE, player.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            player.setPicture(playerPicture);
            player.setTeam(team);

            if (!this.validatorUtil.isValid(player)) {
                output.append(String.format(INVALID_MESSAGE, player.getClass().getSimpleName().toLowerCase()))
                        .append(System.lineSeparator());
                continue;
            }

            this.playerRepository.save(player);
            String playerName = player.getFirstName() + " " + player.getLastName();
            output.append(String.format(SUCCESSFUL_MESSAGE,
                    player.getClass().getSimpleName().toLowerCase(), playerName))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return this.fileUtil.readFile(PLAYER_IMPORT_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        List<Player> players = this.playerRepository
                .findAllBySalaryGreaterThanOrderBySalaryDesc(MINIMUM_SALARY);
        StringBuilder output = new StringBuilder();

        for (Player player : players) {
            String playerInfo = String.format("Player name: %s %s %n" +
                    "   Number: %d%n" +
                    "   Salary: %s%n" +
                    "   Team: %s", player.getFirstName(), player.getLastName(),
                    player.getNumber(), player.getSalary(), player.getTeam().getName());

            output.append(playerInfo).append(System.lineSeparator());
        }
        
        return output.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        List<Player> players = this.playerRepository.findAllByTeamNameOrderById(TEAM_NAME);
        StringBuilder output = new StringBuilder();
        output.append(String.format("Team: %s", TEAM_NAME)).append(System.lineSeparator());
        for (Player player : players) {
            String playerInfo = String.format("Player name: %s %s - %s%n" +
                    "Number: %d", player.getFirstName(), player.getLastName()
                    , player.getPosition().name(), player.getNumber());
            output.append(playerInfo).append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
