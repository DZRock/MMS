package ru.kubsu.mms.core.db.models;

import javax.persistence.*;

/**
 * Created by fedor on 26.06.2016.
 */
@Entity
@Table(name = "control_organisation")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class ControlOrganisation extends BasicEntity{

    @Column(name = "address")
    private String address;
    @Column(name = "code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
