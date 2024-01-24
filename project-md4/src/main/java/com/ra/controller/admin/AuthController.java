package com.ra.controller.admin;

import com.ra.dto.request.UserLoginDTO;
import com.ra.dto.response.ResponseUserLoginDTO;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @GetMapping("/lg-admin")
    public String lgadmin(Model model){
        UserLoginDTO user = new UserLoginDTO();
        model.addAttribute("user", user);
        return "admin/loginadmin";
    }

    @PostMapping("/lg-admin")
    public String postLogon(@ModelAttribute("user") UserLoginDTO user, HttpSession httpSession){
        ResponseUserLoginDTO userAdmin = userService.loginAdmin(user);
        if(userAdmin != null){
            httpSession.setAttribute("admin",userAdmin);
            return "redirect:/admin";
        }
        return "redirect:/lg-admin";
    }


    @GetMapping("/logoutadmin")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/";
    }
}
