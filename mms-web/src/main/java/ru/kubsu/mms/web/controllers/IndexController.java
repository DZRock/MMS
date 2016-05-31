package ru.kubsu.mms.web.controllers;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kubsu.mms.core.db.models.*;
import ru.kubsu.mms.core.db.repo.UserRepo;
import ru.kubsu.mms.core.services.EquipmentService;
import ru.kubsu.mms.core.services.EquipmentServiceImpl;
import ru.kubsu.mms.web.dto.*;

import java.util.ArrayList;
import java.util.List;
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
    public Wrapper exceptionHandler(Exception ex){
        return new Wrapper<>("fatal",ex.getLocalizedMessage(),null);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String rootPath(ModelMap model){
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
    public @ResponseBody Wrapper getTOList(){
        List response = new ArrayList<>();
        equipmentService.getTOList().forEach(techControl -> response.add(dozerMapper.map(techControl,TechControlDTO.class)));
        return new Wrapper<>("success",null,response);
    }

    @RequestMapping(value = "getMOList")
    public @ResponseBody Wrapper getMOList(){
        List response = new ArrayList<>();
        equipmentService.getMOList().forEach(metroControl -> response.add(dozerMapper.map(metroControl,MetroControlDTO.class)));
        return new Wrapper<>("success",null,response);
    }
}
