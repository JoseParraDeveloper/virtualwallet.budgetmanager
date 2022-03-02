package com.virtualwallet.budgetmanager.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.virtualwallet.budgetmanager.entities.Authority;
import com.virtualwallet.budgetmanager.entities.Person;
import com.virtualwallet.budgetmanager.exceptions.AuthorityNotFoundException;
import com.virtualwallet.budgetmanager.service.IAuthorityService;
import com.virtualwallet.budgetmanager.service.IPersonService;
import com.virtualwallet.budgetmanager.service.IUserService;

@Controller
@RequestMapping(value = "/auth")
public class LoginController {
	@Autowired
	private IPersonService personService;

	@Autowired
	private IAuthorityService authorityService;

	@Autowired
	private IUserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String registerForm(Model model) {

		model.addAttribute("person", new Person());
		return "register";
	}

	@PostMapping("/saveRegister")
	public String register(@Valid @ModelAttribute("person") Person personUser, BindingResult result, Model model,
			RedirectAttributes attribute) throws AuthorityNotFoundException {

		if (result.hasErrors()) {
			model.addAttribute("person", personUser);
			return "register";
		}

		if (userService.findByUsername(personUser.getUser().getUsername()) != null) {
			model.addAttribute("message", "Ya existe un usuario con username dado.");
			model.addAttribute("person", personUser);
			return "register";
		}

		if (personService.findByEmail(personUser.getEmail()) != null) {
			model.addAttribute("message", "Ya existe un usuario con la dirección de correo dada.");
			model.addAttribute("person", personUser);
			return "register";
		}
		try {
			personUser.getUser().setPassword(passwordEncoder.encode(personUser.getUser().getPassword()));
			Set<Authority> authorities = new HashSet<Authority>();
			List<Person> listPersonRegister = personService.getAllPerson();
			if (listPersonRegister.isEmpty()) {
				authorities.add(authorityService.getByIdAuthority(1L));
				authorities.add(authorityService.getByIdAuthority(2L));
				authorities.add(authorityService.getByIdAuthority(3L));
			} else {
				authorities.add(authorityService.getByIdAuthority(3L));
			}
			personUser.getUser().setAuthority(authorities);
			personUser.getUser().setPerson(personUser);
			personService.savePerson(personUser);
		} catch (AuthorityNotFoundException e) {
			attribute.addFlashAttribute("error", "Error al registrarte");
			return "redirect:/";
		}

		attribute.addFlashAttribute("success",
				"Felicitaciones " + personUser.getName().toUpperCase() + " ya eres parte de la comunidad!!");
		model.addAttribute("person", personUser);
		return "redirect:/";

	}
}
