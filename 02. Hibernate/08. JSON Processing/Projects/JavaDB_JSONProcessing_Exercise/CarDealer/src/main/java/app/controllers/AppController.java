package app.controllers;

import app.domain.dto.*;
import app.domain.dto.query1.CustomerDto;
import app.services.*;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class AppController implements CommandLineRunner {
    private static final String DEFAULT_PATH = "src\\main\\resources\\files\\";

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final Gson gson;

    public AppController(SupplierService supplierService, PartService partService,
                         CarService carService, CustomerService customerService, SaleService saleService, Gson gson) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedData();

//        this.orderedCustomer();
//        this.carsFromToyota();
//        this.localSuppliers();
        this.carsWithTheirListOfParts();
//        this.totalSalesByCustomer();
//        this.salesWithAppliedDiscount();
    }

    private void seedData() throws FileNotFoundException {
        this.seedSuppliers();
        this.seedParts();
        this.seedCars();
        this.seedCustomers();
        this.seedSales();
    }

    private void seedSuppliers() throws FileNotFoundException {
        SupplierDto[] supplierDtos = this.gson.fromJson(new FileReader(DEFAULT_PATH + "suppliers.json"), SupplierDto[].class);
        System.out.println(this.supplierService.seedSuppliers(supplierDtos));
    }

    private void seedParts() throws FileNotFoundException {
        PartDto[] partDtos = this.gson.fromJson(new FileReader(DEFAULT_PATH + "parts.json"), PartDto[].class);
        System.out.println(this.partService.seedParts(partDtos));
    }

    private void seedCars() throws FileNotFoundException {
        CarDto[] carDtos = this.gson.fromJson(new FileReader(DEFAULT_PATH + "cars.json"), CarDto[].class);
        System.out.println(this.carService.seedCars(carDtos));
    }

    private void seedCustomers() throws FileNotFoundException {
        CustomerDto[] customerDtos = this.gson.fromJson(new FileReader(DEFAULT_PATH + "customers.json"), CustomerDto[].class);
        System.out.println(this.customerService.seedCustomers(customerDtos));
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    //Query 1 – Ordered Customers
    private void orderedCustomer() {
        System.out.println(this.gson.toJson(this.customerService.orderedCustomers()));
    }

    //Query 2 – Cars from make Toyota
    private void carsFromToyota() {
        System.out.println(this.gson.toJson(this.carService.findCarsByMake("Toyota")));
    }

    //Query 3 – Local Suppliers
    private void localSuppliers() {
        System.out.println(this.gson.toJson(
                this.supplierService.findAllSuppliersByImport(false)));
    }

    //Query 4 – Cars with Their List of Parts
    private void carsWithTheirListOfParts() {
        System.out.println(this.gson.toJson(this.carService.findCarsAndParts()));
    }

    //Query 5 – Total Sales by Customer
    private void totalSalesByCustomer() {
        System.out.println(this.gson.toJson(this.customerService.totalSalesByCustomer()));
    }

    //Query 6 – Sales with Applied Discount
    private void salesWithAppliedDiscount(){
        System.out.println(this.gson.toJson(this.saleService.salesWithAppliedDiscount()));
    }
}
