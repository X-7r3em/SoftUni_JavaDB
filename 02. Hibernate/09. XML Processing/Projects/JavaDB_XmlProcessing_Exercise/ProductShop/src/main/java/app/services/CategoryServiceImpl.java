package app.services;

import app.domain.dto.query3.CategoryCountDto;
import app.domain.dto.CategoryImport.CategoryDto;
import app.domain.dto.query3.CategoryCountWrapperDto;
import app.domain.entities.Category;
import app.domain.entities.Product;
import app.repositories.CategoryRepository;
import app.repositories.ProductRepository;
import app.utilities.ValidatorUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static app.utilities.SystemMessages.ALREADY_SEEDED;
import static app.utilities.SystemMessages.SUCCESSFUL_SEED;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final ValidatorUtility validatorUtility;
    private final Random random;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ProductRepository productRepository, ModelMapper mapper,
                               ValidatorUtility validatorUtility, Random random) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.validatorUtility = validatorUtility;
        this.random = random;
    }

    @Override
    @Transactional
    public String seedCategories(List<CategoryDto> categoryDtos) {
        if (this.categoryRepository.count() != 0) {
            return String.format(ALREADY_SEEDED, "Categories");
        }

        StringBuilder output = new StringBuilder();
        for (CategoryDto categoryDto : categoryDtos) {
            if (!this.validatorUtility.isValid(categoryDto)) {
                output.append(this.validatorUtility.validationErrors(categoryDto))
                        .append(System.lineSeparator());
                continue;
            }


            Category category = this.mapper.map(categoryDto, Category.class);

            this.categoryRepository.saveAndFlush(category);
        }

        output.append(String.format(SUCCESSFUL_SEED, "Categories"));

        output.append(System.lineSeparator()).append(this.seedCategoriesToProducts());

        return output.toString();
    }

    private String seedCategoriesToProducts() {
        List<Product> products = this.productRepository.findAll();
        List<Category> categories = this.categoryRepository.findAll();

        for (Product product : products) {
            for (int i = 0; i < 2; i++) {
                Category category = categories.get(this.random.nextInt(categories.size()));

                if (product.getCategories().contains(category)) {
                    i--;
                    continue;
                }

                if (category.getProducts() == null){
                    category.setProducts(new HashSet<>());
                }

                category.addProduct(product);

                this.categoryRepository.saveAndFlush(category);
            }
        }

        return String.format(SUCCESSFUL_SEED, "Categories to products");
    }

    @Override
    public CategoryCountWrapperDto categoriesByProductCount() {
        List<Category> categories = this.categoryRepository.categoriesByProductsCount();
        return new CategoryCountWrapperDto(categories.stream()
                .map(c -> this.mapper.map(c, CategoryCountDto.class))
                .collect(Collectors.toList()));
    }
}
