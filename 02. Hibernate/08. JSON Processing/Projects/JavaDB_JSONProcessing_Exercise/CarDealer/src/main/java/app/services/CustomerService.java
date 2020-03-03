package app.services;

import app.domain.dto.query1.CustomerDto;
import app.domain.dto.query5.CustomerResultDto;

import java.util.List;

public interface CustomerService {
    String seedCustomers(CustomerDto[] customerDtos);

    List<CustomerDto> orderedCustomers();

    List<CustomerResultDto> totalSalesByCustomer();
}
