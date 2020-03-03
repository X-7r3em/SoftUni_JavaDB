package app.services;

import app.domain.dto.query3.CategoryCountDto;
import app.domain.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    String seedCategories(CategoryDto[] categoryDtos);

    List<CategoryCountDto> categoriesByProductCount();
}
