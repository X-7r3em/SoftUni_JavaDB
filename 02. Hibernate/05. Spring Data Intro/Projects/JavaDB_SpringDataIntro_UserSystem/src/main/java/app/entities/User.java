package app.entities;

import app.annotations.Password;
import app.annotations.Email;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private LocalDate registeredOn;
    private LocalDate lastTimeLoggedIn;
    private int age;
    private boolean isDeleted;
    private Town birthTown;
    private Town currentTown;
    private String firstName;
    private String lastName;
    private Set<User> users;
    private Set<Album> albums;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false)
    @Size(min = 4, max = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Column(name = "password", nullable = false)
    @Password(minLength = 4, maxLength = 30, containsDigits = true,
            containsLowerCase = true, containsUpperCase = true, containsSpecialSymbol = true)
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) throws NoSuchMethodException {
        this.validatePassword(password);
        this.password = password;
    }

    @Column(name = "email", nullable = false)
    @Email(pattern = "^[a-zA-Z\\d]+[.\\-_]*[a-zA-Z\\d]+@[a-zA-Z\\d]+[\\.\\-_\\d\\w]*[a-zA-Z\\d]+$")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws NoSuchMethodException {
        this.validateEmail(email);
        this.email = email;
    }

    @Column(name = "registered_on", nullable = false)
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Column(name = "last_time_logged_in", nullable = false)
    public LocalDate getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDate lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }


    @Column(name = "age", nullable = false)
    @Min(1)
    @Max(120)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "is_deleted", nullable = false)
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @ManyToOne
    @JoinColumn(name = "birth_town_id", referencedColumnName = "id")
    public Town getBirthTown() {
        return birthTown;
    }

    public void setBirthTown(Town birthTown) {
        this.birthTown = birthTown;
    }

    @ManyToOne
    @JoinColumn(name = "current_town_id", referencedColumnName = "id")
    public Town getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(Town currentTown) {
        this.currentTown = currentTown;
    }

    @Column(name = "first_name")
    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public void setFullName(String fullName) {
        String[] names = fullName.split(" ");
        this.setFirstName(names[0]);
        this.setLastName(names[1]);
    }

    @ManyToMany
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fiend_id", referencedColumnName = "id"))
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany()
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    private void validatePassword(String password) throws NoSuchMethodException {
        Method method = this.getClass().getMethod("getPassword");

        if (method.getAnnotation(Password.class).maxLength() < password.length()){
            throw new IllegalArgumentException();

        }

        if (method.getAnnotation(Password.class).minLength() > password.length()){
            throw new IllegalArgumentException();
        }

        boolean isValid = true;
        if (method.getAnnotation(Password.class).containsDigits()) {
            isValid = !(password.replaceAll("[\\d]", "").length() == password.length());
        }

        if (!isValid){
            throw new IllegalArgumentException();
        }

        if (method.getAnnotation(Password.class).containsLowerCase()) {
            isValid = !(password.replaceAll("[a-z]", "").length() == password.length());
        }

        if (!isValid){
            throw new IllegalArgumentException();
        }

        if (method.getAnnotation(Password.class).containsUpperCase()) {
            isValid = !(password.replaceAll("[A-Z]", "").length() == password.length());
        }

        if (!isValid){
            throw new IllegalArgumentException();
        }

        if (method.getAnnotation(Password.class).containsUpperCase()) {
            isValid = !(password.replaceAll("[!@#$%^&*()_+<>?]", "").length() == password.length());
        }

        if (!isValid){
            throw new IllegalArgumentException();
        }

        //Абе знам че може и по-хитро ама ми се спи вече
    }

    private void validateEmail(String email) throws NoSuchMethodException {
        Method method = this.getClass().getMethod("getEmail");
        String pattern = method.getAnnotation(Email.class).pattern();

        if (!email.matches(pattern)){
            throw new IllegalArgumentException();
        }
    }
}
