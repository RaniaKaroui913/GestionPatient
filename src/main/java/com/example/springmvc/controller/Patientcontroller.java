package com.example.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springmvc.entities.Patient;
import com.example.springmvc.repositories.Patientrep;

import lombok.Value;

@Controller
public class Patientcontroller {
	@Autowired
	private Patientrep patientrep;

	@GetMapping(path = "/index")
	public String index() {
		return "index";
	}

	@GetMapping(path = "/patients")
	public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size,
			@RequestParam(name = "keyword", defaultValue = "") String mc) {
		Page<Patient> patients = patientrep.findByNomContains(mc, PageRequest.of(page, size));
		model.addAttribute("patients", patients.getContent());
		model.addAttribute("pages", new int[patients.getTotalPages()]);
		model.addAttribute("currentpage", page);
		model.addAttribute("keyword", mc);
		return "patients";
	}

	@GetMapping(path = "/deletepatient")
	public String delete(Long id, @RequestParam(name = "keyword") String keyword,
			@RequestParam(name = "page") String page, @RequestParam(name = "size") String size) {
		patientrep.deleteById(id);
		return "redirect:/patients" + "?page=" + page + "&size=" + size + "&keyword=" + keyword;
	}
	@GetMapping(path = "/editpatient")
	public String editpatient(Model model,Long id) {
		Patient p=patientrep.findById(id).get();
		model.addAttribute("patient", p);
		model.addAttribute("mode", "edit");
		return "formpatient";
	}

	@GetMapping(path = "/formpatient")
	public String formpatient(Model model) {
		model.addAttribute("patient", new Patient());
		model.addAttribute("mode", "add");
		return "formpatient";
	}

	@PostMapping(path = "/savepatient")
	public String savepatient(Model model,@javax.validation.Valid Patient patient, BindingResult binding) {
		if (binding.hasErrors()) return "formpatient";
		patientrep.save(patient);
		model.addAttribute("patient",patient);
		return "confirm";	
	}

}
