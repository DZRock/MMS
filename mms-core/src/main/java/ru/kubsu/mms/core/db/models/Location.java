package ru.kubsu.mms.core.db.models;

import javax.persistence.*;

/**
 * Created by DZRock on 14.05.2016.
 */
@Entity
@Table(name = "locations")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Location extends BasicEntity {

    public Location(String name) {
        super();
        this.setName(name);
    }

    public Location() {
    }
}
