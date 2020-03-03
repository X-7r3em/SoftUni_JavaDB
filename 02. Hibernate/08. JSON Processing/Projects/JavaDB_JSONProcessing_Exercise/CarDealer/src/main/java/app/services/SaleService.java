package app.services;


import app.domain.dto.query6.SaleResultDto;

import java.util.List;

public interface SaleService {
    String seedSales();

    List<SaleResultDto> salesWithAppliedDiscount();
}
