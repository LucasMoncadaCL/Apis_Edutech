package com.edutech.ubicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.edutech"})
public class UbicacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbicacionApplication.class, args);
	}

}
