package ru.kubsu.mms.web.dto;

/**
 * Created by DZRock on 16.05.2016.
 */
public class EquipmentDTO extends IdNameDto{

    private String model;
    private String startUpDate;
    private IdNameDto vender;
    private IdNameDto location;
    private String factoryNumber;
    private String inventoryNumber;

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public IdNameDto getLocation() {
        return location;
    }

    public void setLocation(IdNameDto location) {
        this.location = location;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStartUpDate() {
        return startUpDate;
    }

    public void setStartUpDate(String startUpDate) {
        this.startUpDate = startUpDate;
    }

    public IdNameDto getVender() {
        return vender;
    }

    public void setVender(IdNameDto vender) {
        this.vender = vender;
    }
}
