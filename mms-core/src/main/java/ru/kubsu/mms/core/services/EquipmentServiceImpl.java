package ru.kubsu.mms.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kubsu.mms.core.db.models.*;
import ru.kubsu.mms.core.db.repo.*;

import java.util.Date;
import java.util.List;

/**
 * Created by DZRock on 16.05.2016.
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    private static final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Qualifier("techControlRepo")
    @Autowired
    private TechControlRepo techControlRepo;
    @Qualifier("metroControlRepo")
    @Autowired
    private MetroControlRepo metroControlRepo;
    @Qualifier("equipmentRepo")
    @Autowired
    private EquipmentRepo equipmentRepo;
    @Qualifier("venderRepo")
    @Autowired
    private VenderRepo venderRepo;
    @Qualifier("statusRepo")
    @Autowired
    private StatusRepo statusRepo;
    @Qualifier("locationRepo")
    @Autowired
    private LocationRepo locationRepo;
    @Qualifier("userRepo")
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<Vender> getVenders(){
        return (List<Vender>) venderRepo.findAll();
    }

    @Override
    public List<Location> getLocations() {
        return (List<Location>) locationRepo.findAll();
    }

    @Override
    @Transactional
    public void addLocation(Location location) {
        location.setCreated(new Date());
        locationRepo.save(location);
    }

    @Override
    @Transactional
    public void addVender(Vender vender) {
        vender.setCreated(new Date());
        venderRepo.save(vender);
    }

    @Override
    @Transactional
    public void addEquipment(Equipment equipment) {
        equipment.setCreated(new Date());
        equipment.setVender(venderRepo.findOne(equipment.getVender().getId()));
        equipment.setLocation(locationRepo.findOne(equipment.getLocation().getId()));
        equipmentRepo.save(equipment);
    }

    @Override
    public List<Equipment> getEquipments() {
        return (List<Equipment>) equipmentRepo.findAll();
    }

    @Override
    @Transactional
    public void addTo(TechControl to) {
        to.setEquipment(equipmentRepo.findOne(to.getEquipment().getId()));
        to.setResponsibleUser(userRepo.findOne(to.getResponsibleUser().getId()));
        to.setCreated(new Date());
        to.setLastSupportdate(new Date());
        to.setStatus(statusRepo.findBySystemName("checkouted"));
        techControlRepo.save(to);
    }

    @Override
    public List<TechControl> getTOList() {
        return (List<TechControl>) techControlRepo.findAll();
    }

    @Override
    public List<MetroControl> getMOList() {
        return (List<MetroControl>) metroControlRepo.findAll();
    }

    @Override
    @Transactional
    public void addMo(MetroControl mo) {
        mo.setEquipment(equipmentRepo.findOne(mo.getEquipment().getId()));
        mo.setResponsibleUser(userRepo.findOne(mo.getResponsibleUser().getId()));
        mo.setCreated(new Date());
        mo.setLastSupportdate(new Date());
        mo.setStatus(statusRepo.findBySystemName("checkouted"));
        metroControlRepo.save(mo);
    }
}
