package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	
	//这个主程序可以直接运行，然后可以在浏览器输入http://localhost:8080/hello看到效果。不需要使用Tomcat

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
