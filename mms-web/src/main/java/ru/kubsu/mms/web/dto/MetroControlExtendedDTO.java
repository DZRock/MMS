package ru.kubsu.mms.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fedor on 06.06.2016.
 */
public class MetroControlExtendedDTO extends MetroControlDTO {

    private List<MetroControlDTO> checkouts = new ArrayList<>();

    public List<MetroControlDTO> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<MetroControlDTO> checkouts) {
        this.checkouts = checkouts;
    }
}
