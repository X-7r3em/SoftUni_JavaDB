package app.controllers;

import app.domain.dto.CategoryDto;
import app.domain.dto.ProductDto;
import app.domain.dto.UserDto;
import app.domain.dto.query1.ProductResultDto;
import app.services.CategoryService;
import app.services.ProductService;
import app.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


@Component
public class AppController implements CommandLineRunner {
    private static final String DEFAULT_PATH = "src\\main\\resources\\files\\";

    private final Gson gson;
    private final BufferedReader br;
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public AppController(Gson gson, BufferedReader br, UserService userService,
                         ProductService productService, CategoryService categoryService) {
        this.gson = gson;
        this.br = br;
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedData();


//        this.productsInRange();
//        this.successfullySoldProducts();
        this.categoriesByProductCount();
//        this.usersAndProducts();
    }

    private void seedData() throws FileNotFoundException {
        StringBuilder output = new StringBuilder();

        UserDto[] userDtos = this.gson.fromJson(new FileReader(DEFAULT_PATH + "users.json"), UserDto[].class);
        output.append(this.userService.seedUsers(userDtos));

        ProductDto[] productDtos = this.gson.fromJson(new FileReader(DEFAULT_PATH + "products.json"), ProductDto[].class);
        output.append(System.lineSeparator()).append(this.productService.seedProducts(productDtos));

        CategoryDto[] categoryDtos = this.gson.fromJson(new FileReader(DEFAULT_PATH + "categories.json"), CategoryDto[].class);
        output.append(System.lineSeparator()).append(this.categoryService.seedCategories(categoryDtos));

        System.out.println(output.toString());
    }

    //Query 1 - Products In Range
    private void productsInRange() throws IOException {
        BigDecimal lower = new BigDecimal(this.br.readLine());
        BigDecimal bigger = new BigDecimal(this.br.readLine());
        List<ProductResultDto> productDtos = this.productService.productsInRange(lower, bigger);

        System.out.println(this.gson.toJson(productDtos));
    }

    //Query 2 - Successfully Sold Products
    private void successfullySoldProducts() {
        System.out.println(this.gson.toJson(this.userService.successfullySoldProducts()));
    }

    //Query 3 - Categories By Products Count
    private void categoriesByProductCount() {
        System.out.println(this.gson.toJson(this.categoryService.categoriesByProductCount()));
    }

    //Query 4 - Users and Products
    private void usersAndProducts() {
        System.out.println(this.gson.toJson(this.userService.usersAndProducts()));
    }
}
