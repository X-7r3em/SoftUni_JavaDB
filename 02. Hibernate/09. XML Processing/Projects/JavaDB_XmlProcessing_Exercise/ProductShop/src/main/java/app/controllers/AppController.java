package app.controllers;

import app.domain.dto.CategoryImport.CategoryWrapperDto;
import app.domain.dto.ProductsImport.ProductWrapperDto;
import app.domain.dto.UserImport.UserWrapperDto;
import app.domain.dto.query1.ProductResultDto;
import app.domain.dto.query1.ProductWrapperResultDto;
import app.domain.dto.query2.UserSoldWrapperDto;
import app.domain.dto.query3.CategoryCountWrapperDto;
import app.services.CategoryService;
import app.services.ProductService;
import app.services.UserService;
import app.utilities.XmlParser;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigDecimal;


@Component
public class AppController implements CommandLineRunner {
    private static final String DEFAULT_PATH = "src\\main\\resources\\files\\";

    private final Gson gson;
    private final BufferedReader br;
    private final XmlParser xmlParser;
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public AppController(Gson gson, BufferedReader br, XmlParser xmlParser,
                         UserService userService, ProductService productService, CategoryService categoryService) {
        this.gson = gson;
        this.br = br;
        this.xmlParser = xmlParser;
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedData();


        this.productsInRange();
        this.successfullySoldProducts();
        this.categoriesByProductCount();
        this.usersAndProducts();
    }

    private void seedData() throws FileNotFoundException, JAXBException {
        StringBuilder output = new StringBuilder();

        UserWrapperDto userWrapperDto = this.xmlParser.fromXml(UserWrapperDto.class, DEFAULT_PATH + "users.xml");
        output.append(this.userService.seedUsers(userWrapperDto.getUsers()));

        ProductWrapperDto productWrapperDto =
                this.xmlParser.fromXml(ProductWrapperDto.class, DEFAULT_PATH + "products.xml");
        output.append(System.lineSeparator()).append(this.productService.seedProducts(productWrapperDto.getProducts()));

        CategoryWrapperDto categoryWrapperDto =
                this.xmlParser.fromXml(CategoryWrapperDto.class, DEFAULT_PATH + "categories.xml");
        output.append(System.lineSeparator()).append(this.categoryService.seedCategories(categoryWrapperDto.getCategories()));

        System.out.println(output.toString());
    }

    //Query 1 - Products In Range
    private void productsInRange() throws IOException, JAXBException {
        BigDecimal lower = new BigDecimal(this.br.readLine());
        BigDecimal bigger = new BigDecimal(this.br.readLine());
        ProductWrapperResultDto productDtos = this.productService.productsInRange(lower, bigger);

        System.out.println(this.xmlParser.toXml(productDtos));
    }



    //Query 2 - Successfully Sold Products
    private void successfullySoldProducts() throws JAXBException {
        System.out.println(this.xmlParser.toXml(this.userService.successfullySoldProducts()));
    }

    //Query 3 - Categories By Products Count
    private void categoriesByProductCount() throws JAXBException {
        System.out.println(this.xmlParser.toXml(this.categoryService.categoriesByProductCount()));

    }

    //Query 4 - Users and Products
    private void usersAndProducts() throws JAXBException {
        System.out.println(this.xmlParser.toXml(this.userService.usersAndProducts()));
    }
}
