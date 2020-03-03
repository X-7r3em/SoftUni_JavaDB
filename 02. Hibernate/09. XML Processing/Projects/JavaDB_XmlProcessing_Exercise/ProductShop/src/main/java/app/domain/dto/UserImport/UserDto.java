package app.domain.dto.UserImport;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UserDto implements Serializable {
    @XmlAttribute(name = "first-name")
    private String firstName;

    @NotNull
    @Size(min = 3, message = "Min length is 3.")
    @XmlAttribute(name = "last-name")
    private String lastName;

    @NotNull
    @Min(value = 0, message = "Min age is 0.")
    @XmlAttribute(name = "age")
    private int age;

    public UserDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
