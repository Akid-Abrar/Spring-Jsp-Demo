package com.crud_login_register.spring_login_register_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ComponentScan("com.crud_login_register.spring_login_register_demo")
public class SpringLoginRegisterDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLoginRegisterDemoApplication.class, args);
	}

}
