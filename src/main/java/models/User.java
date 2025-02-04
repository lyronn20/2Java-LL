package models;

public class User {
    private int id;
    private String email;
    private String pseudo;
    private String password;
    private String role;

    public User(int id, String email, String pseudo, String password, String role) {
        this.id = id;
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
        this.role = role;
    }

    // Getters et Setters
    public int getId() { return id; }

    public String getRole() { return role; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
