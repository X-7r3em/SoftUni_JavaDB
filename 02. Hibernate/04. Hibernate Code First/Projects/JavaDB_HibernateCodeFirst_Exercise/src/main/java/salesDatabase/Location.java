package salesDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "store_location")
public class Location extends Identifiable {

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "location", targetEntity = Sale.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Sale> sales;

    public Location() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
