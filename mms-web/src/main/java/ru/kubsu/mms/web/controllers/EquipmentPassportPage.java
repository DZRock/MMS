package ru.kubsu.mms.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kubsu.mms.core.services.EquipmentService;
import ru.kubsu.mms.core.services.EquipmentServiceImpl;
import ru.kubsu.mms.web.dto.Wrapper;

/**
 * Created by fedor on 11.06.2016.
 */
@Controller
@RequestMapping("equipment")
public class EquipmentPassportPage {

    @Autowired
    private EquipmentService equipmentService;

    @ExceptionHandler(Exception.class)
    public Wrapper exceptionHandler(Exception ex){
        return new Wrapper<>("fatal",ex.getLocalizedMessage(),null);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String goToPassport(@RequestParam Long id, ModelMap model){
        model.addAttribute("equip",equipmentService.getEquipmentById(id));
        model.addAttribute("id",id);
        return "EquipmentPassportPage";
    }

}
