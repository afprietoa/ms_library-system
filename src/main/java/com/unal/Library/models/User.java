package com.unal.Library.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private static int idCounter = 0;
    private Integer id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String role;

    public User(String name, String nickname, String email, String password, String role) {
        idCounter++;
        this.id = idCounter;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setId() {
        idCounter++;
        this.id = idCounter;
    }
}