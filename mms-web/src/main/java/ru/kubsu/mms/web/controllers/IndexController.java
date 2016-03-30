package ru.kubsu.mms.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by DZRock on 28.03.2016.
 */
@Controller
public class IndexController {

    

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String rootPath(ModelMap model){
        return "hello";
    }

}
