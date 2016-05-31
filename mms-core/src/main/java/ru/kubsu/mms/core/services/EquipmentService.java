package ru.kubsu.mms.core.services;

import ru.kubsu.mms.core.db.models.*;

import java.util.List;

/**
 * Created by DZRock on 16.05.2016.
 */
public interface EquipmentService {
    List<Vender> getVenders();

    void addLocation(Location location);

    void addVender(Vender vender);

    void addEquipment(Equipment equipment);

    List<Location> getLocations();

    List<Equipment> getEquipments();

    void addTo(TechControl to);

    List<TechControl> getTOList();

    List<MetroControl> getMOList();

    void addMo(MetroControl map);
}
