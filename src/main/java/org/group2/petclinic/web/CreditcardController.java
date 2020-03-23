
package org.group2.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.CreditcardService;
import org.group2.petclinic.service.PaymentService;
import org.group2.petclinic.service.VisitSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secretary/visits/{visitId}/payments/{paymentId}")
public class CreditcardController {

	private static final String		VIEWS_CREDITCARD_CREATE_FORM	= "secretary/visits/createCreditcardForm";

	@Autowired
	private PaymentService			paymentService;

	@Autowired
	private CreditcardService		creditcardService;

	@Autowired
	private VisitSecretaryService	visitSecretaryService;


	@ModelAttribute("visit")
	public Visit findVisit(@PathVariable("visitId") final int visitId) {
		return this.visitSecretaryService.findVisitById(visitId);
	}

	@InitBinder("visit")
	public void initVisitBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("payment")
	public Payment findPayment(@PathVariable("paymentId") final int paymentId) {
		return this.paymentService.findPaymentById(paymentId);
	}

	@InitBinder("payment")
	public void initPaymentBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/creditcards/new")
	public String initCreationForm(final Secretary secretary, final ModelMap model) {
		Creditcard creditcard = new Creditcard();
		model.addAttribute("creditcard", creditcard);
		List<Integer> listExpMonth = new ArrayList<Integer>();
		listExpMonth.add(1);
		listExpMonth.add(2);
		listExpMonth.add(3);
		listExpMonth.add(4);
		listExpMonth.add(5);
		listExpMonth.add(6);
		listExpMonth.add(7);
		listExpMonth.add(8);
		listExpMonth.add(9);
		listExpMonth.add(10);
		listExpMonth.add(11);
		listExpMonth.add(12);
		model.addAttribute("expMonth", listExpMonth);
		return CreditcardController.VIEWS_CREDITCARD_CREATE_FORM;
	}

	@PostMapping(value = "/creditcards/new")
	public String processCreationForm(final Payment payment, @Valid final Creditcard creditcard, final BindingResult result, final ModelMap model) {

		List<Integer> listExpMonth = new ArrayList<Integer>();
		listExpMonth.add(1);
		listExpMonth.add(2);
		listExpMonth.add(3);
		listExpMonth.add(4);
		listExpMonth.add(5);
		listExpMonth.add(6);
		listExpMonth.add(7);
		listExpMonth.add(8);
		listExpMonth.add(9);
		listExpMonth.add(10);
		listExpMonth.add(11);
		listExpMonth.add(12);
		model.addAttribute("expMonth", listExpMonth);

		if (result.hasErrors()) {
			model.addAttribute("creditcard", creditcard);
			return CreditcardController.VIEWS_CREDITCARD_CREATE_FORM;
		} else {

			Payment paymentIn = this.paymentService.findPaymentById(payment.getId());
			paymentIn.setCreditcard(creditcard);

			this.creditcardService.saveCreditcard(creditcard);

			return "redirect:/secretary/visits";
		}
	}

}