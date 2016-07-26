package ru.kubsu.mms.web.dto;

/**
 * Created by fedor on 26.06.2016.
 */
public class OrganisationDTO extends IdNameDto {

    private String address;
    private String code;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
