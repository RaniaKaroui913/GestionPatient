package com.example.springmvc;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springmvc.entities.Patient;
import com.example.springmvc.repositories.Patientrep;


@SpringBootApplication
public class SpringMvcApplication implements CommandLineRunner {
	@Autowired
	private Patientrep patient;
	public static void main(String[] args) {
		SpringApplication.run(SpringMvcApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		patient.save(new Patient(null,"patient1",new Date(),2300,false));
		patient.save(new Patient(null,"patient2",new Date(),7800,false));
		patient.save(new Patient(null,"patient3",new Date(),500,true));
		
		patient.findAll().forEach(p->{System.out.println(p.toString());});	
		System.out.println("*******************************");
		Patient p=patient.findById(3L).get();
		System.out.println(p.toString());
	}

}
