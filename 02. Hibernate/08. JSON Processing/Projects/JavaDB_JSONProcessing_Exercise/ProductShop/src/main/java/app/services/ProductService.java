package app.services;

import app.domain.dto.ProductDto;
import app.domain.dto.query1.ProductResultDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    String seedProducts(ProductDto[] productDtos);

    List<ProductResultDto> productsInRange(BigDecimal lower, BigDecimal bigger);

}
