package softuni.exam.domain.dtos.playerImport;

import com.google.gson.annotations.Expose;

public class PicturePlayerImportDto {
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
