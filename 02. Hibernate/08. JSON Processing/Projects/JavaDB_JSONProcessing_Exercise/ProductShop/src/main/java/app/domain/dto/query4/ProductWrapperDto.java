package app.domain.dto.query4;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ProductWrapperDto implements Serializable {
    @Expose
    private Integer count;
    @Expose
    private ProductResultQ4Dto[] products;

    public ProductWrapperDto() {
    }

    public ProductWrapperDto(Integer count, ProductResultQ4Dto[] products) {
        this.count = count;
        this.products = products;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ProductResultQ4Dto[] getProducts() {
        return products;
    }

    public void setProducts(ProductResultQ4Dto[] products) {
        this.products = products;
    }
}
