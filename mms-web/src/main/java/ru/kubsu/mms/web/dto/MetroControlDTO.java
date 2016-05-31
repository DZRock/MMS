package ru.kubsu.mms.web.dto;

/**
 * Created by DZRock on 17.05.2016.
 */
public class MetroControlDTO extends IdNameDto{

    private IdNameDto responsibleUser;
    private IdNameDto equipment;
    private Integer period;
    private String lastSupportDate;
    private IdNameDto status;
    private String systemStatus;

    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public IdNameDto getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(IdNameDto responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public IdNameDto getEquipment() {
        return equipment;
    }

    public void setEquipment(IdNameDto equipment) {
        this.equipment = equipment;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getLastSupportDate() {
        return lastSupportDate;
    }

    public void setLastSupportDate(String lastSupportDate) {
        this.lastSupportDate = lastSupportDate;
    }

    public IdNameDto getStatus() {
        return status;
    }

    public void setStatus(IdNameDto status) {
        this.status = status;
    }
}
