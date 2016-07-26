package ru.kubsu.mms.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fedor on 06.06.2016.
 */
public class TechCheckoutDTO extends IdNameDto{

    private String comment;
    private IdNameDto responsibleUser;
    private String checkoutDate;
    private List<IdNameDto> files = new ArrayList<>();

    public List<IdNameDto> getFiles() {
        return files;
    }

    public void setFiles(List<IdNameDto> files) {
        this.files = files;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public IdNameDto getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(IdNameDto responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
