package com.mysite.sbb.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	
	public Comment createOnQuestion(Question question,String content,SiteUser author) {
		Comment comment=new Comment();
		comment.setQuestion(question);
		comment.setContent(content);
		comment.setAuthor(author);
		comment.setCreateDate(LocalDateTime.now());
		this.commentRepository.save(comment);
		return comment;
	}
	
	public Comment getCommentOnQuestion(Integer id) {
		Optional<Comment> comment=this.commentRepository.findById(id);
		if(comment.isPresent()) {
			return comment.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public Comment createOnAnswer(Answer answer,String content,SiteUser author) {
		Comment comment=new Comment();
		comment.setAnswer(answer);
		comment.setContent(content);
		comment.setAuthor(author);
		comment.setCreateDate(LocalDateTime.now());
		this.commentRepository.save(comment);
		return comment;
	}
	
	public Comment getCommentOnAnswer(Integer id) {
		Optional<Comment> comment=this.commentRepository.findById(id);
		if(comment.isPresent()) {
			return comment.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}
	
	public void modify(Comment comment,String content) {
		comment.setContent(content);
		comment.setModifyDate(LocalDateTime.now());
		this.commentRepository.save(comment);
	}
	
	public void delete(Comment comment) {
		this.commentRepository.delete(comment);
	}
	
	public void vote(Comment comment,SiteUser siteUser) {
		comment.getVoter().add(siteUser);
		this.commentRepository.save(comment);
	}
	
	public List<Comment> findByUserId(Long userId){
		return commentRepository.findByAuthor_Id(userId);
	}
}
