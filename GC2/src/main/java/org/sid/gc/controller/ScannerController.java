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
public class ScannerController {
	
	@Autowired
	private ScannerRepository scannerRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	
	@GetMapping(path="/scanners")
	public String List(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
//		List<Patient> patients = patientRepository.findAll();
		Page<Scanner> pageScanners = scannerRepository.findByNomContains(mc,PageRequest.of(page, size));
//		for (Scanner scanner : pageScanners) {
//			List<Patient> patient = patientRepository.getPatients(scanner.getPatient_id());
//			for (Patient p : patient) {
//				model.addAttribute("patient", p);	
//			}
//			
//		}
		model.addAttribute("scanners", pageScanners.getContent());
		
		model.addAttribute("pages", new int[pageScanners.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "scanners";
	}
	
	@GetMapping(path ="/deleteScanner")
	public String delete(int id,String keyword,int page,int size) {
		scannerRepository.deleteById(id);
		return "redirect:/scanners?page="+page+"&size="+size+"&keyword="+keyword;
	}

	
	@GetMapping("/formScanner")
	public String formScanner(Model model) {
		model.addAttribute("scanner", new Scanner());
		List<Patient> patients = patientRepository.findAll();
		model.addAttribute("patients",patients);
		return "formScanner";
	}
	
	@PostMapping("/saveScanner")
	public String saveScanner(@Valid Scanner sc, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "formScanner";
		Scanner s = new Scanner();
		Patient patient = patientRepository.findById(sc.getPatient_id()).get();
		s.setPatient(patient.getNom()+" "+patient.getPrenom());
		s.setNom(sc.getNom());
		s.setDes(sc.getDes());
		s.setDate(sc.getDate());
		s.setPatient_id(sc.getPatient_id());
		scannerRepository.save(s);
		return "redirect:/scanners";
	}
	
	@GetMapping("/editScanner")
	public String editScanner(Model model, int id) {
		Scanner sc =scannerRepository.findById(id).get();
		model.addAttribute("scanner",sc);
		List<Patient> patients = patientRepository.findAll();
		model.addAttribute("patients",patients);
		return "EditScanner";
	}
	
	@PostMapping("/updateScanner")
	public String updateScanner(Scanner sc,int id) {
		sc.setId(id);
		Patient patient = patientRepository.findById(sc.getPatient_id()).get();
		String pa=patient.getNom()+" "+patient.getPrenom();
		scannerRepository.updateScanner(id,sc.getNom(),sc.getDes(),sc.getDate(),
				sc.getPatient_id(),pa);

		return "redirect:/scanners";
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
