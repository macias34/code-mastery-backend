package com.macias34.codemastery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

@SpringBootApplication
public class CodeMasteryApplication {

	public static void main(String[] args) {
		Dotenv dotenv = new DotenvBuilder().load();
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});


		SpringApplication.run(CodeMasteryApplication.class, args);
	}

}
