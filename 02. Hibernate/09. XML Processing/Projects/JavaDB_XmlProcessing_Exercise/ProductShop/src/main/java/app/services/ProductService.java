package app.services;

import app.domain.dto.ProductsImport.ProductDto;
import app.domain.dto.query1.ProductResultDto;
import app.domain.dto.query1.ProductWrapperResultDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    String seedProducts(List<ProductDto> productDtos);

    ProductWrapperResultDto productsInRange(BigDecimal lower, BigDecimal bigger);

}
