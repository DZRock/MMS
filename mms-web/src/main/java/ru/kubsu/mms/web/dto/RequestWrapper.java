package ru.kubsu.mms.web.dto;

import java.io.Serializable;

/**
 * Created by DZRock on 11.04.2016.
 */
public class RequestWrapper<T> implements Serializable {

    private String date;
    private T content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RequestWrapper(T content) {
        this.content = content;
    }

    public RequestWrapper() {
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
