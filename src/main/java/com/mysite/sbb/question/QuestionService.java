package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.category.Category;
import com.mysite.sbb.category.CategoryService;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final CategoryService categoryService;
	
	private Specification<Question> search(String kw,Integer categoryId){
		return new Specification<>(){
			private static final long serialVersionUID=1L;
			@Override
			public Predicate toPredicate(Root<Question> q,CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);
				Join<Question,SiteUser> u1=q.join("author",JoinType.LEFT);
				Join<Question,Answer> a=q.join("answerList",JoinType.LEFT);
				Join<Question,Category> c=q.join("category",JoinType.LEFT);
				Join<Answer,SiteUser> u2=a.join("author",JoinType.LEFT);
				return cb.and(cb.or(cb.like(q.get("subject"),"%"+kw+"%"),
						cb.like(q.get("content"),"%"+kw+"%"),
						cb.like(u1.get("username"),"%"+kw+"%"),
						cb.like(a.get("content"),"%"+kw+"%"),
						cb.like(u2.get("username"),"%"+kw+"%")),
						//and
						cb.equal(c.get("id"),categoryId));
			}
		};
	}
	
	public Page<Question> getListByKeyword(int page,String kw,Integer categoryId){
		List<Sort.Order> sorts=new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		Pageable pageable=PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec=search(kw,categoryId);
		//Specification을 통한 Query문 자동 구현
		return this.questionRepository.findAll(spec,pageable);
		
		//Query문 직접 구현
		//return this.questionRepository.findAllByKeyword(kw, pageable);
	}
	
	public Page<Question> getListByAuthor(int page,SiteUser siteuser){
		List<Sort.Order> sorts=new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		Pageable pageable=PageRequest.of(page, 5, Sort.by(sorts));
		return this.questionRepository.findByAuthor(siteuser,pageable);
	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		}
		else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String subject,String content,SiteUser author,Category category) {
		Question q=new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(author);
		q.setCategory(category);
		this.questionRepository.save(q);
	}
	
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	public void modify(Question question, String subject, String content) {
		question.setContent(content);
		question.setSubject(subject);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	public void vote(Question question,SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	
	public Page<Question> getListSorted(int page,String kw,String sort){
		Pageable pageable=PageRequest.of(page, 10, getSortingCriteria(sort));
		Specification<Question> spec=search(kw,categoryService.getCategoryByName("질문게시판").getId());
		return this.questionRepository.findAll(spec,pageable);
	}
	
	public Page<Question> getListByCategoryAndSorted(int page,String kw,Integer categoryId,String sort){
		Pageable pageable=PageRequest.of(page, 10, getSortingCriteria(sort));
		Specification<Question> spec=search(kw,categoryId);
		return this.questionRepository.findAll(spec,pageable);
	}
	
	public void viewsUp(Integer id) {
		Optional<Question> oq=this.questionRepository.findById(id);
		if(oq.isPresent()) {
			Question question=oq.get();
			question.setViews(question.getViews()+1);
			this.questionRepository.save(question);
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	private Sort getSortingCriteria(String sortKey) {
		switch(sortKey) {
			case "recentAnswer":
				return Sort.by(Sort.Direction.DESC,"lastAnswerTime");
			case "recentComment":
				return Sort.by(Sort.Direction.DESC,"lastCommentTime");
			case "ByView":
				return Sort.by(Sort.Direction.DESC,"views");
			default:
				return Sort.by(Sort.Direction.DESC,"id");
		}
	}
	
}
