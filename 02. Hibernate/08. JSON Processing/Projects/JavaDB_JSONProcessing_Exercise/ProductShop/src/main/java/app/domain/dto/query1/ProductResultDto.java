package app.domain.dto.query1;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

public class ProductResultDto implements Serializable {
    @Expose
    @Size(min = 3)
    @NotNull
    private String name;

    @Expose
    @Min(0)
    @NotNull()
    private String price;

    @Expose
    @NotNull
    private String seller;

    public ProductResultDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
