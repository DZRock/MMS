package ru.kubsu.mms.core.db.models;

import javax.persistence.*;

/**
 * Created by fedor on 09.06.2016.
 */
@Entity
@Table(name = "files")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class File extends BasicEntity {

    private String path;
    private String type;
    private String contentType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
