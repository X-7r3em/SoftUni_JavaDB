package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        List<Category> categories = this.categoryRepository.findAll()
                .stream()
                .sorted((f, s) -> {
                    if (f.getItems().size() == s.getItems().size()) {
                        BigDecimal firstSum = BigDecimal.ZERO;
                        for (Item item : f.getItems()) {
                            firstSum = firstSum.add(item.getPrice());
                        }

                        BigDecimal secondSum = BigDecimal.ZERO;
                        for (Item item : s.getItems()) {
                            secondSum = secondSum.add(item.getPrice());
                        }

                        return secondSum.compareTo(firstSum);
                    }

                    return s.getItems().size() - f.getItems().size();
                })
                .collect(Collectors.toList());

        StringBuilder output = new StringBuilder();
        for (Category category : categories) {
            StringBuilder items = new StringBuilder();
            for (Item  item: category.getItems()) {
                items.append(String.format("--- Item Name: %s", item.getName())).append(System.lineSeparator())
                        .append(String.format("--- Item Price: %s", item.getPrice()))
                        .append(System.lineSeparator()).append(System.lineSeparator());
            }

            output.append(String.format("Category: %s", category.getName())).append(System.lineSeparator())
                    .append(items.toString());
        }

        return output.toString().trim();
    }
}
