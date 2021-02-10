package org.sid.gc.controller;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import java.util.List;
import org.sid.gc.dao.PatientRepository;
import org.sid.gc.dao.RendezVousRepository;
import org.sid.gc.dao.UserRepository;
import org.sid.gc.entities.Patient;
import org.sid.gc.entities.RendezVous;
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
public class RendezVousController {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RendezVousRepository rendezVousRepository;
	
	
	@GetMapping(path="/rendez_vous")
	public String List(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
//		List<Patient> patients = patientRepository.findAll();
		Page<RendezVous> pageRendez = rendezVousRepository.findAll(PageRequest.of(page, size));

		model.addAttribute("rendez_vous", pageRendez.getContent());
		for (RendezVous rendezVous : pageRendez) {
			
			User medecin = userRepository.findById(rendezVous.getMedecin_id()).get();
			System.out.println(medecin.getId());
			int i = medecin.getId();
			model.addAttribute("medecin",medecin);
//			model.addAttribute(medecin.getId());
			
			//Patient patient = patientRepository.findById(rendezVous.getPatient_id()).get();
			//model.addAttribute("patient",patient);
//			model.addAttribute("rendez_vous", pageRendez.getContent());
			//System.out.println(patient.getNom());
			
		}
		
		model.addAttribute("pages", new int[pageRendez.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "rendez_vous";
	}
	
	@GetMapping(path ="/deleteRendez")
	public String delete(int id,String keyword,int page,int size,@AuthenticationPrincipal UserDetails currentUser) {
		rendezVousRepository.deleteById(id);
		User user = userRepository.getUserByUsername(currentUser.getUsername());
		if(user.getRole_id() == 3) {
			return "redirect:/rendez_vous?page="+page+"&size="+size+"&keyword="+keyword;
		}
		else {
			return "redirect:/med_rendez?page="+page+"&size="+size+"&keyword="+keyword;
		}
		
	}

	
	@GetMapping("/formRendez")
	public String formPatient(Model model) {
		model.addAttribute("rdv", new RendezVous());
		List<User> medecins = userRepository.getMedecins();
		model.addAttribute("medecins",medecins);
		List<Patient> patients = patientRepository.findAll();
		model.addAttribute("patients",patients);
		return "formRendez";
	}
	
	@PostMapping("/saveRendez")
	public String saveRendez(@Valid RendezVous rd, BindingResult bindingResult,@AuthenticationPrincipal UserDetails currentUser) {
		if(bindingResult.hasErrors()) return "formRendez";
		RendezVous v = new RendezVous();
		User medecin = userRepository.findById(rd.getMedecin_id()).get();
		Patient patient = patientRepository.findById(rd.getPatient_id()).get();
		v.setMedecin(medecin.getNom()+" "+medecin.getPrenom());
		v.setPatient(patient.getNom()+" "+patient.getPrenom());
		v.setMedecin_id(rd.getMedecin_id());
		v.setPatient_id(rd.getPatient_id());
		v.setDate(rd.getDate());
		v.setTime(rd.getTime());
		rendezVousRepository.save(v);
		User user = userRepository.getUserByUsername(currentUser.getUsername());
		if(user.getRole_id() == 3) {
			return "redirect:/rendez_vous";
		}
		else {
			return "redirect:/med_rendez";
		}
		
	}
	
	@GetMapping("/editRendez")
	public String editPatient(Model model, int id) {
		RendezVous rendez =rendezVousRepository.findById(id).get();
		model.addAttribute("rdv",rendez);
		List<User> medecins = userRepository.getMedecins();
		model.addAttribute("medecins",medecins);
		List<Patient> patients = patientRepository.findAll();
		model.addAttribute("patients",patients);
		return "EditRendez";
	}
	
	@PostMapping("/updateRendez")
	public String updateRendez(RendezVous rendez,int id,@AuthenticationPrincipal UserDetails currentUser) {
		rendez.setId(id);
		
		User medecin = userRepository.findById(rendez.getMedecin_id()).get();
		Patient patient = patientRepository.findById(rendez.getPatient_id()).get();
		String med=medecin.getNom()+" "+medecin.getPrenom();
		String pa=patient.getNom()+" "+patient.getPrenom();
		rendezVousRepository.updateRendez(id, rendez.getMedecin_id(), rendez.getPatient_id(), 
				rendez.getDate(),rendez.getTime(),med,pa);

		User user = userRepository.getUserByUsername(currentUser.getUsername());
		if(user.getRole_id() == 3) {
			return "redirect:/rendez_vous";
		}
		else {
			return "redirect:/med_rendez";
		}
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






