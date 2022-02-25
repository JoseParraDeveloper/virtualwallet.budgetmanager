package com.virtualwallet.budgetmanager.controllers;

import java.io.IOException;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.virtualwallet.budgetmanager.entities.PersonEntity;
import com.virtualwallet.budgetmanager.exceptions.PersonNotFoundException;
import com.virtualwallet.budgetmanager.service.IPersonService;

@Controller
@RequestMapping("/people/")
public class PersonController {

	@Autowired
	private IPersonService personService;

	@GetMapping
	public String listPeople(@RequestParam Map<String, Object> params, Model model) {

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 10);

		Page<PersonEntity> pagePersonEntity = personService.getListPerson(pageRequest);

		int totalPage = pagePersonEntity.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		model.addAttribute("listPeople", pagePersonEntity.getContent());
		model.addAttribute("titleTable", (totalPage > 0 ? "Lista de Usuarios en la Aplicación"
				: "No se Encuentran Usuarios Registrados en la Aplicación"));
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);
		return "listPeople";
	}

	@GetMapping("/addPerson")
	public String addPerson(Model model) {

		PersonEntity personEntity = new PersonEntity();

		model.addAttribute("titleTable", "Registrar Usuario en la Aplicación");
		model.addAttribute("action", "CREATE");
		model.addAttribute("person", personEntity);

		return "addPerson";
	}

	@PostMapping("/savePerson")
	public String savePerson(@Valid @ModelAttribute("person") PersonEntity personEntity, BindingResult result,
			Model model, @RequestParam("file") MultipartFile image, RedirectAttributes attribute) throws Exception {

		if (result.hasErrors()) {
			model.addAttribute("titleTable", (personEntity.getId() != null ? "Actualizar Usuario en la Aplicación"
					: "Registrar Usuario en la Aplicación"));
			model.addAttribute("action", (personEntity.getId() != null ? "EDITAR" : "CREATE"));
			model.addAttribute("person", personEntity);
			return "addPerson";
		}

		if (!image.isEmpty()) {

			try {
				personEntity.setPhoto(Base64.getEncoder().encodeToString(image.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (personEntity.getId() != null) {
			attribute.addFlashAttribute("success", "Usuario Actualizado con éxito!");
		} else {
			attribute.addFlashAttribute("success", "Usuario Registrado con éxito!");
		}
		personService.savePerson(personEntity);
		return "redirect:/people/";
	}

	@GetMapping("/edit/{id}")
	public String editPerson(@PathVariable("id") Long idPerson, Model model, RedirectAttributes attribute)
			throws PersonNotFoundException {
		PersonEntity personEntity = null;
		try {
			personEntity = personService.getPersonById(idPerson);
		} catch (PersonNotFoundException e) {
			attribute.addFlashAttribute("error", e.getMessage());
			return "redirect:/people/";
		}
		model.addAttribute("titleTable", "Actualizar Usuario");
		model.addAttribute("person", personEntity);

		return "addPerson";
	}

	@GetMapping("/delete/{id}")
	public String deletePerson(@PathVariable("id") Long idPerson, Model model, RedirectAttributes attribute)
			throws PersonNotFoundException {
		PersonEntity personEntity = null;
		try {
			personEntity = personService.getPersonById(idPerson);
		} catch (PersonNotFoundException e) {
			attribute.addFlashAttribute("error", e.getMessage());
			return "redirect:/people/";
		}

		personService.deletePersonById(idPerson);
		attribute.addFlashAttribute("warning",
				personEntity.getName() + " " + personEntity.getSurname() + " fue eliminad@ con éxito!");
		return "redirect:/people/";
	}
}
