package com.example.demo.domain;

import com.example.demo.domain.entities.Ingredient;
import com.example.demo.domain.entities.Size;
import com.example.demo.domain.services.interfaces.IngredientService;
import com.example.demo.domain.services.interfaces.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AppController implements CommandLineRunner {
    private BufferedReader br;
    private ShampooService shampooService;
    private IngredientService ingredientService;

    @Autowired
    public AppController(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
//        this.selectShampoosBySize();
//        this.selectShampoosBySizeOrLabel();
//        this.selectShampoosByPrice();
//        this.selectIngredientsByName();
//        this.selectIngredientsByNames();
//        this.countShampoosByPrice();
//        this.findAllByIngredients();
        this.selectShampoosByIngredientsCount();
//        this.selectIngredientNameAndShampooBrandByName();
//        this.deleteIngredientsByName();
//        this.increasePrice();
//        this.increasePriceByName();
    }


    private void selectShampoosBySize() throws IOException {
        String size = this.br.readLine().toUpperCase();
        Size sizeValue = Size.valueOf(size);
        this.shampooService.findAllBySize(sizeValue)
                .forEach(s -> System.out.printf("%s %s %slv.%n",
                        s.getBrand(), s.getSize(), s.getPrice()));

    }

    private void selectShampoosBySizeOrLabel() throws IOException {
        String size = this.br.readLine().toUpperCase();
        Size sizeValue = Size.valueOf(size);
        long id = Long.parseLong(this.br.readLine());
        this.shampooService.findAllBySizeOrLabel(sizeValue, id)
                .forEach(s -> System.out.println(
                        String.format("%s %s %slv.",
                                s.getBrand(), s.getSize(), s.getPrice())));
    }

    private void selectShampoosByPrice() throws IOException {
        BigDecimal price = new BigDecimal(this.br.readLine());
        this.shampooService.findAllByPriceGreaterThan(price)
                .forEach(s -> System.out.println(
                        String.format("%s %s %slv.",
                                s.getBrand(), s.getSize(), s.getPrice())));
    }

    private void selectIngredientsByName() throws IOException {
        this.ingredientService.findAllIngredientsStartingWith(this.br.readLine())
                .forEach(i -> System.out.println(i.getName()));
    }

    private void selectIngredientsByNames() throws IOException {
        List<String> names = new ArrayList<>();
        String ingredient;
        while (!(ingredient = this.br.readLine()).isBlank()) {
            names.add(ingredient);
        }

        this.ingredientService.findAllIngredientsWithNameIn(names)
                .forEach(i -> System.out.println(i.getName()));
    }

    private void countShampoosByPrice() throws IOException {
        BigDecimal price = new BigDecimal(this.br.readLine());

        System.out.println(this.shampooService.countOfShampooByPriceLowerThan(price));
    }

    private void selectShampoosByIngredients() throws IOException {
        Set<Ingredient> ingredients = new HashSet<>();
        String ingredient = "";
        while (!(ingredient = this.br.readLine()).isBlank()) {
            ingredients.addAll(this.ingredientService.findAllIngredientsWithName(ingredient));
        }

        this.shampooService.selectShampoosByIngredient(ingredients)
                .forEach(s -> System.out.println(s.getBrand()));
    }

    private void selectShampoosByIngredientsCount() throws IOException {
        int number = Integer.parseInt(this.br.readLine());
        this.shampooService.findAllByIngredientsCount(number)
                .forEach(s -> System.out.println(s.getBrand()));
    }

    private void selectIngredientNameAndShampooBrandByName() throws IOException {
        String name = this.br.readLine();
        this.shampooService.findPriceByBrand(name)
                .stream().limit(1)
                .forEach(s ->
                        System.out.println(
                                String.format("%s %.2f",
                                        s.getBrand(), s.getIngredients().stream()
                                                .map(Ingredient::getPrice)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add))));
    }

    private void deleteIngredientsByName() throws IOException {
        this.ingredientService.deleteAllByName(this.br.readLine());
    }

    private void increasePrice() {
        this.ingredientService.increasePrice();
    }

    private void increasePriceByName() throws IOException {
        Set<String> names = new HashSet<>();
        String name = "";
        while (!(name = this.br.readLine()).isBlank()){
            names.add(name);
        }

        this.ingredientService.increasePrice(names);
    }
}
