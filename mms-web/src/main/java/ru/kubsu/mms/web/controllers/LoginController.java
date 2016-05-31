package ru.kubsu.mms.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kubsu.mms.web.dto.LoginDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by DZRock on 31.03.2016.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.GET)
    public String login(ModelMap modelMap) {
        return "LoginPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doLogin(@RequestParam LoginDto loginDto, RedirectAttributes redirectAttributes) {
        if ("".equals(loginDto.getPassword()) || "".equals(loginDto.getEmail()))
            return "redirect:/login";
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(null, loginDto));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (UsernameNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorText", "Некорректное имя пользователя или пароль");
            return "redirect:/login";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest req) throws ServletException {
        req.logout();
        return "redirect:/login";
    }

}