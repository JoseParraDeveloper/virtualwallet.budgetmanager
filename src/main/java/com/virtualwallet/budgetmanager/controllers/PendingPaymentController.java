package com.virtualwallet.budgetmanager.controllers;

import java.util.List;
import java.util.Map;

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

import com.virtualwallet.budgetmanager.entities.Operation;
import com.virtualwallet.budgetmanager.entities.PendingPayment;
import com.virtualwallet.budgetmanager.entities.Person;
import com.virtualwallet.budgetmanager.entities.User;
import com.virtualwallet.budgetmanager.enumTypes.TypeCoin;
import com.virtualwallet.budgetmanager.service.IPendingPaymentService;
import com.virtualwallet.budgetmanager.utils.PersonAuthenticationUtil;

@Controller
@RequestMapping("/pendingPayments")
public class PendingPaymentController {

	private User personUser;
	private Person person;

	@Autowired
	private PersonAuthenticationUtil personAuthenticationUtil;

	@Autowired
	private IPendingPaymentService pendingPaymentService;

	@GetMapping(value = "/")
	public String listPendingPayment(@RequestParam Map<String, Object> params,
			@RequestParam(value = "typeCoin", required = false) String typeCoin, Model model) {

		personUser = personAuthenticationUtil.personAuthentication();
		person = personUser.getPerson();
		Long idPerson = person.getId();

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<PendingPayment> pagePendingPayment = null;

		if (typeCoin != null) {
			pagePendingPayment = pendingPaymentService.findAllPendingPaymentByCoin(idPerson, typeCoin, pageRequest);
		} else {
			pagePendingPayment = pendingPaymentService.listPendingPayments(idPerson, pageRequest);
		}

		model.addAttribute("pendingPayments", pagePendingPayment);
		model.addAttribute("person", person);
		model.addAttribute("titleTable", (pagePendingPayment.isEmpty() ? "NO HAY" : "") + " PAGOS PENDIENTES"
				+ (typeCoin != null
						? " EN LA MONEDA " + (TypeCoin.PESOS.toString().equals(typeCoin) ? "PESOS" : "DOLAR") + "."
						: "."));

		return "/pendingPayments/pendingPayments";

	}

	@GetMapping("/addPendingPayment")
	public String addOperation(Model model) {
		personUser = personAuthenticationUtil.personAuthentication();
		person = personUser.getPerson();
		PendingPayment pendingPayment = new PendingPayment();

		pendingPayment.setPerson(person);
		model.addAttribute("titleTable", "AGENDAR PAGO");
		model.addAttribute("person", person);
		model.addAttribute("pendingPayment", pendingPayment);
		model.addAttribute("listTypeCoin", TypeCoin.values());

		return "/pendingPayments/addPendingPayment";
	}

	@PostMapping("/savePendingPayment")
	public String savePendingPayment(@Valid @ModelAttribute("pendingPayment") PendingPayment pendingPayment,
			BindingResult result, Model model, RedirectAttributes attribute) {

		personUser = personAuthenticationUtil.personAuthentication();
		person = personUser.getPerson();

		if (result.hasErrors()) {
			model.addAttribute("titleTable", "AGENDAR PAGO");
			model.addAttribute("person", person);
			model.addAttribute("pendingPayment", pendingPayment);

			return "pendingPayments/addPendingPayment";
		}

		pendingPaymentService.createPendingPayment(pendingPayment);
		attribute.addFlashAttribute("success", "PAGO PENDIENTE AGENDADO CON ÉXITO!");
		return "redirect:/pendingPayments/";
	}
}
