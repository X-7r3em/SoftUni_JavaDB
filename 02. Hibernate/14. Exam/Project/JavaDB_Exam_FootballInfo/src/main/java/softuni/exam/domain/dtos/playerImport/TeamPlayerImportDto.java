package softuni.exam.domain.dtos.playerImport;

import com.google.gson.annotations.Expose;

public class TeamPlayerImportDto {
    @Expose
    private String name;

    @Expose
    private PictureTeamPlayerImportDto picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureTeamPlayerImportDto getPicture() {
        return picture;
    }

    public void setPicture(PictureTeamPlayerImportDto picture) {
        this.picture = picture;
    }
}
