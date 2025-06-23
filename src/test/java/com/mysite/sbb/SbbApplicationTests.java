package com.mysite.sbb;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	void TestJpa() {
		for(int i=1;i<612;i++) {
			Optional<Question> oq=this.questionRepository.findById(i);
			if(oq.isPresent()) {
				Question question=oq.get();
				question.setViews(0);
				this.questionRepository.save(question);
			}
		}
	}

}
