package org.sid.gc.controller;

import java.util.Arrays;
import java.util.HashSet;

import org.sid.gc.dao.RoleRepository;
import org.sid.gc.dao.UserRepository;
import org.sid.gc.entities.Role;
import org.sid.gc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GPController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping(path = "/gps")
	public String List(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
		Page<User> pageGPs = userRepository.getGP(mc, PageRequest.of(page, size));
		Page<User> pageGPK = userRepository.getGPK(mc, PageRequest.of(page, size));
		model.addAttribute("gpsk", pageGPK.getContent());
		model.addAttribute("gps", pageGPs.getContent());
		model.addAttribute("pages", new int[pageGPs.getTotalPages()]);
		model.addAttribute("pagesk", new int[pageGPK.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "gps";
	}
	
	@GetMapping("/formGP")
	public String formGP(Model model) {
		model.addAttribute("gp", new User());
		model.addAttribute("mode", "new");
		return "formGP";
	}
	
	@PostMapping("/saveGP")
	public String saveGP(Model model,User gp,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "formGP";
		User g = new User(); 
		g.setUsername(gp.getUsername());
		g.setPassword(passwordEncoder.encode(gp.getPassword()));
		g.setRoles(new HashSet<Role> (Arrays.asList(roleRepository.findByName("ROLE_GP"))));
		g.setNom(gp.getNom());
		g.setPrenom(gp.getPrenom());
		g.setGenre(gp.getGenre());
		g.setDate_naiss(gp.getDate_naiss());
		g.setAddress(gp.getAddress());
		g.setTel(gp.getTel());
		g.setEmail(gp.getEmail());
		g.setCin(gp.getCin());
		g.setRole_id(3);
		g.setEnabled(true);
		userRepository.save(g);
		return "redirect:/gps";
	}
	
	@GetMapping("/editGP")
	public String editGP(Model model, Integer id) {
		User gp =userRepository.findById(id).get();	
		model.addAttribute("gp",gp);
		model.addAttribute("mode", "edit");
		return "EditGP";
	}
	
	@PostMapping("/updateGP")
	public String updateGP(User gp,Integer id) {
		gp.setRoles(new HashSet<Role> (Arrays.asList(roleRepository.findByName("ROLE_GP"))));
		gp.setId(id);
//		userRepository.save(gp);
		userRepository.updateUser(id, gp.getUsername(),gp.getNom(),gp.getPrenom(),gp.getDate_naiss(),
				gp.getGenre(),gp.getTel(),gp.getAddress(),gp.getEmail(),gp.getCin());
		return "redirect:/gps";
	}
	
	@GetMapping(path ="/deleteGP")
	public String delete(Integer id,String keyword,int page,int size) {
		userRepository.deleteUser(id);
		userRepository.deleteRoleUser(id);
		return "redirect:/gps?page="+page+"&size="+size+"&keyword="+keyword;
	}
	
	@GetMapping("/showGP")
	public String showGP(Model model, Integer id) {
		User gp =userRepository.findById(id).get();
		model.addAttribute("gp",gp);
		return "showGP";
	}

}
