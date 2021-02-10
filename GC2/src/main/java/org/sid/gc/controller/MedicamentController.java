package org.sid.gc.controller;

import java.util.List;

import javax.validation.Valid;

import org.sid.gc.dao.MedicamentRepository;
import org.sid.gc.dao.PatientRepository;
import org.sid.gc.dao.RendezVousRepository;
import org.sid.gc.dao.ScannerRepository;
import org.sid.gc.dao.UserRepository;
import org.sid.gc.entities.Medicament;
import org.sid.gc.entities.Patient;
import org.sid.gc.entities.RendezVous;
import org.sid.gc.entities.Scanner;
import org.sid.gc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MedicamentController {
	
	
	
	@Autowired
	private MedicamentRepository medicamentRepository;
	
	
	
	@GetMapping(path="/medicaments")
	public String List(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
//		List<Patient> patients = patientRepository.findAll();
		Page<Medicament> pageMedicaments = medicamentRepository.findByNameContains(mc,PageRequest.of(page, size));

		model.addAttribute("medicaments", pageMedicaments.getContent());
		
		model.addAttribute("pages", new int[pageMedicaments.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "medicaments";
	}
	
	@GetMapping(path ="/deleteMedicament")
	public String delete(int id,String keyword,int page,int size) {
		medicamentRepository.deleteById(id);
		return "redirect:/medicaments?page="+page+"&size="+size+"&keyword="+keyword;
	}

	
	@GetMapping("/formMedicament")
	public String formMedicament(Model model) {
		model.addAttribute("medicament", new Medicament());
		return "formMedicament";
	}
	
	@PostMapping("/saveMedicament")
	public String saveMedicament(@Valid Medicament med, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "formMedicament";
		Medicament m = new Medicament();
		m.setName(med.getName());
		m.setCaract(med.getCaract());
		m.setQuantite(med.getQuantite());
		medicamentRepository.save(m);
		return "redirect:/medicaments";
	}
	
	@GetMapping("/editMedicament")
	public String editMedicament(Model model, int id) {
		Medicament medict =medicamentRepository.findById(id).get();
		model.addAttribute("medicament",medict);
		return "EditMedicament";
	}
	
	@PostMapping("/updateMedicament")
	public String updateMedicament(Medicament medicament,int id) {
		medicament.setId(id);
		
		medicamentRepository.updateMedicament(id,medicament.getName(),medicament.getCaract(),
				medicament.getQuantite());

		return "redirect:/medicaments";
	}
	
//	@GetMapping("/showPatient")
//	public String showPatient(Model model, Long id) {
//		Patient patient =patientRepository.findById(id).get();
//		model.addAttribute("patient",patient);
//		User medecin = userRepository.findById(patient.getDocteur_id()).get();
//		model.addAttribute("medecin",medecin);
//		return "showPatient";
//	}
}
