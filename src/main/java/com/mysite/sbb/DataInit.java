package com.mysite.sbb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysite.sbb.category.CategoryService;

@Configuration
public class DataInit{
	@Bean
	public CommandLineRunner initData(CategoryService categoryService) {
		return args -> {categoryService.create("자유게시판");
		categoryService.create("질문게시판");};
	}
}

