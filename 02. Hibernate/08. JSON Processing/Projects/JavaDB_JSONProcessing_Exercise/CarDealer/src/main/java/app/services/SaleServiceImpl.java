package app.services;

import app.domain.dto.query6.CarDiscountResultDto;
import app.domain.dto.query6.SaleResultDto;
import app.domain.entities.Car;
import app.domain.entities.Customer;
import app.domain.entities.Sale;
import app.repositories.CarRepository;
import app.repositories.CustomerRepository;
import app.repositories.SaleRepository;
import app.utilities.ValidatorUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static app.utilities.SystemMessages.ALREADY_SEEDED;
import static app.utilities.SystemMessages.SUCCESSFUL_SEED;

@Service
public class SaleServiceImpl implements SaleService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ValidatorUtility validatorUtility;
    private final ModelMapper mapper;
    private final Random random;

    @Autowired
    public SaleServiceImpl(CarRepository carRepository, CustomerRepository customerRepository,
                           SaleRepository saleRepository, ValidatorUtility validatorUtility,
                           ModelMapper mapper, Random random) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
        this.validatorUtility = validatorUtility;
        this.mapper = mapper;
        this.random = random;
    }

    @Override
    public String seedSales() {
        if (this.saleRepository.count() != 0) {
            return String.format(ALREADY_SEEDED, "Sale");
        }

        List<Car> cars = this.carRepository.findAll();
        List<Customer> customers = this.customerRepository.findAll();
        List<Integer> discounts = new ArrayList<>(Arrays.asList(0, 5, 10, 15, 20, 30, 40, 50));
        for (Car car : cars) {
            int numberOfSales = 1 + this.random.nextInt(3);
            for (int i = 0; i < numberOfSales; i++) {
                Sale sale = new Sale();
                sale.setCar(car);
                sale.setCustomer(customers.get(this.random.nextInt(customers.size())));
                int discount = discounts.get(this.random.nextInt(discounts.size()));
                discount = sale.getCustomer().getYoungDriver() ? discount += 5 : discount;
                sale.setDiscount(discount);

                this.saleRepository.saveAndFlush(sale);
            }
        }

        return String.format(SUCCESSFUL_SEED, "Sales");
    }

    @Override
    public List<SaleResultDto> salesWithAppliedDiscount() {
        List<Object[]> sales = this.saleRepository.salesWithAppliedDiscount();
        List<SaleResultDto> saleResultDtos = new ArrayList<>();

        for (Object[] sale : sales) {
            CarDiscountResultDto carDiscountResultDto =
                    new CarDiscountResultDto((String)sale[0], (String)sale[1], (BigInteger) sale[2]);

            SaleResultDto saleResultDto = new SaleResultDto(carDiscountResultDto,
                    (String)sale[3], (BigDecimal)sale[4],
                    (BigDecimal)sale[5], (BigDecimal)sale[6]);

            saleResultDtos.add(saleResultDto);
        }

        return saleResultDtos;
    }
}
