package com.virtualwallet.budgetmanager.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.virtualwallet.budgetmanager.DTOs.AdvancedSearchDTO;
import com.virtualwallet.budgetmanager.DTOs.OperationDTO;
import com.virtualwallet.budgetmanager.entities.Operation;
import com.virtualwallet.budgetmanager.entities.Person;
import com.virtualwallet.budgetmanager.entities.User;
import com.virtualwallet.budgetmanager.enumTypes.TypeCoin;
import com.virtualwallet.budgetmanager.enumTypes.TypeOperation;
import com.virtualwallet.budgetmanager.repository.IOperationRepository;
import com.virtualwallet.budgetmanager.service.IOperationService;
import com.virtualwallet.budgetmanager.utils.PersonAuthenticationUtil;

@Controller
@RequestMapping(value = "/operations")
public class OperationPersonController {

	private User personUser;
	private Person person;

	@Autowired
	private IOperationService operationService;

	@Autowired
	IOperationRepository operationRepository;

	@Autowired
	private PersonAuthenticationUtil personAuthenticationUtil;

	@GetMapping("/")
	public String operationsSummary(Model model, RedirectAttributes attribute) {
		personUser = personAuthenticationUtil.personAuthentication();
		person = personUser.getPerson();
		Long idPerson = person.getId();

		BigDecimal balancePesos = operationService.totalBalance(idPerson, TypeCoin.PESOS.toString());
		BigDecimal balanceDollar = operationService.totalBalance(idPerson, TypeCoin.DOLLAR.toString());

		model.addAttribute("balancePesos", balancePesos);
		model.addAttribute("balanceDollar", balanceDollar);
		model.addAttribute("person", person);
		return "Operations/operationsSummary";
	}

	@GetMapping("/movements")
	public String getMovements(@RequestParam Map<String, Object> params,
			@RequestParam(value = "typeCoin", required = false) String typeCoin,
			@RequestParam(value = "typeOperation", required = false) String typeOperation,
			@ModelAttribute("advancedSearchDTO") AdvancedSearchDTO advancedSearch, Model model) {
		personUser = personAuthenticationUtil.personAuthentication();
		person = personUser.getPerson();
		Long idPerson = person.getId();

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<Operation> pageOperation = null;
		if (typeOperation != null) {
			pageOperation = operationService.findAllOperationByTypeOperationAndCoin(idPerson, typeCoin, typeOperation,
					pageRequest);
		} else {
			pageOperation = operationService.findAllOperationByCoin(idPerson, typeCoin, pageRequest);
		}

		int totalPage = pageOperation.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<Operation> listOperationsPerson = pageOperation.getContent();
		List<OperationDTO> listOperationsPersonDTO = new ArrayList<OperationDTO>();

		for (Operation operation : listOperationsPerson) {
			OperationDTO operationDTO = new OperationDTO();
			operationDTO.setConcept(operation.getConcept());
			operationDTO.setAmount(operation.getAmount());
			operationDTO.setDate(operation.getDate());
			listOperationsPersonDTO.add(operationDTO);
		}
		BigDecimal balanceCoin = operationService.totalBalance(idPerson, typeCoin);
		AdvancedSearchDTO advancedSearchDTO = new AdvancedSearchDTO();
		advancedSearchDTO.setDateFrom(new Date());
		advancedSearchDTO.setDateTo(new Date());
		advancedSearchDTO.setCoin(TypeCoin.valueOf(typeCoin));
		advancedSearchDTO.setTypeOperation("TODAS");

		model.addAttribute("operations", listOperationsPersonDTO);
		model.addAttribute("person", person);
		model.addAttribute("balanceCoin", balanceCoin);
		model.addAttribute("coin", TypeCoin.valueOf(typeCoin));
		model.addAttribute("titleTable",
				"MOVIMIENTOS EN " + (TypeCoin.PESOS.toString().equals(typeCoin) ? "PESOS" : "DOLAR"));
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("advancedSearchDTO", advancedSearchDTO);
		model.addAttribute("listTypeCoin", TypeCoin.values());
		if (listOperationsPersonDTO.isEmpty()) {
			model.addAttribute("textInfo",
					"NO TIENE MOVIMIENTOS EN " + TypeCoin.valueOf(typeCoin).getCoin()
							+ (typeOperation != null ? " PARA EL TIPO DE OPERACIÓN "
									+ (TypeOperation.INGRESS.name().equals(typeOperation) ? "INGRESOS." : "EGRESOS.")
									: "."));
		}

		return "Operations/homeOperationPerson";
	}

