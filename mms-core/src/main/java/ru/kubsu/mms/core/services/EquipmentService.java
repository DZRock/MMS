package ru.kubsu.mms.core.services;

import ru.kubsu.mms.core.db.models.*;
import ru.kubsu.mms.core.db.models.File;

import java.io.IOException;
import java.text.ParseException;
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

    List<TechControl> getTOList(User currentUser);

    List<MetroControl> getMOList(User currentUser);

    void processTOCheck(User currentUser, Long id, String msg, List<File> files);

    void processMOCheck(User currentUser, Long id, String msg, List<File> files);

    File processMultipartFile(String name, String contentType, java.io.File file);

    File getFileById(Long id);

    byte[] generateExplodedDataTech(Long id) throws IOException;

    byte[] generateExplodedDataMetro(Long id) throws IOException;

    void parseXlsxTO(java.io.File fileTmp) throws IOException, ParseException;

    Equipment getEquipmentById(Long id);

    void parseXlsxMO(java.io.File fileTmp) throws IOException, ParseException;

    List<ControlOrganisation> getOrganisations();

    void addOrganisation(ControlOrganisation controlOrganisation);

    void deleteMO(Long id);
    void deleteTO(Long id);

    void changeTO(Long id);
    void changeMO(Long id);

    ControlOrganisation findOrg(Long id);

    List<TechControl> findTechControlsByOrg(Long id);
    List<MetroControl> findMetroControlsByOrg(Long id);
}
