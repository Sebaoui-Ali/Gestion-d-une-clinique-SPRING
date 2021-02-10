package org.sid.gc.controller;
import java.util.List;
import org.sid.gc.dao.PatientRepository;
import org.sid.gc.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PatientRestController {
	
	@Autowired
	private PatientRepository patientRepository;

	
	@GetMapping("/listePatients")
	public List<Patient> list(){
		return patientRepository.findAll();
	}
	
	@GetMapping("/patient/{id}")
	public Patient getOne(@PathVariable int id){
		return patientRepository.findById(id).get();
	}
}






