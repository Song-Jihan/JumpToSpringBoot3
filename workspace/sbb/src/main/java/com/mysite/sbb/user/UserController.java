package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult,Model model) {
		
		List<String> fieldOrder = List.of("userName", "password1", "password2", "email");

		List<String> orderedErrors = new ArrayList<>();

		for (String field : fieldOrder) {
		    List<FieldError> fieldErrors = bindingResult.getFieldErrors(field);
		    for (FieldError error : fieldErrors) {
		        orderedErrors.add(error.getDefaultMessage());
		    }
		}
		
		if (!orderedErrors.isEmpty()) {
	        model.addAttribute("orderedErrors", orderedErrors);
	        return "signup_form";
	    }
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		
		try {
			userService.create(userCreateForm.getUserName(), userCreateForm.getPassword1(), userCreateForm.getEmail());
		}
		catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
			return "signup_form";
		}
		catch(Exception e){
			e.printStackTrace();
			bindingResult.reject("signupFailed",e.getMessage());
			return "signup_form";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
}
