package app.domain.dto.query5;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class CustomerResultDto implements Serializable {
    @Expose
    private String fullName;

    @Expose
    private BigInteger boughtCars;

    @Expose
    private BigDecimal spentMoney;

    public CustomerResultDto() {
    }

    public CustomerResultDto(String fullName, BigInteger boughtCars, BigDecimal spentMoney) {
        this.fullName = fullName;
        this.boughtCars = boughtCars;
        this.spentMoney = spentMoney;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigInteger getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(BigInteger boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }
}
