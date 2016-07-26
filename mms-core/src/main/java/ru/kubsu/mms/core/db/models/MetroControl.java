package ru.kubsu.mms.core.db.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DZRock on 14.05.2016.
 */
@Entity
@Table(name = "metro_controls")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class MetroControl extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "responsible_user_id",referencedColumnName = "id")
    private User responsibleUser;
    @Column(name = "period")
    private int period;
    @Column(name = "last_support_date")
    @Temporal(TemporalType.DATE)
    private Date lastSupportdate;
    @ManyToOne
    @JoinColumn(name = "status_id",referencedColumnName = "id")
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;
    @OneToMany(mappedBy = "metroControl",cascade = CascadeType.ALL)
    private List<MetroCheckout> metroCheckouts = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "control_organisation_id")
    private ControlOrganisation organisation;

    public ControlOrganisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(ControlOrganisation organisation) {
        this.organisation = organisation;
    }

    public List<MetroCheckout> getMetroCheckouts() {
        return metroCheckouts;
    }

    public void setMetroCheckouts(List<MetroCheckout> metroCheckouts) {
        this.metroCheckouts = metroCheckouts;
    }

    public User getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(User responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Date getLastSupportdate() {
        return lastSupportdate;
    }

    public void setLastSupportdate(Date lastSupportdate) {
        this.lastSupportdate = lastSupportdate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
