package footballBettingDatabase;

import java.io.Serializable;
import java.util.Objects;


public class PlayerStatisticsId implements Serializable {
    private int gameId;
    private int playerId;

    public PlayerStatisticsId() {
    }

    public PlayerStatisticsId(int gameId, int playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStatisticsId that = (PlayerStatisticsId) o;
        return gameId == that.gameId &&
                playerId == that.playerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerId);
    }
}
