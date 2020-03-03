package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Ingredient;
import com.example.demo.domain.entities.Shampoo;
import com.example.demo.domain.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    List<Shampoo> findAllByPriceLessThan(BigDecimal price);

    @Query(value = "SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i IN :ingredients")
    List<Shampoo> findAllByIngredients(@Param(value = "ingredients") Set<Ingredient> ingredients);

    @Query(value = "SELECT s FROM Shampoo s JOIN s.ingredients WHERE SIZE(s.ingredients) < :number ORDER BY s.id")
    List<Shampoo> findAllByIngredientsCount(@Param(value = "number") int number);

    @Query(value = "SELECT s FROM Shampoo s JOIN FETCH s.ingredients i WHERE s.brand = :brand")
    List<Shampoo> findAllByBrand(@Param(value = "brand")String brand);
}
