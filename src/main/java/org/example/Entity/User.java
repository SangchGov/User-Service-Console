package org.example.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User() {

    }

    public User(String name, String email, int age, LocalDateTime createdAt){
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }

    //getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAge(int age) {
        this.age = age;
    }


    //equals and hash for id

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    //toString


    @Override
    public String toString() {
        return "User { " +
                "id=" + id + ", " +
                "name" + name + ", " +
                "email" + email + ", " +
                "age" + age + ", " +
                "created at" + createdAt + ", " +
                "}"
                ;
    }
}
