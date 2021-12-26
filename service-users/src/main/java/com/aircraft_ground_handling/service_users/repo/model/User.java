package com.aircraft_ground_handling.service_users.repo.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
public final class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "ENUM('CABIN_CREW', 'AIRPORT_MANAGER')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType type;

    @NotNull
    private String name;

    public User() {
    }

    public User(UserType type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
