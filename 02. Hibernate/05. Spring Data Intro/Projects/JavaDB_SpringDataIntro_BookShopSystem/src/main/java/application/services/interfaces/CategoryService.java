package application.services.interfaces;

import application.entities.Category;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);
    boolean isEmpty();
    List<Category> getCategories();

}
