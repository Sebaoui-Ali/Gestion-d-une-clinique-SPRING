package org.sid.gc.controller;

import org.sid.gc.dao.UserRepository;
import org.sid.gc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/monCompte")
	public String monCompte(Model model,@AuthenticationPrincipal UserDetails currentUser) {
		User user = userRepository.getUserByUsername(currentUser.getUsername());
		model.addAttribute("user", user);
		return "monCompte";
	}
}
