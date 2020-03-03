package app.repositories;

import app.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Query(value = "SELECT c.make, c.model, c.traveled_distance, cust.name, " +
            "s.discount / 100, SUM(p.price), SUM(p.price) * (1 - s.discount / 100) " +
            "FROM sales s " +
            "INNER JOIN customer cust on s.customer_id = cust.id " +
            "INNER JOIN cars c ON s.car_id = c.id " +
            "INNER JOIN parts_cars pc ON c.id = pc.car_id " +
            "INNER JOIN parts p on pc.part_id = p.id " +
            "GROUP BY s.id", nativeQuery = true)
    List<Object[]> salesWithAppliedDiscount();
}
