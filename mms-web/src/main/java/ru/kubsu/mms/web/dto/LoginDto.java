package ru.kubsu.mms.web.dto;

import java.io.Serializable;

/**
 * Created by DZRock on 31.03.2016.
 */
public class LoginDto implements Serializable {

    private String email;
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
