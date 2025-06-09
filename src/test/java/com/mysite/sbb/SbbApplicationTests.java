package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.category.CategoryService;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired
	private CategoryService categoryService;
	
	@Test
	void TestJpa() {
		this.categoryService.create("자유게시판");
	}

}
