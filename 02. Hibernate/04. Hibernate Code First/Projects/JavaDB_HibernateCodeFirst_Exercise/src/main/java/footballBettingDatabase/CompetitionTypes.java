package footballBettingDatabase;

import javax.persistence.*;

@Entity
@Table(name = "competition_type")
public class CompetitionTypes {
    private int id;
    private String type;

    public CompetitionTypes() {
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
