package com.example.demo.domain.services.interfaces;

import com.example.demo.domain.entities.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientService {
    List<Ingredient> findAllIngredientsStartingWith(String pattern);

    List<Ingredient> findAllIngredientsWithNameIn(List<String> names);

    List<Ingredient> findAllIngredientsWithName(String name);

    void deleteAllByName(String name);

    void increasePrice();

    void increasePrice(Set<String> names);
}
