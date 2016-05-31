package ru.kubsu.mms.core.db.models;

import javax.persistence.*;

/**
 * Created by DZRock on 14.05.2016.
 */
@Entity
@Table(name = "venders")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Vender extends BasicEntity {

    @Column(name = "country")
    private String country;

    public Vender(String name, String country) {
        super();
        setName(name);
        this.country=country;

    }

    public Vender() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
