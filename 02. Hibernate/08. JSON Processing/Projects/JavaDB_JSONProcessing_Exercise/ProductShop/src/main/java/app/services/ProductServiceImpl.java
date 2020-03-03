package app.services;

import app.domain.dto.ProductDto;
import app.domain.dto.query1.ProductResultDto;
import app.domain.entities.Product;
import app.domain.entities.User;
import app.repositories.ProductRepository;
import app.repositories.UserRepository;
import app.utilities.ValidatorUtility;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static app.utilities.SystemMessages.ALREADY_SEEDED;
import static app.utilities.SystemMessages.SUCCESSFUL_SEED;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ValidatorUtility validatorUtility;
    private final Random random;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository,
                              ModelMapper mapper, ValidatorUtility validatorUtility, Random random) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validatorUtility = validatorUtility;
        this.random = random;
    }

    @Override
    public String seedProducts(ProductDto[] productDtos) {
        if (this.productRepository.count() != 0) {
            return String.format(ALREADY_SEEDED, "Products");
        }

        List<User> users = this.userRepository.findAll();

        StringBuilder output = new StringBuilder();
        for (ProductDto productDto : productDtos) {
            if (!this.validatorUtility.isValid(productDto)) {
                output.append(this.validatorUtility.validationErrors(productDto))
                        .append(System.lineSeparator());
                continue;
            }

            Product product = this.mapper.map(productDto, Product.class);
            User seller = users.get(this.random.nextInt(users.size()));
            product.setSeller(seller);

            int buyerId = this.random.nextInt(users.size() + 25);
            User buyer = buyerId < users.size() ? users.get(buyerId) : null;
            product.setBuyer(buyer);

            this.productRepository.saveAndFlush(product);
        }

        return output.toString() + String.format(SUCCESSFUL_SEED, "Products");
    }

    @Override
    public List<ProductResultDto> productsInRange(BigDecimal lower, BigDecimal bigger) {
        List<Product> products = this.productRepository
                .findAllByBuyerIsNullAndPriceBetweenOrderByPriceAsc(lower, bigger);

        List<ProductResultDto> productResultDtos = new ArrayList<>();

        for (Product product : products) {
            ProductResultDto productResultDto = this.mapper.map(product, ProductResultDto.class);
            productResultDtos.add(productResultDto);
        }

        return productResultDtos;
    }
}
