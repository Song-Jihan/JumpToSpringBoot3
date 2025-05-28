package com.mysite.sbb.comment;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	private final CommentService commentService;
	
	//Create
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/createCommentOnQuestion/{id}")
	public String createCommentOnQuestion(Model model,
			@PathVariable("id")Integer id,
			@ModelAttribute("commentForm") @Valid CommentForm commentForm,
			BindingResult bindingResult,Principal principal) {
		Question question=this.questionService.getQuestion(id);
		SiteUser siteUser=this.userService.getUser(principal.getName());
		if(bindingResult.hasErrors()) {
			model.addAttribute("question",question);
			model.addAttribute("commentForm",commentForm);
			model.addAttribute("org.springframework.validation.BindingResult.commentForm", bindingResult);
			return "question_detail";
		}
		Comment comment=this.commentService.createOnQuestion(question, commentForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s#comment_%s", comment.getQuestion().getId(), comment.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/createCommentOnAnswer/{id}")
	public String createCommentOnAnswer(Model model,@PathVariable("id")Integer id,
			@ModelAttribute("commentForm") @Valid CommentForm commentForm,
			BindingResult bindingResult,Principal principal) {
		Answer answer=this.answerService.getAnswer(id);
		SiteUser siteUser=this.userService.getUser(principal.getName());
		if(bindingResult.hasErrors()) {
			model.addAttribute("answer",answer);
			model.addAttribute("commentForm",commentForm);
			model.addAttribute("org.springframework.validation.BindingResult.commentForm", bindingResult);
			return "question_detail";
		}
		Comment comment=this.commentService.createOnAnswer(answer, commentForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s#comment_%s", comment.getAnswer().getQuestion().getId(), comment.getAnswer());
	}
	
	//ModifyOnQuestion
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modifyCommentOnQuestion/{id}")
	public String modifyCommentOnQuestion(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		Comment comment=this.commentService.getCommentOnQuestion(id);
		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerForm.setContent(comment.getContent());
		return "answer_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modifyCommentOnQuestion/{id}")
	public String modifyCommentOnQuestion(@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "answer_form";
		}
		Comment comment=this.commentService.getCommentOnQuestion(id);
		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.commentService.modify(comment, answerForm.getContent());
		return String.format("redirect:/question/detail/%s#answer_%s", comment.getQuestion().getId(), comment.getId());
	}
	
	//ModifyOnAnswer
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modifyCommentOnAnswer/{id}")
	public String modifyCommentOnAnswer(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		Comment comment=this.commentService.getCommentOnAnswer(id);
		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerForm.setContent(comment.getContent());
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modifyCommentOnAnswer/{id}")
	public String modifyCommentOnAnswer(@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "answer_form";
		}
		Comment comment=this.commentService.getCommentOnAnswer(id);
		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.commentService.modify(comment, answerForm.getContent());
		return String.format("redirect:/question/detail/%s#answer_%s", comment.getAnswer().getQuestion().getId(), comment.getId());
	}
	
	//deleteOnQuestion
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/deleteCommentOnQuestion/{id}")
	public String deleteCommentOnQuestion(Principal principal, @PathVariable("id") Integer id) {
		Comment comment=this.commentService.getCommentOnQuestion(id);
		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.commentService.delete(comment);
		return String.format("redirect:/question/detail/%s#comment_%s", comment.getQuestion().getId(), comment.getId());
	}
	
	//deleteOnAnswer
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/deleteCommentOnAnswer/{id}")
	public String deleteCommentOnAnswer(Principal principal, @PathVariable("id") Integer id) {
		Comment comment=this.commentService.getCommentOnAnswer(id);
		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.commentService.delete(comment);
		return String.format("redirect:/question/detail/%s#comment_%s", comment.getAnswer().getQuestion().getId(), comment.getId());
	}
	
	//voteOnQuestion
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/voteCommentOnQuestion/{id}")
	public String voteCommentOnQuestion(Principal principal, @PathVariable("id") Integer id) {
		Comment comment=this.commentService.getCommentOnQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.commentService.vote(comment, siteUser);
		return String.format("redirect:/question/detail/%s", comment.getQuestion().getId(), comment.getId());
	}
	
	//voteOnAnswer
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/voteCommentOnAnswer/{id}")
	public String voteCommentOnAnswer(Principal principal, @PathVariable("id") Integer id) {
		Comment comment=this.commentService.getCommentOnAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.commentService.vote(comment, siteUser);
		return String.format("redirect:/question/detail/%s", comment.getAnswer().getQuestion().getId());
	}
}
