package com.mysite.sbb.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mysite.sbb.question.Question;

public interface AnswerRepository extends JpaRepository<Answer,Integer>{
	//추천순으로 답변 정렬
	Page<Answer> findAllByQuestion(Question question, Pageable pageable);
}
