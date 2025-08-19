package com.mysite.sbb;

import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbbApplication {

	public static void main(String[] args) {

		try {
            ClassLoader classLoader = SbbApplication.class.getClassLoader();
            // Hibernate의 핵심 클래스 중 하나의 실제 위치를 찾습니다.
            URL resource = classLoader.getResource("org/hibernate/Session.class");
            System.out.println("### Hibernate Session 클래스 위치: " + resource + " ###");
        } catch (Exception e) {
            e.printStackTrace();
        }
		SpringApplication.run(SbbApplication.class, args);
	}

}
