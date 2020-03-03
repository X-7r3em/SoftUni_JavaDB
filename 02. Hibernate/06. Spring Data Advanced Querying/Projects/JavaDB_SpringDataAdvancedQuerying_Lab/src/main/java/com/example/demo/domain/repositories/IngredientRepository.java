package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findIngredientByNameIsLike(String patter);

    List<Ingredient> findIngredientByName(String name);

    List<Ingredient> findIngredientByNameInOrderByPrice(List<String> names);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Ingredient i WHERE i.name = :name")
    void deleteAllByName(@Param(value = "name") String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Ingredient i SET i.price = i.price * 1.1")
    void increasePrice();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Ingredient i SET i.price = i.price * 1.1 WHERE i.name IN :names")
    void increasePrice(@Param(value = "names") Set<String> names);
}
