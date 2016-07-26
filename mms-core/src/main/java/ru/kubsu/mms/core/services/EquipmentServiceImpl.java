package ru.kubsu.mms.core.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kubsu.mms.core.db.models.*;
import ru.kubsu.mms.core.db.models.File;
import ru.kubsu.mms.core.db.repo.*;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DZRock on 16.05.2016.
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    private static final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);
    @Qualifier("techCheckoutRepo")
    @Autowired
    private TechCheckoutRepo techCheckoutRepo;
    @Qualifier("metroCheckoutRepo")
    @Autowired
    private MetroCheckoutRepo metroCheckoutRepo;
    @Qualifier("fileRepo")
    @Autowired
    private FileRepo fileRepo;
    @Qualifier("controlOrganisationRepo")
    @Autowired
    private ControlOrganisationRepo controlOrganisationRepo;

    @PostConstruct
    private void initStatuses(){
        List<Status> statuses = (List<Status>) statusRepo.findAll();
        if(statuses.size()<1){
            Status status=new Status();
            status.setName("Поверено");
            status.setSystemName("checkouted_mo");
            status.setCreated(new Date());

            statusRepo.save(status);

            Status status_ = new Status();
            status_.setName("Требует поверки");
            status_.setSystemName("need_checkout_mo");
            status_.setCreated(new Date());

            statusRepo.save(status_);

            Status _status_ = new Status();
            _status_.setName("Требует проверки");
            _status_.setSystemName("need_checkout_to");
            _status_.setCreated(new Date());

            statusRepo.save(_status_);

            Status __status_ = new Status();
            __status_.setName("Проверено");
            __status_.setSystemName("checkouted_to");
            __status_.setCreated(new Date());

            statusRepo.save(__status_);
        }
    }

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
        to.setStatus(statusRepo.findBySystemName("checkouted_to"));
        to.setOrganisation(controlOrganisationRepo.findOne(to.getOrganisation().getId()));
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
        mo.setStatus(statusRepo.findBySystemName("checkouted_mo"));
        mo.setOrganisation(controlOrganisationRepo.findOne(mo.getOrganisation().getId()));
        metroControlRepo.save(mo);
    }

    @Override
    public List<TechControl> getTOList(User currentUser) {
        return techControlRepo.findByResponsibleUser(currentUser);
    }

    @Override
    public List<MetroControl> getMOList(User currentUser) {
        return metroControlRepo.findByResponsibleUser(currentUser);
    }

    @Override
    @Transactional
    public void processTOCheck(User currentUser, Long id, String msg, List<File> files) {
        TechCheckout techCheckout = new TechCheckout();
        techCheckout.setResponsibleUser(currentUser);
        techCheckout.setComment(msg);
        techCheckout.setCheckoutDate(new Date());
        techCheckout.setFiles(files);

        TechControl techControl = techControlRepo.findOne(id);
        techControl.setStatus(statusRepo.findBySystemName("checkouted_to"));
        techControl.setLastSupportdate(new Date());

        techCheckout.setTechControl(techControlRepo.findOne(id));
        techCheckoutRepo.save(techCheckout);

        techControlRepo.save(techControl);
    }

    @Override
    @Transactional
    public void processMOCheck(User currentUser, Long id, String msg, List<File> files) {
        MetroCheckout metroCheckout = new MetroCheckout();
        metroCheckout.setCheckoutDate(new Date());
        metroCheckout.setComment(msg);
        metroCheckout.setResponsibleUser(currentUser);
        metroCheckout.setFiles(files);

        MetroControl metroControl = metroControlRepo.findOne(id);
        metroControl.setStatus(statusRepo.findBySystemName("checkouted_mo"));
        metroControl.setLastSupportdate(new Date());

        metroCheckout.setMetroControl(metroControl);
        metroCheckoutRepo.save(metroCheckout);

        metroControlRepo.save(metroControl);
    }

    @Override
    public File processMultipartFile(String name, String contentType, java.io.File file){
        File rFile = new File();
        rFile.setName(name);
        rFile.setCreated(new Date());
        rFile.setContentType(contentType);
        rFile.setPath(file.getPath());
        return rFile;
    }

    @Override
    public File getFileById(Long id) {
        return fileRepo.findOne(id);
    }

    @Override
    public byte[] generateExplodedDataTech(Long id) throws IOException {
        TechControl techControl = techControlRepo.findOne(id);
        Workbook workbook = new XSSFWorkbook("C:/mms/tech_control.xlsx");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Sheet sheet = workbook.getSheetAt(0);

        int rowIndex = 1;
        int cellIndex = 0;

        Row row = sheet.createRow(rowIndex++);

        Cell indexCell = row.createCell(cellIndex++);
        indexCell.setCellType(Cell.CELL_TYPE_STRING);
        indexCell.setCellValue(techControl.getId().toString());

        Cell stateCell = row.createCell(cellIndex++);
        stateCell.setCellType(Cell.CELL_TYPE_STRING);
        stateCell.setCellValue(techControl.getStatus().getName());

        Cell dateCell = row.createCell(cellIndex++);
        dateCell.setCellValue(simpleDateFormat.format(techControl.getCreated()));

        Cell lastSupport = row.createCell(cellIndex++);
        lastSupport.setCellValue(simpleDateFormat.format(techControl.getLastSupportdate()));

        Cell matCell = row.createCell(cellIndex++);
        matCell.setCellValue(techControl.getExpendableMaterial());

        Cell insCell = row.createCell(cellIndex++);
        insCell.setCellValue(techControl.getInstruments());

        Cell periodCell = row.createCell(cellIndex++);
        periodCell.setCellValue(techControl.getPeriod());

        Cell respCell = row.createCell(cellIndex++);
        respCell.setCellValue(techControl.getResponsibleUser().getName());

        Cell eqNameCell = row.createCell(cellIndex++);
        eqNameCell.setCellValue(techControl.getEquipment().getName());

        Cell modelCell = row.createCell(cellIndex++);
        modelCell.setCellValue(techControl.getOrganisation().getName());

        Cell facCell = row.createCell(cellIndex++);
        facCell.setCellValue(techControl.getEquipment().getFactoryNumber());

        rowIndex=5;
        cellIndex=0;
        for(TechCheckout checkout:techControl.getTechCheckouts()){
            Row chRow= sheet.createRow(rowIndex++);
            Cell msg = chRow.createCell(cellIndex++);
            msg.setCellValue(checkout.getComment());

            Cell date = chRow.createCell(cellIndex++);
            date.setCellValue(simpleDateFormat.format(checkout.getCheckoutDate()));

            Cell resp = chRow.createCell(cellIndex++);
            resp.setCellValue(checkout.getResponsibleUser().getName());
            cellIndex=0;
        }

//        FileOutputStream fileOutputStream = new FileOutputStream("C:/tmp/tesst.xlsx");
//        workbook.write(fileOutputStream);
//        fileOutputStream.flush();
//        fileOutputStream.close();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        bos.close();
        return bos.toByteArray();
    }

    @Override
    public byte[] generateExplodedDataMetro(Long id) throws IOException {
        MetroControl techControl = metroControlRepo.findOne(id);
        Workbook workbook = new XSSFWorkbook("C:/mms/metro_control.xlsx");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Sheet sheet = workbook.getSheetAt(0);

        int rowIndex = 1;
        int cellIndex = 0;

        Row row = sheet.createRow(rowIndex++);

        Cell indexCell = row.createCell(cellIndex++);
        indexCell.setCellType(Cell.CELL_TYPE_STRING);
        indexCell.setCellValue(techControl.getId().toString());

        Cell stateCell = row.createCell(cellIndex++);
        stateCell.setCellType(Cell.CELL_TYPE_STRING);
        stateCell.setCellValue(techControl.getStatus().getName());

        Cell dateCell = row.createCell(cellIndex++);
        dateCell.setCellValue(simpleDateFormat.format(techControl.getCreated()));

        Cell lastSupport = row.createCell(cellIndex++);
        lastSupport.setCellValue(simpleDateFormat.format(techControl.getLastSupportdate()));

        Cell periodCell = row.createCell(cellIndex++);
        periodCell.setCellValue(techControl.getPeriod());

        Cell respCell = row.createCell(cellIndex++);
        respCell.setCellValue(techControl.getResponsibleUser().getName());

        Cell eqNameCell = row.createCell(cellIndex++);
        eqNameCell.setCellValue(techControl.getEquipment().getName());

        Cell modelCell = row.createCell(cellIndex++);
        modelCell.setCellValue(techControl.getOrganisation().getName());

        Cell facCell = row.createCell(cellIndex++);
        facCell.setCellValue(techControl.getEquipment().getFactoryNumber());

        rowIndex=5;
        cellIndex=0;
        for(MetroCheckout checkout:techControl.getMetroCheckouts()){
            Row chRow= sheet.createRow(rowIndex++);
            Cell msg = chRow.createCell(cellIndex++);
            msg.setCellValue(checkout.getComment());

            Cell date = chRow.createCell(cellIndex++);
            date.setCellValue(simpleDateFormat.format(checkout.getCheckoutDate()));

            Cell resp = chRow.createCell(cellIndex++);
            resp.setCellValue(checkout.getResponsibleUser().getName());
            cellIndex=0;
        }

//        FileOutputStream fileOutputStream = new FileOutputStream("C:/tmp/tesst.xlsx");
//        workbook.write(fileOutputStream);
//        fileOutputStream.flush();
//        fileOutputStream.close();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        bos.close();
        return bos.toByteArray();
    }

    @Override
    @Transactional
    public void parseXlsxTO(java.io.File fileTmp) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(fileTmp.getPath());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Sheet sheet = workbook.getSheetAt(0);

        DataFormat format = workbook.createDataFormat();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(format.getFormat("@"));

        sheet.setDefaultColumnStyle(0,style);

        List<TechControl> controls = new ArrayList<>();

        Iterator<Row> rows = sheet.iterator();
        rows.next();
        while (rows.hasNext()){
            Row row = rows.next();

            int cellIndex=0;
            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);

            if(row.getCell(cellIndex).getStringCellValue().equals(""))break;

            TechControl techControl = new TechControl();

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            techControl.setName(row.getCell(cellIndex++).getStringCellValue());

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            techControl.setEquipment(equipmentRepo.findByInventoryNumber(row.getCell(cellIndex++).getStringCellValue()));

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            techControl.setResponsibleUser(userRepo.findByEmail(row.getCell(cellIndex++).getStringCellValue()));
//            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            techControl.setCreated(row.getCell(cellIndex++).getDateCellValue());
            techControl.setLastSupportdate(row.getCell(cellIndex++).getDateCellValue());
            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            techControl.setExpendableMaterial(row.getCell(cellIndex++).getStringCellValue());

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            techControl.setInstruments(row.getCell(cellIndex++).getStringCellValue());
//            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            techControl.setPeriod((int) row.getCell(cellIndex++).getNumericCellValue());

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            techControl.setOrganisation(controlOrganisationRepo.findByCode(row.getCell(cellIndex++).getStringCellValue()));

            techControl.setStatus(statusRepo.findBySystemName("checkouted_to"));

            controls.add(techControl);
        }

        techControlRepo.save(controls);
    }

    @Override
    @Transactional
    public void parseXlsxMO(java.io.File fileTmp) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(fileTmp.getPath());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Sheet sheet = workbook.getSheetAt(0);

        DataFormat format = workbook.createDataFormat();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(format.getFormat("@"));

        sheet.setDefaultColumnStyle(0,style);

        List<MetroControl> controls = new ArrayList<>();

        Iterator<Row> rows = sheet.iterator();
        rows.next();
        while (rows.hasNext()){
            Row row = rows.next();

            int cellIndex=0;
            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);

            if(row.getCell(cellIndex).getStringCellValue().equals(""))break;

            MetroControl metroControl = new MetroControl();

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            metroControl.setName(row.getCell(cellIndex++).getStringCellValue());

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            metroControl.setEquipment(equipmentRepo.findByInventoryNumber(row.getCell(cellIndex++).getStringCellValue()));

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            metroControl.setResponsibleUser(userRepo.findByEmail(row.getCell(cellIndex++).getStringCellValue()));
//            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            metroControl.setCreated(row.getCell(cellIndex++).getDateCellValue());
            metroControl.setLastSupportdate(row.getCell(cellIndex++).getDateCellValue());
            metroControl.setPeriod((int) row.getCell(cellIndex++).getNumericCellValue());

            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
            metroControl.setOrganisation(controlOrganisationRepo.findByCode(row.getCell(cellIndex++).getStringCellValue()));

            metroControl.setStatus(statusRepo.findBySystemName("checkouted_mo"));

            controls.add(metroControl);
        }

        metroControlRepo.save(controls);
    }

    @Override
    public List<ControlOrganisation> getOrganisations() {
        return (List<ControlOrganisation>) controlOrganisationRepo.findAll();
    }

    @Override
    public void addOrganisation(ControlOrganisation controlOrganisation) {
        controlOrganisationRepo.save(controlOrganisation);
    }

    @Override
    @Transactional
    public void deleteMO(Long id) {
        MetroControl metroControl = metroControlRepo.findOne(id);
        for(MetroCheckout checkout : metroControl.getMetroCheckouts()){
            for(File file:checkout.getFiles()){
                java.io.File fle = new java.io.File(file.getPath());
                fle.delete();
            }
        }
        metroControlRepo.delete(metroControl);
    }

    @Override
    @Transactional
    public void deleteTO(Long id) {
        TechControl techControl = techControlRepo.findOne(id);
        for(TechCheckout checkout : techControl.getTechCheckouts()){
            for(File file:checkout.getFiles()){
                java.io.File fle = new java.io.File(file.getPath());
                fle.delete();
            }
        }
        techControlRepo.delete(techControl);
    }

    @Override
    @Transactional
    public void changeTO(Long id) {
        TechControl techControl = techControlRepo.findOne(id);
        switch (techControl.getStatus().getSystemName()){
            case "need_checkout_to":
                techControl.setStatus(statusRepo.findBySystemName("checkouted_to"));
                break;
            case "checkouted_to":
                techControl.setStatus(statusRepo.findBySystemName("need_checkout_to"));
                break;
        }
        techControlRepo.save(techControl);
    }

    @Override
    @Transactional
    public void changeMO(Long id) {
        MetroControl metroControl = metroControlRepo.findOne(id);
        switch (metroControl.getStatus().getSystemName()){
            case "need_checkout_mo":
                metroControl.setStatus(statusRepo.findBySystemName("checkouted_mo"));
                break;
            case "checkouted_mo":
                metroControl.setStatus(statusRepo.findBySystemName("need_checkout_mo"));
                break;
        }
        metroControlRepo.save(metroControl);
    }

    @Override
    public Equipment getEquipmentById(Long id) {
        return equipmentRepo.findOne(id);
    }

    @Override
    public ControlOrganisation findOrg(Long id) {
        return controlOrganisationRepo.findOne(id);
    }

    @Override
    public List<TechControl> findTechControlsByOrg(Long id) {
        return techControlRepo.findByOrganisation_Id(id);
    }

    @Override
    public List<MetroControl> findMetroControlsByOrg(Long id) {
        return metroControlRepo.findByOrganisation_Id(id);
    }
}
