package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.category.Category;
import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=200)
	private String subject;
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy="question",cascade=CascadeType.REMOVE)
	private List<Answer> answerList;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Comment> commentList;
	
	@ManyToOne
	public SiteUser author;
	
	private LocalDateTime modifyDate;
	
	@ManyToMany
	Set<SiteUser> voter;
	
	@ManyToOne
	private Category category;
	
	private LocalDateTime lastAnswerTime;	
	
	private LocalDateTime lastCommentTime;

	public void updateLastAnswerTime() {
		this.lastAnswerTime=LocalDateTime.now();
	}
	
	public void updateLastCommentTime() {
		this.lastCommentTime=LocalDateTime.now();
	}
}
