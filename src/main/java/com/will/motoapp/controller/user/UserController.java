package com.will.motoapp.controller.user;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.will.motoapp.models.dto.user.ResponseUserDto;
import com.will.motoapp.security.CustomUserDetails;
import com.will.motoapp.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping
    public String showUserView(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        UUID userId = userDetails.getId();

        ResponseUserDto userDto = userService.getUserById(userId);

        model.addAttribute("user", userDto);

        return "user/inicialPage";
    }
    

}
