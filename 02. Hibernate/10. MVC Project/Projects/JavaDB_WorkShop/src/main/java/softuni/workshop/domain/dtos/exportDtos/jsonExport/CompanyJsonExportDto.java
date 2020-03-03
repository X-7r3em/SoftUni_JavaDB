package softuni.workshop.domain.dtos.exportDtos.jsonExport;

import com.google.gson.annotations.Expose;

public class CompanyJsonExportDto {

    @Expose
    private String name;

    public CompanyJsonExportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
