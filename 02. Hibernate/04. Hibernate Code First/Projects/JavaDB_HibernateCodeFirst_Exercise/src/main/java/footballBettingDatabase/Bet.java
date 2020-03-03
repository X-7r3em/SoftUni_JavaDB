package footballBettingDatabase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
public class Bet {
    private int id;
    private BigDecimal betMoney;
    private Date dateOfBet;
    private FootBallUser user;
    private Set<Game> games;

    public Bet() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    public BigDecimal getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(BigDecimal betMoney) {
        this.betMoney = betMoney;
    }

    @Basic
    public Date getDateOfBet() {
        return dateOfBet;
    }

    public void setDateOfBet(Date dateOfBet) {
        this.dateOfBet = dateOfBet;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public FootBallUser getUser() {
        return user;
    }

    public void setUser(FootBallUser user) {
        this.user = user;
    }

    @ManyToMany(mappedBy = "bets", targetEntity = Game.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
