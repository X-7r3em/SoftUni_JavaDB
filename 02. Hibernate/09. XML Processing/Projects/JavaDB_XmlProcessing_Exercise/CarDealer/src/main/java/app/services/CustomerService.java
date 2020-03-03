package app.services;

import app.domain.dto.customerimport.CustomerDto;
import app.domain.dto.query1.CustomerResultIdWrapperDto;
import app.domain.dto.query5.CustomerResultWrapperDto;

import java.util.List;

public interface CustomerService {
    String seedCustomers(List<CustomerDto> customerDtos);

    CustomerResultIdWrapperDto orderedCustomers();

    CustomerResultWrapperDto totalSalesByCustomer();
}
