package ru.kubsu.mms.web.controllers;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kubsu.mms.core.db.models.User;
import ru.kubsu.mms.core.services.UserService;
import ru.kubsu.mms.web.dto.RegistrationUserDto;
import ru.kubsu.mms.web.dto.Wrapper;

/**
 * Created by DZRock on 09.04.2016.
 */
@Controller
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private Mapper dozerMapepr;
    @Autowired
    private UserService userService;

    @ExceptionHandler(Throwable.class)
    public Wrapper<String> exceptionHandler(Throwable t) {
        return new Wrapper<>("fatal",t.getLocalizedMessage(),null);
    }

    @RequestMapping(value = "registration",method = RequestMethod.GET)
    public String registrationPage(){
        return "RegistrationPage";
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public String registration(RegistrationUserDto userDto, ModelMap model){
        try{
            userService.registerUser(dozerMapepr.map(userDto,User.class));
            return "LoginPage";
        }catch (Exception ex){
            model.put("error",ex.getLocalizedMessage());
            return "ErrorPage";
        }
    }
}
