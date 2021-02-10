package org.sid.gc.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Null;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.sid.gc.dao.FactureRepository;
import org.sid.gc.dao.MedicamentRepository;
import org.sid.gc.dao.PatientRepository;
import org.sid.gc.dao.RendezVousRepository;
import org.sid.gc.dao.TraitementRepository;
import org.sid.gc.dao.UserRepository;
import org.sid.gc.entities.Facture;
import org.sid.gc.entities.Medicament;
import org.sid.gc.entities.Patient;
import org.sid.gc.entities.RendezVous;
import org.sid.gc.entities.Traitement;
import org.sid.gc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.lowagie.text.DocumentException;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class FactureController {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TraitementRepository traitementRepository;
	
	@Autowired
	private MedicamentRepository medicamentRepository;
	
	@Autowired
	private FactureRepository factureRepository;
	
	 @Autowired
    ServletContext servletContext;

    private TemplateEngine templateEngine;

	
	
	@GetMapping(path="/factures")
	public String List(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String mc) {
//		List<Patient> patients = patientRepository.findAll();
		Page<Facture> pageFacture = factureRepository.findByPatientContains(mc,PageRequest.of(page, size));

		model.addAttribute("factures", pageFacture.getContent());
		
		model.addAttribute("pages", new int[pageFacture.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);
		return "factures";
	}
	
	@GetMapping(path ="/deleteFacture")
	public String delete(int id,String keyword,int page,int size) {
		factureRepository.deleteById(id);
		return "redirect:/factures?page="+page+"&size="+size+"&keyword="+keyword;
	}

	
	@GetMapping("/formFacture")
	public String formFacture(Model model,int id) {
		model.addAttribute("facture", new Facture());
		Traitement traitement =traitementRepository.findById(id).get();
		model.addAttribute("traitement", traitement);
//		List<Medicament> medicaments = medicamentRepository.findAll();
//		model.addAttribute("medicaments",medicaments);
//		List<User> medecins = userRepository.getMedecins();
//		model.addAttribute("medecins",medecins);
//		List<Patient> patients = patientRepository.findAll();
//		model.addAttribute("patients",patients);
		return "formFacture";
	}
	
	@PostMapping("/saveFacture")
	public String saveFacture(@Valid Facture fct, BindingResult bindingResult,int id) {
		if(bindingResult.hasErrors()) return "formFacture";
		Facture f = new Facture();
		Traitement tr = traitementRepository.findById(id).get();
		Medicament medicament = medicamentRepository.findById(tr.getMedicament_id()).get();
//		User medecin = userRepository.getUserByUsername(currentUser.getUsername());
//		Patient patient = patientRepository.findById(tr.getPatient_id()).get();
		
		f.setPrix(fct.getPrix());
		
		f.setTraitement_id(tr.getId());
		f.setMedecin_id(tr.getMedecin_id());
		f.setPatient_id(tr.getPatient_id());
		f.setMedicament_id(tr.getMedicament_id());
		
		f.setMedecin(tr.getMedecin());
		f.setPatient(tr.getPatient());
		f.setMedicament(medicament.getName()+" "+medicament.getQuantite());
		

		factureRepository.save(f);
		return "redirect:/factures";
	}
	
	@RequestMapping(path = "/pdf")
    public ResponseEntity<?> getPDF(int id,HttpServletRequest request, HttpServletResponse response) throws IOException {

        /* Do Business Logic*/

		Facture facture =factureRepository.findById(id).get();
		Date date = facture.getDate_ajout();
		DateFormat fullDateFormat = DateFormat.getDateTimeInstance(
		        DateFormat.FULL,
		        DateFormat.FULL);
		
		Patient patient = patientRepository.findById(facture.getPatient_id()).get();
		
		User medecin = userRepository.findById(patient.getDocteur_id()).get();
		
		
		Traitement traitement = traitementRepository.findById(facture.getTraitement_id()).get();
		
		Medicament medicament = medicamentRepository.findById(facture.getMedicament_id()).get();
		


        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("facture", facture);
        context.setVariable("date", fullDateFormat.format(date));
        
        context.setVariable("patient", patient);
        context.setVariable("medecin", medecin);
        context.setVariable("traitement", traitement);
        context.setVariable("medicament", medicament);
        String orderHtml = templateEngine.process("showFacture", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");

        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);  

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
        		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Facture_Ref°"+id+".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(bytes);

    }
	
	
	
	@GetMapping("/showFacture")
	public String showFacture(Model model, int id) {
		Facture facture =factureRepository.findById(id).get();
		model.addAttribute("facture",facture);
		Patient patient = patientRepository.findById(facture.getPatient_id()).get();
		model.addAttribute("patient",patient);
		User medecin = userRepository.findById(patient.getDocteur_id()).get();
		model.addAttribute("medecin",medecin);
		Traitement traitement = traitementRepository.findById(facture.getTraitement_id()).get();
		model.addAttribute("traitement",traitement);
		Medicament medicament = medicamentRepository.findById(facture.getMedicament_id()).get();
		model.addAttribute("medicament",medicament);
		return "showFacture";
	}
}






