package com.example.demo.domain.services;

import com.example.demo.domain.entities.Ingredient;
import com.example.demo.domain.repositories.IngredientRepository;
import com.example.demo.domain.services.interfaces.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAllIngredientsStartingWith(String pattern) {
        pattern = pattern + "%";
        return this.ingredientRepository.findIngredientByNameIsLike(pattern);
    }

    @Override
    public List<Ingredient> findAllIngredientsWithNameIn(List<String> names) {
        return this.ingredientRepository.findIngredientByNameInOrderByPrice(names);
    }

    @Override
    public List<Ingredient> findAllIngredientsWithName(String name) {
        return this.ingredientRepository.findIngredientByName(name);
    }

    @Override
    public void deleteAllByName(String name) {
        this.ingredientRepository.deleteAllByName(name);
    }

    @Override
    public void increasePrice() {
        this.ingredientRepository.increasePrice();
    }

    @Override
    public void increasePrice(Set<String> names) {
        this.ingredientRepository.increasePrice(names);
    }
}
