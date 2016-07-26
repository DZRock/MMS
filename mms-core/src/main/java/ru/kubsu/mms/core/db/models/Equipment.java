package ru.kubsu.mms.core.db.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DZRock on 14.05.2016.
 */
@Entity
@Table(name = "equipments")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Equipment extends BasicEntity {

    private String model;
    @Temporal(TemporalType.DATE)
    private Date startUpDate;
    private String factoryNumber;
    @ManyToOne
    @JoinColumn(name = "location_id",referencedColumnName = "id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "vender_id",referencedColumnName = "id")
    private Vender vender;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "equipment")
    private List<TechControl> techControls =new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "equipment")
    private List<MetroControl> metroControls =new ArrayList<>();
    @Column(name = "inventory_number")
    private String inventoryNumber;

    public List<MetroControl> getMetroControls() {
        return metroControls;
    }

    public void setMetroControls(List<MetroControl> metroControls) {
        this.metroControls = metroControls;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getStartUpDate() {
        return startUpDate;
    }

    public void setStartUpDate(Date startUpDate) {
        this.startUpDate = startUpDate;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<TechControl> getTechControls() {
        return techControls;
    }

    public void setTechControls(List<TechControl> techControls) {
        this.techControls = techControls;
    }

    public Vender getVender() {
        return vender;
    }

    public void setVender(Vender vender) {
        this.vender = vender;
    }
}