	@GetMapping("/addOperation")
	public String addOperation(Model model) {

		Operation operation = new Operation();

		operation.setPerson(person);
		model.addAttribute("titleTable", "Nueva Operación");
		model.addAttribute("person", person);
		model.addAttribute("operation", operation);
		model.addAttribute("listTypeOperation", TypeOperation.values());
		model.addAttribute("listTypeCoin", TypeCoin.values());

		return "Operations/addOperation";
	}

	@PostMapping("/saveOperation")
	public String saveOperation(@Valid @ModelAttribute("operation") Operation operation, BindingResult result,
			Model model, RedirectAttributes attribute) {

		if (result.hasErrors()) {
			model.addAttribute("titleTable", "NUEVA OPERACIÓN");
			model.addAttribute("person", person);
			model.addAttribute("operation", operation);
			model.addAttribute("listTypeOperation", TypeOperation.values());

			return "Operations/addOperation";
		}

		try {
			if (TypeOperation.EXPENSES.name().equals(operation.getTypeOperation().toString())) {
				operation.setAmount(operation.getAmount().multiply(new BigDecimal(-1)));
			}

			operationService.saveOperation(operation);
		} catch (Exception e) {
			attribute.addFlashAttribute("error", "NO TIENE SUFICIENTE DINERO DISPONIBLE PARA REALIZAR ESTA OPERACIÓN!");
			return "redirect:/operations/";
		}
		attribute.addFlashAttribute("success", "OPERACIÓN REGISTRADA CON ÉXITO!");
		return "redirect:/operations/";
	}

	@GetMapping("/advancedSearch")
	public String advancedSearch(@RequestParam Map<String, Object> params,
			@ModelAttribute("advancedSearchDTO") AdvancedSearchDTO advancedSearch, Model model) {
		personUser = personAuthenticationUtil.personAuthentication();
		person = personUser.getPerson();
		Long idPerson = person.getId();

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 10);
		String typeOperation = advancedSearch.getTypeOperation();
		Page<Operation> pageOperation = null;
		Collection<String> typeOperations = new ArrayList<String>();
		TypeOperation[] a = TypeOperation.values();

		if (typeOperation.equals("TODAS")) {

			for (TypeOperation typeOperation2 : a) {
				typeOperations.add(typeOperation2.name());
			}

		}
		pageOperation = operationRepository.findAllByAdvancedSearch(idPerson, typeOperations,
				advancedSearch.getCoin().toString(), advancedSearch.getDateFrom(), advancedSearch.getDateTo(),
				advancedSearch.getAmountFrom(), advancedSearch.getAmountTo(), pageRequest);

		int totalPage = pageOperation.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<Operation> listOperationsPerson = pageOperation.getContent();
		List<OperationDTO> listOperationsPersonDTO = new ArrayList<OperationDTO>();

		for (Operation operation : listOperationsPerson) {
			OperationDTO operationDTO = new OperationDTO();
			operationDTO.setConcept(operation.getConcept());
			operationDTO.setAmount((operation.getAmount()));
			operationDTO.setDate(operation.getDate());
			listOperationsPersonDTO.add(operationDTO);
		}

		model.addAttribute("titleTable", "BÚSQUEDA AVANZADA.");
		model.addAttribute("person", person);
		model.addAttribute("coin", advancedSearch.getCoin());
		model.addAttribute("operations", listOperationsPersonDTO);
		model.addAttribute("listTypeCoin", TypeCoin.values());
		if (listOperationsPersonDTO.isEmpty()) {
			model.addAttribute("textInfo", "NO EXISTEN MOVIMIENTOS CON LOS PARÁMETROS INDICADOS.");
		}
		return "Operations/homeOperationPerson";

	}

}
