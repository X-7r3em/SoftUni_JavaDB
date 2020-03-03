package footballBettingDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "continent")
public class Continent {
    private int id;
    private String name;
    private Set<Country> countries;

    public Continent() {
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

    @ManyToMany(mappedBy = "continent", targetEntity = Country.class,
        fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
}
