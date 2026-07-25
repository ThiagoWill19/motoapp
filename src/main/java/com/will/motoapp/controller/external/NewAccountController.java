package com.will.motoapp.controller.external;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;

import com.will.motoapp.models.dto.user.CreateUserDto;
import com.will.motoapp.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/create-account")
@RequiredArgsConstructor
public class NewAccountController {

    private final UserService userService;

    @GetMapping
    public String showCreateAccountForm(Model model) {
        model.addAttribute("createUserDto", new CreateUserDto());
        return "public/createAccount";
    }

    @PostMapping
    public String createAccount(@Valid CreateUserDto createUserDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("registrationError", true);
            return "redirect:/login";
        }

        try {
            userService.create(createUserDto);
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorMessage", e.getMessage());
            redirectAttributes.addAttribute("registrationError", true);
            return "redirect:/login";
        }

        return "redirect:/login?success=true";
    }
    
    
}
