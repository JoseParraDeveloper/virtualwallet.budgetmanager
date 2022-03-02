package com.virtualwallet.budgetmanager.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.virtualwallet.budgetmanager.entities.Authority;
import com.virtualwallet.budgetmanager.exceptions.AuthorityNotFoundException;
import com.virtualwallet.budgetmanager.service.IAuthorityService;

@Controller
@RequestMapping(value = "/manageAuthority/")
public class ManageAuthorityController {
	@Autowired
	private IAuthorityService authorityService;

	@GetMapping
	public String manageAuthority(Model model) {

		List<Authority> listAuthorities = authorityService.getAllAuthorities();
		model.addAttribute("listAuthorities", listAuthorities);
		model.addAttribute("titleTable",
				(listAuthorities.isEmpty() ? "La Aplicación no tiene ROLES." : "ROLES en la Aplicación"));
		return "listAuthorities";

	}

	@GetMapping(value = "/edit/{id}")
	public String editDescriptionAuthority(@PathVariable("id") Long idAuthority, Model model,
			RedirectAttributes attribute) throws AuthorityNotFoundException {
		Authority authorityEntity = null;
		try {
			authorityEntity = authorityService.getByIdAuthority(idAuthority);
		} catch (AuthorityNotFoundException e) {
			attribute.addFlashAttribute("error", e.getMessage());
			return "redirect:/manageAuthority/";
		}
		model.addAttribute("titleTable", "Editar Descripción del ROL: " + authorityEntity.getAuthority());
		model.addAttribute("authorityEntity", authorityEntity);
		return "editDescriptionAuthority";
	}

	@PostMapping("/saveAuthority")
	public String savePerson(@Valid @ModelAttribute("authorityEntity") Authority authorityEntity, Model model,
			RedirectAttributes attribute) {

		authorityService.saveAuthority(authorityEntity);

		return "redirect:/manageAuthority/";
	}
}
