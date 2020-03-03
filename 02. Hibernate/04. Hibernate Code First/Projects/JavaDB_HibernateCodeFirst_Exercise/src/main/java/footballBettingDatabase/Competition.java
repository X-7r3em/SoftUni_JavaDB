package footballBettingDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Competition {
    private int id;
    private String name;
    private CompetitionTypes competitionTypes;
    private Set<Game> games;

    public Competition() {
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    public CompetitionTypes getCompetitionTypes() {
        return competitionTypes;
    }

    public void setCompetitionTypes(CompetitionTypes competitionTypes) {
        this.competitionTypes = competitionTypes;
    }

    @OneToMany(mappedBy = "competition", targetEntity = Game.class)
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
