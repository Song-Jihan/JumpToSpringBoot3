package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.sbb.category.Category;
import com.mysite.sbb.user.SiteUser;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	Question findBySubject(String subject);
	
	Question findBySubjectAndContent(String subject, String content);

	List<Question> findBySubjectLike(String subject);

	Page<Question> findByAuthor(SiteUser siteuser,Pageable pageable);
	
	Page<Question> findAll(Pageable pageable);

	//Specification을 통한 Query문 자동 구현
	//Query 직접 구현이 어려우면 그냥 이거 쓰기
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	//Query문 직접 구현
	//join,distinct,or 조건이 많으면 @Query에 쓸 Pageable의 실행이 꼬임. >> count=1 이 되는 버그 발생
	@Query("select " 
			+ "distinct q " 
			+ "from Question q " 
			+ "left outer join SiteUser u1 on q.author=u1 "
			+ "left outer join Answer a on a.question=q " 
			+ "left outer join SiteUser u2 on a.author=u2 " 
			+ "where "
			+ "   q.subject like %:kw% " 
			+ "   or q.content like %:kw% " 
			+ "   or u1.username like %:kw% "
			+ "   or a.content like %:kw% " 
			+ "   or u2.username like %:kw% ")
	Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
	
	Page<Question> findByCategory(Category category,Pageable pageable);
}
