package app.services;

import app.domain.dto.query3.CategoryCountDto;
import app.domain.dto.CategoryImport.CategoryDto;
import app.domain.dto.query3.CategoryCountWrapperDto;

import java.util.List;

public interface CategoryService {

    String seedCategories(List<CategoryDto> categoryDtos);

    CategoryCountWrapperDto categoriesByProductCount();
}
