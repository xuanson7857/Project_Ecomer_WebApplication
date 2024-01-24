package com.ra.controller.admin;

import com.ra.dto.response.ResponseUserLoginDTO;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping(value= {"/", ""})
    public String index(Model model){
        return "admin/indexadmin";
    }

}
