package com.ra.controller.user;

import com.ra.dto.request.UserLoginDTO;
import com.ra.dto.request.UserRegisterDTO;
import com.ra.dto.response.ResponseUserLoginDTO;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("userRegisterDTO", userRegisterDTO);
        return "users/register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
                               BindingResult result){
        if(!result.hasErrors()){
            if(userService.register(userRegisterDTO)){
                return "redirect:/login";
            }
        }
        return "users/register";
    }





    @GetMapping("/login")
    public String login(@CookieValue(required = false, name = "email") String emailCookie, Model model){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        // check cookei có không
        if(emailCookie != null){
            userLoginDTO.setEmail(emailCookie);
            model.addAttribute("checked",true);
        }

        model.addAttribute("userLoginDTO", userLoginDTO);
        return "users/login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("userLoginDTO") UserLoginDTO UserLoginDTO,
                            Model model,
                            HttpSession httpSession,
                            @RequestParam( required = false, name = "checked") Boolean isCheck,
                            HttpServletResponse response, HttpServletRequest request){

        ResponseUserLoginDTO user2 = userService.login(UserLoginDTO);
        if(isCheck != null && isCheck){
            // khởi tạo cookie
            Cookie cookieEmail = new Cookie("email",UserLoginDTO.getEmail());
            // sét thời gian tồn tại cookei.
            cookieEmail.setMaxAge(72*60*60);
            response.addCookie(cookieEmail);
        }else {
            Cookie[] cookie = request.getCookies();
            if(cookie != null){
                for (int i = 0; i < cookie.length ; i++) {
                    if(cookie[i].getName().equals("email")){
                        cookie[i].setMaxAge(0);
                        // Đặt lại giá trị của cookie trong response để nó được gửi về client và xóa đi
                        response.addCookie(cookie[i]);
                        break;
                    }
                }
            }

        }

        if(user2 != null && user2.getStatus() == 0){
            httpSession.setAttribute("username", user2);
            model.addAttribute("message", "Đăng nhập thành công");
            return "redirect:/";
        }
        if(user2 != null && user2.getStatus() == 1){
            model.addAttribute("message", "Tài khoản bị khoá");
            return  "users/login";
        }
        model.addAttribute("message", "Email hoặc mật khẩu không chính xác");
        return  "users/login";

    }


//logout
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("username");
        session.removeAttribute("cart");
        return "redirect:/";
    }


}
