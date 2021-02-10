package org.sid.gc.controller;

import java.util.Arrays;
import java.util.HashSet;

import org.sid.gc.dao.PatientRepository;
import org.sid.gc.dao.RendezVousRepository;
import org.sid.gc.dao.RoleRepository;
import org.sid.gc.dao.TraitementRepository;
import org.sid.gc.dao.UserRepository;
import org.sid.gc.entities.Patient;
import org.sid.gc.entities.RendezVous;
import org.sid.gc.entities.Role;
import org.sid.gc.entities.Traitement;
import org.sid.gc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MedecinController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private RendezVousRepository rendezVousRepository;
	
	@Autowired
	private TraitementRepository traitementRepository;
	
	@GetMapping(path = "/medecins")
	public String List(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
		
		Page<User> pageMedecins = userRepository.getMedecin(mc, PageRequest.of(page, size));
		model.addAttribute("medecins", pageMedecins.getContent());
		Page<User> pageMedecinsK = userRepository.getMedecinK(mc, PageRequest.of(page, size));
		model.addAttribute("medecinsk", pageMedecinsK.getContent());
		model.addAttribute("pages", new int[pageMedecins.getTotalPages()]);
		model.addAttribute("pagesk", new int[pageMedecinsK.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "medecins";
	}
	
	@GetMapping("/formMedecin")
	public String formMedecin(Model model) {
		model.addAttribute("medecin", new User());
		model.addAttribute("mode", "new");
		return "formMedecin";
	}
	
	@PostMapping("/saveMedecin")
	public String saveMedecin(Model model,User medecin,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "formMedecin";
		User med = new User(); 
		med.setRoles(new HashSet<Role> (Arrays.asList(roleRepository.findByName("ROLE_MEDECIN"))));
		med.setUsername(medecin.getUsername());
		med.setNom(medecin.getNom());
		med.setPrenom(medecin.getPrenom());
		med.setGenre(medecin.getGenre());
		med.setDate_naiss(medecin.getDate_naiss());
		med.setAddress(medecin.getAddress());
		med.setTel(medecin.getTel());
		med.setEmail(medecin.getEmail());
		med.setCin(medecin.getCin());
		med.setEnabled(true);
		med.setRole_id(2);
		med.setPassword(passwordEncoder.encode(medecin.getPassword()));
		userRepository.save(med);
		return "redirect:/medecins";
	}
	
	@GetMapping("/editMedecin")
	public String editMedecin(Model model, Integer id) {
		User medecin =userRepository.findById(id).get();
		model.addAttribute("medecin",medecin);
		model.addAttribute("mode", "edit");
		return "EditMedecin";
	}
	
	@PostMapping("/updateMedecin")
	public String updateMedecin(User medecin,Integer id) {
		medecin.setRoles(new HashSet<Role> (Arrays.asList(roleRepository.findByName("ROLE_MEDECIN"))));
		medecin.setId(id);
//		userRepository.save(medecin);
		userRepository.updateUser(id, medecin.getUsername(),medecin.getNom(),medecin.getPrenom(),medecin.getDate_naiss(),
				medecin.getGenre(),medecin.getTel(),medecin.getAddress(),medecin.getEmail(),medecin.getCin());
		return "redirect:/medecins";
	}
	
	@GetMapping(path ="/deleteMedecin")
	public String delete(Integer id,String keyword,int page,int size) {
		userRepository.deleteUser(id);
		userRepository.deleteRoleUser(id);
		return "redirect:/medecins?page="+page+"&size="+size+"&keyword="+keyword;
	}
	
	@GetMapping("/showMedecin")
	public String showMedecin(Model model, Integer id) {
		User medecin =userRepository.findById(id).get();
		model.addAttribute("medecin",medecin);
		return "showMedecin";
	}
	
	@GetMapping(path = "/med_patients")
	public String med_patients(Model model,@AuthenticationPrincipal UserDetails currentUser,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
//		User userAuth = (User)auth.getPrincipal();
		User user = userRepository.getUserByUsername(currentUser.getUsername());
		System.out.println(user.getId());
		Page<Patient> pageMedPatients = patientRepository.getMedPatient(user.getId(), PageRequest.of(page, size));
		model.addAttribute("med_patients", pageMedPatients.getContent());
		model.addAttribute("pages", new int[pageMedPatients.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "med_patients";
	}
	
	@GetMapping(path = "/med_rendez")
	public String med_rendez(Model model,@AuthenticationPrincipal UserDetails currentUser,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
//		User userAuth = (User)auth.getPrincipal();
		User user = userRepository.getUserByUsername(currentUser.getUsername());
		System.out.println(user.getId());
		Page<RendezVous> pageMedRendez = rendezVousRepository.getMedRendez(user.getId(), PageRequest.of(page, size));
		model.addAttribute("med_rendez", pageMedRendez.getContent());
		model.addAttribute("pages", new int[pageMedRendez.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "med_rendez";
	}
	
	@GetMapping(path = "/med_traitements")
	public String med_traitements(Model model,@AuthenticationPrincipal UserDetails currentUser,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
//		User userAuth = (User)auth.getPrincipal();
		User user = userRepository.getUserByUsername(currentUser.getUsername());
		System.out.println(user.getId());
		Page<Traitement> pageMedTraitement = traitementRepository.getMedTraitement(user.getId(), PageRequest.of(page, size));
		model.addAttribute("med_traitements", pageMedTraitement.getContent());
		model.addAttribute("pages", new int[pageMedTraitement.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "med_traitements";
	}
	

}
