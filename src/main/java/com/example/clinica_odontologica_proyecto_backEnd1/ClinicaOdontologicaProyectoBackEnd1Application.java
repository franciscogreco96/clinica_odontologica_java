package com.example.clinica_odontologica_proyecto_backEnd1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClinicaOdontologicaProyectoBackEnd1Application {
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().registerModule(new JavaTimeModule());
	}

	public static void main(String[] args) {
		SpringApplication.run(ClinicaOdontologicaProyectoBackEnd1Application.class, args);
	}

}
