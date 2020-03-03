package entities;

import orm.Annotations.Column;
import orm.Annotations.Entity;
import orm.Annotations.Id;

import java.sql.Date;


@Entity(name = "users")
public final class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private Date registrationDate;

    public User() {
        this.username = "";
        this.password = "";
        this.registrationDate = Date.valueOf("1900-01-01");
    }

    public User(String username, String password, int age, Date registrationDate) {
        this.setUsername(username);
        this.setPassword(password);
        this.setAge(age);
        this.setRegistrationDate(registrationDate);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %d %s", this.id, this.username, this.password, this.age, this.registrationDate);
    }
}
