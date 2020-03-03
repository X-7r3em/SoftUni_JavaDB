package softuni.exam.domain.dtos.teamImport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class TeamImportWrapperDto {
    @XmlElement(name = "team")
    private List<TeamImportDto> teams;

    public List<TeamImportDto> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamImportDto> teams) {
        this.teams = teams;
    }
}
