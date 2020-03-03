package app.services;


import app.domain.dto.query6.SaleResultWrapperDto;

import java.util.List;

public interface SaleService {
    String seedSales();

    SaleResultWrapperDto salesWithAppliedDiscount();
}
