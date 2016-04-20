package ru.kubsu.mms.web.dto;

/**
 * Created by DZRock on 09.04.2016.
 */
public class RegistrationUserDto extends UserDto {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
