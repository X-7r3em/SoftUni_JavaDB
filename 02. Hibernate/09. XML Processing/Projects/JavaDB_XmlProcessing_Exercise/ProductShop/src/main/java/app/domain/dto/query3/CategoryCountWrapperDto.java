package app.domain.dto.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoryCountWrapperDto {

    @XmlElement(name = "category")
    private List<CategoryCountDto> categories;

    public CategoryCountWrapperDto() {
    }

    public CategoryCountWrapperDto(List<CategoryCountDto> categories) {
        this.categories = categories;
    }

    public List<CategoryCountDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryCountDto> categories) {
        this.categories = categories;
    }
}
