package hospitalDatabase;

import javax.persistence.*;

@Entity
@Table(name = "diagnose")
public class Diagnose {
    private int id;
    private String name;
    private String comment;

    public Diagnose() {
    }

    public Diagnose(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "comment", nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
