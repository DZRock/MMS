package ru.kubsu.mms.web.dto;

import java.io.Serializable;

/**
 * Created by DZRock on 03.04.2016.
 */
public class IdNameDto implements Serializable {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
