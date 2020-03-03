package app.services;

import app.domain.dto.query1.CustomerDto;
import app.domain.dto.query5.CustomerResultDto;
import app.domain.entities.Customer;
import app.repositories.CustomerRepository;
import app.utilities.ValidatorUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static app.utilities.SystemMessages.ALREADY_SEEDED;
import static app.utilities.SystemMessages.SUCCESSFUL_SEED;

@Repository
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidatorUtility validatorUtility;
    private final ModelMapper mapper;
    private final Random random;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ValidatorUtility validatorUtility, ModelMapper mapper, Random random) {
        this.customerRepository = customerRepository;
        this.validatorUtility = validatorUtility;
        this.mapper = mapper;
        this.random = random;
    }

    @Override
    public String seedCustomers(CustomerDto[] customerDtos) {
        if (this.customerRepository.count() != 0) {
            return String.format(ALREADY_SEEDED, "Customers");
        }

        StringBuilder output = new StringBuilder();
        for (CustomerDto customerDto : customerDtos) {
            if (!this.validatorUtility.isValid(customerDto)) {
                output.append(this.validatorUtility.validationErrors(customerDto))
                        .append(System.lineSeparator());
                continue;
            }


            Customer customer = this.mapper.map(customerDto, Customer.class);
            customer.setBirthDate(LocalDateTime.parse(customerDto.getBirthDate()));

            this.customerRepository.saveAndFlush(customer);
        }

        return output.toString() + String.format(SUCCESSFUL_SEED, "Customers");
    }

    @Override
    public List<CustomerDto> orderedCustomers() {
        return this.customerRepository.findAllOrderByBirthDate().stream()
                .map(e -> mapper.map(e, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResultDto> totalSalesByCustomer() {
        List<Object[]> cars = this.customerRepository.totalSalesByCustomer();
        return cars.stream()
                .map(c -> new CustomerResultDto((String) c[0],  (BigInteger) c[1], (BigDecimal) c[2]))
                .collect(Collectors.toList());
    }
}
