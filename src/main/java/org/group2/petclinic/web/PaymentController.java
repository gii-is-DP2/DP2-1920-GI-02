
package org.group2.petclinic.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.PaymentService;
import org.group2.petclinic.service.SecretaryService;
import org.group2.petclinic.service.VisitSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/secretary/visits/{visitId}")
public class PaymentController {

	private static final String		VIEWS_PAYMENTS_CREATE_FORM	= "/secretary/visits/createPaymentForm";

	@Autowired
	private PaymentService			paymentService;

	@Autowired
	private SecretaryService		secretaryService;

	@Autowired
	private VisitSecretaryService	visitSecretaryService;


	@InitBinder("payment")
	public void initPaymentBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new PaymentValidator());
	}

	@ModelAttribute("visit")
	public Visit findVisit(@PathVariable("visitId") final int visitId) {
		return this.visitSecretaryService.findVisitById(visitId);
	}

	@InitBinder("visit")
	public void initVisitBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/payments/new")
	public String initCreationForm(final Visit visit, final ModelMap model) {
		Payment payment = new Payment();
		model.addAttribute("payment", payment);
		List<String> listMethod = new ArrayList<String>();
		listMethod.add("creditcard");
		listMethod.add("cash");
		model.addAttribute("method", listMethod);

		Double finalPrice = visit.getVisitType().getPrice();
		payment.setFinalPrice(finalPrice);

		return PaymentController.VIEWS_PAYMENTS_CREATE_FORM;
	}

	@PostMapping(value = "/payments/new")
	public String processCreationForm(final Visit visit, @Valid final Payment payment, final BindingResult result, final ModelMap model, final RedirectAttributes redirectAttributes) {

		List<String> listMethod = new ArrayList<String>();
		listMethod.add("creditcard");
		listMethod.add("cash");
		model.addAttribute("method", listMethod);

		if (result.hasErrors()) {
			model.addAttribute("payment", payment);
			return PaymentController.VIEWS_PAYMENTS_CREATE_FORM;
		} else {
			LocalDateTime actualMoment = LocalDateTime.now();
			actualMoment.minusSeconds(1);
			payment.setMoment(actualMoment);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			userDetail.getUsername();

			Secretary secretary = this.secretaryService.findSecretaryByName(userDetail.getUsername());

			payment.setSecretary(secretary);

			payment.setCreditcard(null);

			Visit visitIn = this.visitSecretaryService.findVisitById(visit.getId());

			visitIn.setPayment(payment);
			model.addAttribute("visit", visitIn);
			this.paymentService.savePayment(payment);
			redirectAttributes.addAttribute("paymentId", payment.getId());

			if (payment.getMethod().contains("creditcard")) {
				return "redirect:/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new";
			} else {
				return "redirect:/secretary/visits";
			}

		}
	}

}
