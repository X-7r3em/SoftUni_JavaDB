package softuni.exam.domain.dtos.playerImport;

import com.google.gson.annotations.Expose;

public class PictureTeamPlayerImportDto {
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
