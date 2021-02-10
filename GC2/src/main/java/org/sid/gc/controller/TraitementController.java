package org.sid.gc.controller;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import java.util.List;

import org.sid.gc.dao.MedicamentRepository;
import org.sid.gc.dao.PatientRepository;
import org.sid.gc.dao.RendezVousRepository;
import org.sid.gc.dao.RoleRepository;
import org.sid.gc.dao.TraitementRepository;
import org.sid.gc.dao.UserRepository;
import org.sid.gc.entities.Medicament;
import org.sid.gc.entities.Patient;
import org.sid.gc.entities.RendezVous;
import org.sid.gc.entities.Role;
import org.sid.gc.entities.Traitement;
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
public class TraitementController {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TraitementRepository traitementRepository;
	
	@Autowired
	private MedicamentRepository medicamentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@GetMapping(path="/traitements")
	public String List(Model model,@AuthenticationPrincipal UserDetails currentUser,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
//		List<Patient> patients = patientRepository.findAll();
		User medecin = userRepository.getUserByUsername(currentUser.getUsername());
		Page<Traitement> pageTraitement = traitementRepository.findByPatientContains(mc,PageRequest.of(page, size));

		model.addAttribute("traitements", pageTraitement.getContent());
//		for (RendezVous rendezVous : pageRendez) {
//			
//			User medecin = userRepository.findById(rendezVous.getMedecin_id()).get();
//			System.out.println(medecin.getId());
//			int i = medecin.getId();
//			model.addAttribute("medecin",medecin);
////			model.addAttribute(medecin.getId());
//			
//			//Patient patient = patientRepository.findById(rendezVous.getPatient_id()).get();
//			//model.addAttribute("patient",patient);
////			model.addAttribute("rendez_vous", pageRendez.getContent());
//			//System.out.println(patient.getNom());
//			
//		}
		
		model.addAttribute("pages", new int[pageTraitement.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "traitements";
	}
	
	@GetMapping(path ="/deleteTraitement")
	public String delete(int id,String keyword,int page,int size,@AuthenticationPrincipal UserDetails currentUser) {
		User medecin = userRepository.getUserByUsername(currentUser.getUsername());
		traitementRepository.deleteById(id);
		if(medecin.getRole_id() == 3) {
			return "redirect:/traitements?page="+page+"&size="+size+"&keyword="+keyword;
		}
		else {
			return "redirect:/med_traitements?page="+page+"&size="+size+"&keyword="+keyword;
		}
		
	}

	
	@GetMapping("/formTraitement")
	public String formTraitement(Model model) {
		model.addAttribute("traitement", new Traitement());
		List<Medicament> medicaments = medicamentRepository.findAll();
		model.addAttribute("medicaments",medicaments);
		List<User> medecins = userRepository.getMedecins();
		model.addAttribute("medecins",medecins);
		List<Patient> patients = patientRepository.findAll();
		model.addAttribute("patients",patients);
		return "formTraitement";
	}
	
	@PostMapping("/saveTraitement")
	public String saveTraitement(@Valid Traitement tr, BindingResult bindingResult,
			@AuthenticationPrincipal UserDetails currentUser) {
		if(bindingResult.hasErrors()) return "saveTraitement";
		Traitement t = new Traitement();
		Medicament medicament = medicamentRepository.findById(tr.getMedicament_id()).get();
		//User medecin = userRepository.findById(tr.getMedecin_id()).get();
		User medecin = userRepository.getUserByUsername(currentUser.getUsername());
		Patient patient = patientRepository.findById(tr.getPatient_id()).get();
		t.setMedicament(medicament.getName());
		t.setMedecin(medecin.getNom()+" "+medecin.getPrenom());
		t.setPatient(patient.getNom()+" "+patient.getPrenom());
		t.setMedecin_id(medecin.getId());
		t.setPatient_id(tr.getPatient_id());
		t.setMedicament_id(tr.getMedicament_id());
		t.setNombre(tr.getNombre());
		t.setPeriode(tr.getPeriode());
		traitementRepository.save(t);
		if(medecin.getRole_id() == 3) {
			return "redirect:/traitements";
		}
		else {
			return "redirect:/med_traitements";
		}
	}
	
	@GetMapping("/editTraitement")
	public String editPatient(Model model, int id) {
		Traitement traitement =traitementRepository.findById(id).get();
		model.addAttribute("traitement",traitement);
		List<Medicament> medicaments = medicamentRepository.findAll();
		model.addAttribute("medicaments",medicaments);
		List<User> medecins = userRepository.getMedecins();
		model.addAttribute("medecins",medecins);
		List<Patient> patients = patientRepository.findAll();
		model.addAttribute("patients",patients);
		return "EditTraitement";
	}
	
	@PostMapping("/updateTraitement")
	public String updateTraitement(Traitement traitement,int id,
			@AuthenticationPrincipal UserDetails currentUser) {
		traitement.setId(id);
		Medicament medicament = medicamentRepository.findById(traitement.getMedicament_id()).get();
		//User medecin = userRepository.findById(traitement.getMedecin_id()).get();
		User medecin = userRepository.getUserByUsername(currentUser.getUsername());
		Patient patient = patientRepository.findById(traitement.getPatient_id()).get();
		String me=medicament.getName();
		String med=medecin.getNom()+" "+medecin.getPrenom();
		String pa=patient.getNom()+" "+patient.getPrenom();
		traitementRepository.updateTraitement(id,traitement.getPeriode(),traitement.getNombre(),
				medicament.getId(), traitement.getPatient_id(), 
				me,pa,med);

		if(medecin.getRole_id() == 3) {
			return "redirect:/traitements";
		}
		else {
			return "redirect:/med_traitements";
		}
		
	}
	
//	@GetMapping(path="/med_traitements")
//	public String med_traitements(Model model,@AuthenticationPrincipal UserDetails currentUser,
//			@RequestParam(name="page",defaultValue = "0") int page,
//			@RequestParam(name="size",defaultValue = "5") int size,
//			@RequestParam(name="keyword",defaultValue = "") String mc) {
////		List<Patient> patients = patientRepository.findAll();
//		User medecin = userRepository.getUserByUsername(currentUser.getUsername());
//		Page<Traitement> pageTraitement = traitementRepository.getMedTraitement(medecin.getId(),PageRequest.of(page, size));
//
//		model.addAttribute("traitements", pageTraitement.getContent());
//
//		model.addAttribute("pages", new int[pageTraitement.getTotalPages()]);
//		model.addAttribute("currentPage", page);
//		model.addAttribute("keyword",mc);
//		model.addAttribute("size",size);
//		return "traitements";
//	}
	
	
	
	
//	@GetMapping("/showPatient")
//	public String showPatient(Model model, Long id) {
//		Patient patient =patientRepository.findById(id).get();
//		model.addAttribute("patient",patient);
//		User medecin = userRepository.findById(patient.getDocteur_id()).get();
//		model.addAttribute("medecin",medecin);
//		return "showPatient";
//	}
}






