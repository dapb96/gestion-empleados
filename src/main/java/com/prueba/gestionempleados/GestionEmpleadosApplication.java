package com.prueba.gestionempleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.gestionempleados")
public class GestionEmpleadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEmpleadosApplication.class, args);
	}

}
