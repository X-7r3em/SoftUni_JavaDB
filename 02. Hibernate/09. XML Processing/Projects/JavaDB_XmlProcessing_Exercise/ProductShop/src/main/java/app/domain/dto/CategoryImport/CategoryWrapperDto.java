package app.domain.dto.CategoryImport;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoryWrapperDto {

    @XmlElement(name = "category")
    private List<CategoryDto> categories;

    public CategoryWrapperDto() {
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
