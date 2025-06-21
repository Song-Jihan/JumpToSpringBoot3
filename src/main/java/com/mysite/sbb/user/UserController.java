package com.mysite.sbb.user;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final QuestionService questionService;;
	private final AnswerService answerService;
	private final JavaMailSender javaMailSender;

	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model) {

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

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}

		try {
			userService.create(userCreateForm.getUserName(), userCreateForm.getPassword1(), userCreateForm.getEmail());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "login_form";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/profile")
	public String profile(UserUpdateForm userUpdateForm,
			@RequestParam(value = "questionPage", defaultValue = "0") int questionPage,
			@RequestParam(value = "answerPage", defaultValue = "0") int answerPage, Model model, Principal principal) {
		SiteUser siteuser = this.userService.getUser(principal.getName());
		Page<Question> questionPaging = this.questionService.getListByAuthor(questionPage, siteuser);
		Page<Answer> commentPaging = this.answerService.getListByAuthor(answerPage, siteuser);
		model.addAttribute("username", siteuser.getUsername());
		model.addAttribute("userEmail", siteuser.getEmail());
		model.addAttribute("questionPaging", questionPaging);
		model.addAttribute("commentPaging", commentPaging);
		return "profile_form";
	}

	@GetMapping("/change_password")
	public String changePassword(Model model) {
		model.addAttribute("userUpdateForm", new UserUpdateForm());
		return "change_password_form";
	}

	@PostMapping("/change_password")
	public String changePassword(@Valid @ModelAttribute UserUpdateForm userUpdateForm, BindingResult bindingResult,
			Principal principal, Model model) {
		SiteUser user = this.userService.getUser(principal.getName());
		System.out.println("원본 비밀번호: " + userUpdateForm.getOriginPassword());
		System.out.println("비밀번호 변경 요청: " + userUpdateForm.getPassword1() + " → " + userUpdateForm.getPassword2());
		System.out.println("비밀번호 빈칸인가?: " + userUpdateForm.getOriginPassword().isBlank());
		System.out.println("비밀번호 변경 요청: " + this.userService.MatchPassword(userUpdateForm.getPassword1(), userUpdateForm.getPassword2()));
		
		if(userUpdateForm.getOriginPassword().isBlank() || userUpdateForm.getPassword2().isBlank()) {
			return "change_password_form";
		}
		
		if (!this.userService.MatchPassword(userUpdateForm.getOriginPassword(), user.getPassword())) {
			bindingResult.rejectValue("originPassword", "passwordInCorrect", "기존 비밀번호와 일치하지 않습니다.");
			return "change_password_form";
		}

		if (!userUpdateForm.getPassword1().equals(userUpdateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "확인 비밀번호가 일치하지 않습니다.");
			return "change_password_form";
		}

		try {
			this.userService.updatePassword(user, userUpdateForm.getPassword1());
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("updateFailed", e.getMessage());
		}

		return "redirect:/user/profile";
	}

	@GetMapping("/recovery_password")
	public String recoveryPassword(Model model) {
		model.addAttribute("sendConfirm", false);
		model.addAttribute("error", false);
		return "recovery_password_form";
	}

	@PostMapping("/recovery_password")
	public String recoveryPassword(@RequestParam(value = "email") String email, Model model) {
		try {
			SiteUser siteuser = this.userService.getUserByEmail(email);
			model.addAttribute("sendConfirm", true);
			model.addAttribute("userEmail", email);
			model.addAttribute("error", false);
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(email);
			simpleMailMessage.setSubject("계정 정보입니다.");
			StringBuilder sb = new StringBuilder();
			String newPassword = PasswordGenerator.generateRandomPassword();
			sb.append(siteuser.getUsername()).append(" 계정의 비밀번호를 초기화 했습니다.\n").append("새 비밀번호는 ").append(newPassword)
					.append(" 입니다.\n").append("해당 비밀번호로 로그인 후 비밀번호를 변경해주세요.");

			simpleMailMessage.setText(sb.toString());
			this.userService.updatePassword(siteuser, newPassword);
			new Thread(new Runnable() {
				@Override
				public void run() {
					javaMailSender.send(simpleMailMessage);
				}
			}).start();
		} catch (DataNotFoundException e) {
			model.addAttribute("sendConfirm", false);
			model.addAttribute("error", true);
		}
		return "recovery_password_form";
	}

	// 랜덤한 비밀번호 생성 메서드
	public static class PasswordGenerator {
		private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
		private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
		private static final String NUMBER = "0123456789";
		private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

		private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
		private static final int PASSWORD_LENGTH = 12;

		public static String generateRandomPassword() {
			if (PASSWORD_LENGTH < 1)
				throw new IllegalArgumentException("Password length must be at least 1");

			StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
			Random random = new SecureRandom();
			for (int i = 0; i < PASSWORD_LENGTH; i++) {
				int rndCharAt = random.nextInt(PASSWORD_ALLOW_BASE.length());
				char rndChar = PASSWORD_ALLOW_BASE.charAt(rndCharAt);
				sb.append(rndChar);
			}

			return sb.toString();
		}
	}
}
