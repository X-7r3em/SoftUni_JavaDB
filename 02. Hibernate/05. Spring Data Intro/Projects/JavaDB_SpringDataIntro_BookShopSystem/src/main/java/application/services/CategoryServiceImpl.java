package application.services;

import application.entities.Category;
import application.repositories.CategoryRepository;
import application.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public boolean isEmpty() {
        return this.categoryRepository.count() == 0;
    }

    @Override
    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }
}
