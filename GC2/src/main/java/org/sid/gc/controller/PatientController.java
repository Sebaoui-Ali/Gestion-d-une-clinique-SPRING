package org.sid.gc.controller;

import javax.validation.Valid;
import java.util.List;
import org.sid.gc.dao.PatientRepository;
import org.sid.gc.dao.UserRepository;
import org.sid.gc.entities.Patient;
import org.sid.gc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PatientController {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path ="/index")
	public String index() {
		return "index";
	}
	
	@GetMapping(path="/patients")
	public String List(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
//		List<Patient> patients = patientRepository.findAll();
		Page<Patient> pagePatients = patientRepository.findByNomContains(mc, PageRequest.of(page, size));
		model.addAttribute("patients", pagePatients.getContent());
		model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "patients";
	}
	
	@GetMapping(path ="/deletePatient")
	public String delete(int id,String keyword,int page,int size) {
		patientRepository.deleteById(id);
		return "redirect:/patients?page="+page+"&size="+size+"&keyword="+keyword;
	}
	
	@GetMapping(path ="/deletePatient2")
	public String delete2(int id,String keyword,int page,int size,Model model) {
		patientRepository.deleteById(id);
		return List(model, page, size, keyword);
	}
	
	@GetMapping("/formPatient")
	public String formPatient(Model model) {
		model.addAttribute("patient", new Patient());
		List<User> docteurs = userRepository.getMedecins();
		model.addAttribute("mode", "new");
		model.addAttribute("docteurs",docteurs);
		return "formPatient";
	}
	
	@PostMapping("/savePatient")
	public String savePatient(@Valid Patient patient, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "formPatient";
		patientRepository.save(patient);
		return "redirect:/patients";
	}
	
	@GetMapping("/editPatient")
	public String editPatient(Model model, int id) {
		Patient p =patientRepository.findById(id).get();
		model.addAttribute("patient",p);
		model.addAttribute("mode", "edit");
		List<User> docteurs = userRepository.getMedecins();
		model.addAttribute("docteurs",docteurs);
		return "EditPatient";
	}
	
	@PostMapping("/updatePatient")
	public String updateMedecin(Patient patient,int id,@AuthenticationPrincipal UserDetails currentUser) {
		patient.setId(id);
		patientRepository.updatePatient(id, patient.getNom(), patient.getPrenom(), patient.getAge(), 
				patient.getGenre(), patient.getCin(), patient.getTel(), patient.getAddress(), patient.getEmail()
				,patient.getMaladie(),patient.getDocteur_id());

		User user = userRepository.getUserByUsername(currentUser.getUsername());
		if(user.getRole_id() == 3) {
			return "redirect:/patients";
		}
		else {
			return "redirect:/med_patients";
		}
		
	}
	
	@GetMapping("/showPatient")
	public String showPatient(Model model, int id) {
		Patient patient =patientRepository.findById(id).get();
		model.addAttribute("patient",patient);
		User medecin = userRepository.findById(patient.getDocteur_id()).get();
		model.addAttribute("medecin",medecin);
		return "showPatient";
	}
}






