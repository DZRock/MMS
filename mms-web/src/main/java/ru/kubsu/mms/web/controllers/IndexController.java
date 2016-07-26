package ru.kubsu.mms.web.controllers;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.kubsu.mms.core.db.models.*;
import ru.kubsu.mms.core.db.models.File;
import ru.kubsu.mms.core.db.repo.UserRepo;
import ru.kubsu.mms.core.services.EquipmentService;
import ru.kubsu.mms.core.services.EquipmentServiceImpl;
import ru.kubsu.mms.web.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by DZRock on 28.03.2016.
 */
@Controller
public class IndexController {


    @Qualifier("equipmentServiceImpl")
    @Autowired
    private EquipmentService equipmentService;
    @Qualifier("dozerMapper")
    @Autowired
    private Mapper dozerMapper;
    @Qualifier("userRepo")
    @Autowired
    private UserRepo userRepo;

    @ExceptionHandler(Exception.class)
    public @ResponseBody Wrapper exceptionHandler(Exception ex){
        return new Wrapper<>("fatal",ex.getLocalizedMessage(),null);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String rootPath(ModelMap model) throws IOException {
        return "HomePage";
    }

    @RequestMapping(value = "/getVenders")
    public @ResponseBody
    Wrapper getVenders(){
        List response = new ArrayList<>();
        equipmentService.getVenders().forEach(vender -> response.add(dozerMapper.map(vender, IdNameDto.class)));
        return new Wrapper<>("success",null,response);
    }
    @RequestMapping(value = "/getLocations")
    public @ResponseBody
    Wrapper getLocations(){
        List response = new ArrayList<>();
        equipmentService.getLocations().forEach(location -> response.add(dozerMapper.map(location, IdNameDto.class)));
        return new Wrapper<>("success",null,response);
    }

    @RequestMapping(value = "/getOrganisations")
    public @ResponseBody Wrapper getOrganisations(){
        List response = new ArrayList<>();
        equipmentService.getOrganisations().forEach(controlOrganisation -> response.add(dozerMapper.map(controlOrganisation,IdNameDto.class)));
        return new Wrapper<>("success",null,response);
    }

    @RequestMapping(value = "addLocation",method = RequestMethod.POST)
    public @ResponseBody Wrapper addLocation(@RequestParam String name){
        equipmentService.addLocation(new Location(name));
        return new Wrapper<>("success","Месторасположение добавлено",null);
    }

    @RequestMapping(value = "addVender",method = RequestMethod.POST)
    public @ResponseBody Wrapper addVender(@RequestParam String name, @RequestParam String country){
        equipmentService.addVender(new Vender(name,country));
        return new Wrapper<>("success","Производитель добавлен",null);
    }

    @RequestMapping(value = "addEquipment",method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Wrapper addEquipment(@RequestBody EquipmentDTO equipmentDTO){
        equipmentService.addEquipment(dozerMapper.map(equipmentDTO, Equipment.class));
        return new Wrapper<>("success","Оборудование добавлено",null);
    }

    @RequestMapping(value = "addOrganisation", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Wrapper addOrganisation(@RequestBody OrganisationDTO organisationDTO){
        ControlOrganisation controlOrganisation = dozerMapper.map(organisationDTO,ControlOrganisation.class);
        equipmentService.addOrganisation(controlOrganisation);
        return new Wrapper<>("success","Организация добавлена",null);
    }

    @RequestMapping(value = "addTo",method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Wrapper addTo(@RequestBody TechControlDTO techControlDTO){
        equipmentService.addTo(dozerMapper.map(techControlDTO, TechControl.class));
        return new Wrapper<>("success","Техническое обслуживание запущено",null);
    }

    @RequestMapping(value = "addMo",method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Wrapper addMo(@RequestBody MetroControlDTO metroControlDTO){
        equipmentService.addMo(dozerMapper.map(metroControlDTO, MetroControl.class));
        return new Wrapper<>("success","Метрологическое обслуживание запущено",null);
    }

    @RequestMapping(value = "getUsersMinimal")
    public @ResponseBody Wrapper getUsersMinimal(){
        List response = new ArrayList<>();
        userRepo.findAll().forEach(user -> response.add(dozerMapper.map(user,IdNameDto.class)));
        return new Wrapper<>("success",null,response);
    }

    @RequestMapping(value = "getEquipmentsMinimal")
    public @ResponseBody Wrapper getEquipmentsMinimal(){
        List response = new ArrayList<>();
        equipmentService.getEquipments().forEach(equipment -> response.add(dozerMapper.map(equipment,IdNameDto.class)));
        return new Wrapper<>("success",null,response);
    }

    @RequestMapping(value = "getTOList")
    public @ResponseBody Wrapper getTOList(HttpServletRequest req,@RequestParam String sort){
        List response = new ArrayList<>();
        List<TechControl> data = new ArrayList<>();
        if(req.isUserInRole("ROLE_ADMIN")){
            data = equipmentService.getTOList();
            if(sort.equals("up")){
                data.sort((o1, o2) -> o1.getLastSupportdate().compareTo(o2.getLastSupportdate()));
            }else{
                data.sort((o1, o2) -> o1.getLastSupportdate().compareTo(o2.getLastSupportdate())*(-1));
            }
            data.forEach(techControl -> response.add(dozerMapper.map(techControl,TechControlDTO.class)));
        }else{
            data = equipmentService.getTOList(getCurrentUser());
            if(sort.equals("up")){
                data.sort((o1, o2) -> o1.getLastSupportdate().compareTo(o2.getLastSupportdate()));
            }else{
                data.sort((o1, o2) -> o1.getLastSupportdate().compareTo(o2.getLastSupportdate())*(-1));
            }
            data.forEach(techControl -> response.add(dozerMapper.map(techControl,TechControlDTO.class)));
        }
        return new Wrapper<>("success",null,response);
    }

    @RequestMapping(value = "getMOList")
    public @ResponseBody Wrapper getMOList(HttpServletRequest req,@RequestParam String sort){
        List response = new ArrayList<>();
        List<MetroControl> data = new ArrayList<>();
        if(req.isUserInRole("ROLE_ADMIN")){
            data = equipmentService.getMOList();
            if(sort.equals("up")){
                data.sort((o1, o2) -> o1.getLastSupportdate().compareTo(o2.getLastSupportdate()));
            }else{
                data.sort((o1, o2) -> o1.getLastSupportdate().compareTo(o2.getLastSupportdate())*(-1));
            }
            data.forEach(metroControl -> response.add(dozerMapper.map(metroControl,MetroControlDTO.class)));
        }else{
            data = equipmentService.getMOList();
            if(sort.equals("up")){
                data.sort((o1, o2) -> o1.getLastSupportdate().compareTo(o2.getLastSupportdate()));
            }else{
                data.sort((o1, o2) -> o1.getLastSupportdate().compareTo(o2.getLastSupportdate())*(-1));
            }
            data.forEach(metroControl -> response.add(dozerMapper.map(metroControl,MetroControlDTO.class)));
        }
        return new Wrapper<>("success",null,response);
    }

    @RequestMapping(value = "check",method = RequestMethod.POST)
    public @ResponseBody Wrapper check(@RequestParam(value = "check-id") Long id,
                                       @RequestParam(value = "check-msg") String msg,
                                       @RequestParam(value = "check-type") String type, MultipartHttpServletRequest request){
        String responseMessage="";
        List<File> files = new ArrayList<>();
        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        for(MultipartFile multipartFile:map.get("files[]")) {
            try {
                files.add(equipmentService.processMultipartFile(multipartFile.getOriginalFilename(),
                        multipartFile.getContentType(),
                        convert(multipartFile)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (type){
            case "to": equipmentService.processTOCheck(getCurrentUser(),id,msg,files);
                       responseMessage = "Оборудование успешно проверено";
                        break;
            case "mo": equipmentService.processMOCheck(getCurrentUser(),id,msg,files);
                        responseMessage = "Оборудование успешно поверено";
                        break;
        }
        return new Wrapper<>("success",responseMessage,null);
    }

    @RequestMapping(value = "getToXlsxTemplate",method = RequestMethod.GET)
    public @ResponseBody void uploadXlsxTO(HttpServletResponse response) throws IOException {
        java.io.File file = new java.io.File("C:/mms/tech_controls.xlsx");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=template.xlsx");

        InputStream is = new FileInputStream(file.getPath());
        org.apache.commons.io.IOUtils.copy(is,response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value = "getMoXlsxTemplate",method = RequestMethod.GET)
    public @ResponseBody void uploadXlsxMO(HttpServletResponse response) throws IOException {
        java.io.File file = new java.io.File("C:/mms/metro_controls.xlsx");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=template.xlsx");

        InputStream is = new FileInputStream(file.getPath());
        org.apache.commons.io.IOUtils.copy(is,response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value = "uploadToXlsx", method = RequestMethod.POST)
    public @ResponseBody Wrapper uploadToXlsx(@RequestParam  MultipartFile file) throws IOException, ParseException {
        java.io.File fileTmp = convert(file);
        equipmentService.parseXlsxTO(fileTmp);

        return new Wrapper<>("success","Файл успешно загружен",null);
    }

    @RequestMapping(value = "uploadMoXlsx", method = RequestMethod.POST)
    public @ResponseBody Wrapper uploadMoXlsx(@RequestParam MultipartFile file) throws IOException, ParseException{
        java.io.File fileTmp = convert(file);
        equipmentService.parseXlsxMO(fileTmp);

        return new Wrapper<>("success","Файл успешно загружен",null);
    }

    @RequestMapping(value = "organisation/{id}",method = RequestMethod.GET)
    public String organisation(@PathVariable Long id, ModelMap model){
        try{
            ControlOrganisation controlOrganisation = equipmentService.findOrg(id);
            model.addAttribute("org",controlOrganisation);
            model.addAttribute("techControls",equipmentService.findTechControlsByOrg(id));
            model.addAttribute("metroControls",equipmentService.findMetroControlsByOrg(id));

            return "OrganisationPage";
        }catch (Exception ex){
            return "ErrorPage";
        }
    }

    private java.io.File convert(MultipartFile file) throws IOException {
        String separator = java.io.File.separator;

        java.io.File convFile = new java.io.File("C:"+separator+"mms"+separator+file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
