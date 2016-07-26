package ru.kubsu.mms.web.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by fedor on 06.06.2016.
 */
public class TechControlExtendedDTO extends TechControlDTO {

    private List<TechCheckoutDTO> checkouts = new ArrayList<>();

    public List<TechCheckoutDTO> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<TechCheckoutDTO> checkouts) {
        this.checkouts = checkouts;
    }
}
