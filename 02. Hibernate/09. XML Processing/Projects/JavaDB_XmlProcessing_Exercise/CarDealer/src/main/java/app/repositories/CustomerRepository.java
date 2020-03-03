package app.repositories;

import app.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c ORDER BY c.birthDate ASC, c.youngDriver")
    List<Customer> findAllOrderByBirthDate();

    @Query(value = "SELECT c.name fullName, COUNT(DISTINCT car.id) boughtCars, SUM(p.price) spentMoney FROM customer c " +
            "INNER JOIN sales s ON c.id = s.customer_id " +
            "INNER JOIN cars car ON s.car_id = car.id " +
            "INNER JOIN parts_cars pc ON car.id = pc.car_id " +
            "INNER JOIN parts p ON pc.part_id = p.id " +
            "GROUP BY c.id " +
            "ORDER BY spentMoney DESC, boughtCars DESC", nativeQuery = true)
    List<Object[]> totalSalesByCustomer();
}
