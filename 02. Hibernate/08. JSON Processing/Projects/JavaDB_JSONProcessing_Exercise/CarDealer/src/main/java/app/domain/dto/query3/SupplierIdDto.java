package app.domain.dto.query3;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SupplierIdDto implements Serializable {
    @Expose
    @NotNull
    private Integer id;

    @Expose
    @NotNull
    private String name;

    @Expose
    @NotNull
    private Integer count;

    public SupplierIdDto() {
    }

    public SupplierIdDto(@NotNull Integer id, @NotNull String name, @NotNull Integer count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
