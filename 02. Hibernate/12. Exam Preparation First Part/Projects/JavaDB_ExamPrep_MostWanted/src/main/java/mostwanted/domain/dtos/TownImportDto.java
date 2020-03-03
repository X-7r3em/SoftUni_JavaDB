package mostwanted.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.persistence.Entity;

public class TownImportDto {
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
