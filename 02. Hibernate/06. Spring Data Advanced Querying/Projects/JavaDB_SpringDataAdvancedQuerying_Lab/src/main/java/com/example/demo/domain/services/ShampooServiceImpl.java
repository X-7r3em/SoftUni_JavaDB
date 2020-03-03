package com.example.demo.domain.services;

import com.example.demo.domain.entities.Ingredient;
import com.example.demo.domain.entities.Shampoo;
import com.example.demo.domain.entities.Size;
import com.example.demo.domain.repositories.ShampooRepository;
import com.example.demo.domain.services.interfaces.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {
    private ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findAllBySize(Size size) {
        return this.shampooRepository.findAllBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> findAllBySizeOrLabel(Size size, long id) {
        return this.shampooRepository.findAllBySizeOrLabelIdOrderByPrice(size, id);
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThan(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countOfShampooByPriceLowerThan(BigDecimal price) {
        return this.shampooRepository.findAllByPriceLessThan(price).size();
    }

    @Override
    public List<Shampoo> selectShampoosByIngredient(Set<Ingredient> ingredients) {
        return this.shampooRepository.findAllByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> findAllByIngredientsCount(int number) {
        return this.shampooRepository.findAllByIngredientsCount(number);
    }

    @Override
    public List<Shampoo> findPriceByBrand(String brand) {
        return this.shampooRepository.findAllByBrand(brand);
    }
}
