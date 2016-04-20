package ru.kubsu.mms.web.dto;

/**
 * Created by DZRock on 02.04.2016.
 */
public class Wrapper<T>  {

    private String type;
    private String text;
    private T content;

    public Wrapper(String type, String text, T content) {
        this.type = type;
        this.text = text;
        this.content = content;
    }

    public Wrapper(T content) {
        this.content = content;
        this.setType("success");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

}
