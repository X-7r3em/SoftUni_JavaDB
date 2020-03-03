package app.controllers;

import app.domain.dto.carimport.CarWrapperDto;
import app.domain.dto.customerimport.CustomerWrapperDto;
import app.domain.dto.partimport.PartWrapperDto;
import app.domain.dto.query1.CustomerResultIdWrapperDto;
import app.domain.dto.query2.CarIdWrapperDto;
import app.domain.dto.query3.SupplierIdWrapperDto;
import app.domain.dto.query4.ListWrapperDto;
import app.domain.dto.query5.CustomerResultWrapperDto;
import app.domain.dto.query6.SaleResultWrapperDto;
import app.domain.dto.supplierimport.SupplierWrapperDto;
import app.services.*;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.*;
import java.io.*;

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
//        this.seedData();

        this.orderedCustomer();
        this.carsFromToyota();
        this.localSuppliers();
        this.carsWithTheirListOfParts();
        this.totalSalesByCustomer();
        this.salesWithAppliedDiscount();
    }

    private void seedData() throws FileNotFoundException, JAXBException {
        this.seedSuppliers();
        this.seedParts();
        this.seedCars();
        this.seedCustomers();
        this.seedSales();
    }

    private void seedSuppliers() throws FileNotFoundException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SupplierWrapperDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader bfr = new BufferedReader(new FileReader(DEFAULT_PATH + "suppliers.xml"));
        SupplierWrapperDto supplierWrapperDto = (SupplierWrapperDto) unmarshaller.unmarshal(bfr);

        System.out.println(this.supplierService.seedSuppliers(supplierWrapperDto.getSuppliers()));
    }

    private void seedParts() throws FileNotFoundException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PartWrapperDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader bfr = new BufferedReader(new FileReader(DEFAULT_PATH + "parts.xml"));
        PartWrapperDto partWrapperDto = (PartWrapperDto) unmarshaller.unmarshal(bfr);
        System.out.println(this.partService.seedParts(partWrapperDto.getParts()));
    }

    private void seedCars() throws FileNotFoundException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CarWrapperDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader bfr = new BufferedReader(new FileReader(DEFAULT_PATH + "cars.xml"));
        CarWrapperDto carWrapperDto = (CarWrapperDto) unmarshaller.unmarshal(bfr);
        System.out.println(this.carService.seedCars(carWrapperDto.getCars()));
    }

    private void seedCustomers() throws FileNotFoundException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CustomerWrapperDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader bfr = new BufferedReader(new FileReader(DEFAULT_PATH + "customers.xml"));
        CustomerWrapperDto customerWrapperDto = (CustomerWrapperDto) unmarshaller.unmarshal(bfr);
        System.out.println(this.customerService.seedCustomers(customerWrapperDto.getCustomers()));
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    //Query 1 – Ordered Customers
    private void orderedCustomer() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CustomerResultIdWrapperDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(this.customerService.orderedCustomers(), stringWriter);

        System.out.println(stringWriter);
    }

    //Query 2 – Cars from make Toyota
    private void carsFromToyota() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CarIdWrapperDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(this.carService.findCarsByMake("Toyota"), stringWriter);

        System.out.println(stringWriter);
    }

    //Query 3 – Local Suppliers
    private void localSuppliers() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SupplierIdWrapperDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(this.supplierService.findAllSuppliersByImport(false), stringWriter);

        System.out.println(stringWriter);
    }

    //Query 4 – Cars with Their List of Parts
    private void carsWithTheirListOfParts() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ListWrapperDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(this.carService.findCarsAndParts(), stringWriter);

        System.out.println(stringWriter);
    }

    //Query 5 – Total Sales by Customer
    private void totalSalesByCustomer() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CustomerResultWrapperDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(this.customerService.totalSalesByCustomer(), stringWriter);

        System.out.println(stringWriter);
    }

    //Query 6 – Sales with Applied Discount
    private void salesWithAppliedDiscount() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SaleResultWrapperDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(this.saleService.salesWithAppliedDiscount(), stringWriter);

        System.out.println(stringWriter);
    }
}
