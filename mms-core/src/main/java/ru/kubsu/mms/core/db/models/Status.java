package ru.kubsu.mms.core.db.models;

import javax.persistence.*;

/**
 * Created by DZRock on 14.05.2016.
 */
@Entity
@Table(name = "statuses")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Status extends BasicEntity {

    @Column(name="system_name")
    private String systemName;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
