package com.chris_works.activedge.Arinze_Nafdac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.chris_works.activedge")
public class ArinzeNafdacApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArinzeNafdacApplication.class, args);
	}
}
