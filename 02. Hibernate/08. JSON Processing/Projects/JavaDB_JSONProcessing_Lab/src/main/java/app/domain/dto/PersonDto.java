package app.domain.dto;

import app.domain.model.Address;
import com.google.gson.annotations.Expose;

public class PersonDto {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private AddressDto address;

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

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
