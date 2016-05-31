package ru.kubsu.mms.core.db.models;

import javax.persistence.*;
import java.util.Date;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

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
