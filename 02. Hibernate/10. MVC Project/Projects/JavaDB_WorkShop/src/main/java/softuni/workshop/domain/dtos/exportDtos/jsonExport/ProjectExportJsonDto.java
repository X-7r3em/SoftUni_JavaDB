package softuni.workshop.domain.dtos.exportDtos.jsonExport;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProjectExportJsonDto implements Serializable {
    @Expose
    private String name;

    @Expose
    private String description;

    @Expose
    private String startDate;

    @Expose
    private Boolean isFinished;

    @Expose
    private BigDecimal payment;

    @Expose
    private CompanyJsonExportDto company;

    public ProjectExportJsonDto() {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public CompanyJsonExportDto getCompany() {
        return company;
    }

    public void setCompany(CompanyJsonExportDto company) {
        this.company = company;
    }
}
