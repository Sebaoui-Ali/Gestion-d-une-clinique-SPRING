package org.sid.gc;

import java.util.Date;

import org.sid.gc.dao.PatientRepository;
import org.sid.gc.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GcApplication implements CommandLineRunner {

	@Autowired
	private PatientRepository patientRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(GcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		patientRepository.save(new Patient(null,"Hassan",new Date(),5));
//		patientRepository.save(new Patient(null,"Alioo",new Date(),6));
//		patientRepository.save(new Patient(null,"Ahmed",new Date(),7));
//		
//		patientRepository.findAll().forEach(p->{
//			System.out.println(p.getName());
//		});
//		
		System.out.println("Demarrage");
	}

}
