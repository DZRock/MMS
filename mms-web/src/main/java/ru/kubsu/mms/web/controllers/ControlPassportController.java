package ru.kubsu.mms.web.controllers;

import org.apache.commons.io.IOUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kubsu.mms.core.db.models.File;
import ru.kubsu.mms.core.db.models.MetroControl;
import ru.kubsu.mms.core.db.models.TechControl;
import ru.kubsu.mms.core.db.repo.MetroControlRepo;
import ru.kubsu.mms.core.db.repo.TechCheckoutRepo;
import ru.kubsu.mms.core.db.repo.TechControlRepo;
import ru.kubsu.mms.core.services.EquipmentServiceImpl;
import ru.kubsu.mms.web.dto.MetroControlExtendedDTO;
import ru.kubsu.mms.web.dto.TechControlExtendedDTO;
import ru.kubsu.mms.web.dto.Wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by fedor on 06.06.2016.
 */
@Controller
@RequestMapping("passport")
public class ControlPassportController {


    @Autowired
    private EquipmentServiceImpl equipmentService;
    @Qualifier("techControlRepo")
    @Autowired
    private TechControlRepo techControlRepo;
    @Autowired
    private Mapper mapper;
    @Qualifier("metroControlRepo")
    @Autowired
    private MetroControlRepo metroControlRepo;
    @Qualifier("techCheckoutRepo")
    @Autowired
    private TechCheckoutRepo techCheckoutRepo;

    @ExceptionHandler(Exception.class)
    public Wrapper exceptionHandler(Exception ex){
        return new Wrapper<>("fatal",ex.getLocalizedMessage(),null);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String goToPassport(@RequestParam String type,@RequestParam Long id,ModelMap model){
        model.addAttribute("type",type);
        model.addAttribute("id",id);
        return "PassportPage";
    }

    @RequestMapping(value = "deleteControl",method = RequestMethod.GET)
    public String deleteControl(HttpServletRequest req,@RequestParam Long id, @RequestParam String type){
        if(!req.isUserInRole("ROLE_ADMIN"))return "ErrorPage";

        try{
            switch (type){
                case "to":
                    equipmentService.deleteTO(id);
                    break;
                case "mo":
                    equipmentService.deleteMO(id);
                    break;
            }
            return "redirect:/";
        }catch (Exception ex){
            return "ErrorPage";
        }
    }

    @RequestMapping(value = "changeStatus",method = RequestMethod.GET)
    public String changeStatus(HttpServletRequest req,@RequestParam Long id, @RequestParam String type){
        if(!req.isUserInRole("ROLE_ADMIN"))return "ErrorPage";

        try{
            switch (type){
                case "to":
                    equipmentService.changeTO(id);
                    break;
                case "mo":
                    equipmentService.changeMO(id);
                    break;
            }
            return "redirect:/passport/?id="+id+"&type="+type;
        }catch (Exception ex){
            return "ErrorPage";
        }
    }

    @RequestMapping(value = "getDatato",method = RequestMethod.POST)
    public @ResponseBody
    Wrapper getDatato(@RequestParam Long id){
        TechControl techControl = techControlRepo.findOne(id);
        TechControlExtendedDTO techControlExtendedDTO = mapper.map(techControl,TechControlExtendedDTO.class);
        return new Wrapper<>("success",null,techControlExtendedDTO);
    }

    @RequestMapping(value = "getDatamo",method = RequestMethod.POST)
    public @ResponseBody
    Wrapper getDatamo(@RequestParam Long id){
        MetroControl metroControl = metroControlRepo.findOne(id);
        MetroControlExtendedDTO metroControlExtendedDTO = mapper.map(metroControl,MetroControlExtendedDTO.class);
        return new Wrapper<>("success",null,metroControlExtendedDTO);
    }

    @RequestMapping(value = "getFile",method = RequestMethod.GET)
    public void getFile(HttpServletResponse response, @RequestParam Long id) throws IOException {
        File file = equipmentService.getFileById(id);

        response.setContentType(file.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename="+file.getName().replace(" ","_"));

        InputStream is = new FileInputStream(file.getPath());
        org.apache.commons.io.IOUtils.copy(is,response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value = "getXlsx", method = RequestMethod.GET)
    public void getXlsx(HttpServletResponse response, @RequestParam Long id, @RequestParam String type) throws IOException {
        byte[] xlsx=null;

        switch (type){
            case "mo":xlsx=equipmentService.generateExplodedDataMetro(id);
                break;
            case "to":xlsx=equipmentService.generateExplodedDataTech(id);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=OTCHET.xlsx");

        ByteArrayInputStream bis = new ByteArrayInputStream(xlsx);
        IOUtils.copy(bis,response.getOutputStream());

        response.flushBuffer();
    }
}
