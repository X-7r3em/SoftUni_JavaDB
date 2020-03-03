package com.example.demo.domain.services.interfaces;

import com.example.demo.domain.entities.Ingredient;
import com.example.demo.domain.entities.Shampoo;
import com.example.demo.domain.entities.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {
    List<Shampoo> findAllBySize(Size size);

    List<Shampoo> findAllBySizeOrLabel(Size size, long id);

    List<Shampoo> findAllByPriceGreaterThan(BigDecimal price);

    int countOfShampooByPriceLowerThan(BigDecimal price);

    List<Shampoo> selectShampoosByIngredient(Set<Ingredient> ingredients);

    List<Shampoo> findAllByIngredientsCount(int number);

    List<Shampoo> findPriceByBrand(String brand);
}
