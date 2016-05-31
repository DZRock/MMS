package ru.kubsu.mms.web.dto;

/**
 * Created by DZRock on 17.05.2016.
 */
public class TechControlDTO extends IdNameDto {

    private IdNameDto responsibleUser;
    private IdNameDto equipment;
    private Integer period;
    private String expendableMaterial;
    private String instruments;
    private String lastSupportDate;
    private IdNameDto status;
    private String systemStatus;

    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getLastSupportDate() {
        return lastSupportDate;
    }

    public void setLastSupportDate(String lastSupportDate) {
        this.lastSupportDate = lastSupportDate;
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

    public String getExpendableMaterial() {
        return expendableMaterial;
    }

    public void setExpendableMaterial(String expendableMaterial) {
        this.expendableMaterial = expendableMaterial;
    }

    public String getInstruments() {
        return instruments;
    }

    public void setInstruments(String instruments) {
        this.instruments = instruments;
    }

    public IdNameDto getStatus() {
        return status;
    }

    public void setStatus(IdNameDto status) {
        this.status = status;
    }
}
