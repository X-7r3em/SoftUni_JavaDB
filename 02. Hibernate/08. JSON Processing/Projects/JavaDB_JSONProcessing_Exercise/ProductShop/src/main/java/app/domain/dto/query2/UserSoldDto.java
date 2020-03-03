package app.domain.dto.query2;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class UserSoldDto implements Serializable {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private ProductBuyerDto[] soldProducts;

    public UserSoldDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ProductBuyerDto[] getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductBuyerDto[] soldProducts) {
        this.soldProducts = soldProducts;
    }
}
