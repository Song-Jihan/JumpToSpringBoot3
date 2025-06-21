package com.mysite.sbb.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	
	Page<Answer> findAllByQuestion(Question question, Pageable pageable);

	Page<Answer> findByAuthor(SiteUser siteuser, Pageable pageable);

	// 추천순으로 답변 정렬
	@Query("SELECT a "
			+"FROM Answer a "
			+"LEFT JOIN a.voter v "
			+"WHERE a.question.id = :questionId "
			+"GROUP BY a ORDER BY COUNT(v) DESC, a.createDate DESC, a.id DESC")
	Page<Answer> findByQuestionIdOrderedByVoteCount(@Param("questionId") Integer questionId, Pageable pageable);
}
