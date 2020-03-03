package app.domain.dto.query4;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class UserResultDto implements Serializable {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;

    @Expose
    private ProductWrapperDto soldProducts;

    public UserResultDto() {
    }

    public UserResultDto(String firstName, String lastName, Integer age, ProductWrapperDto soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductWrapperDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductWrapperDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
