package app.domain.dto.query4;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductResultQ4Dto implements Serializable {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductResultQ4Dto() {
    }

    public ProductResultQ4Dto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
