package ru.kubsu.mms.web.dto;

import java.io.Serializable;

/**
 * Created by DZRock on 09.04.2016.
 */
public class UserDto extends IdNameDto implements Serializable{

    private String email;
    private Short sex;
    private String birthDate;
    private String address;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
