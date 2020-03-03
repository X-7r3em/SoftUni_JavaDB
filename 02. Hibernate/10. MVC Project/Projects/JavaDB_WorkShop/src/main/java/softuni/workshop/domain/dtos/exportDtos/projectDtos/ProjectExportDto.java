package softuni.workshop.domain.dtos.exportDtos.projectDtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProjectExportDto implements Serializable {
    private String name;
    private String description;
    private BigDecimal payment;

    public ProjectExportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return String.format("Project name: %s%n" +
                "    Description: %s%n" +
                "   %s", this.getName(), this.getDescription(), this.getPayment());
    }
}
