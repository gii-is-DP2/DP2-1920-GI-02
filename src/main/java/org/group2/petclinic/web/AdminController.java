
package org.group2.petclinic.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.PaymentService;
import org.group2.petclinic.service.VisitSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final String		VIEWS_SHOW_REVENUES			= "/admin/revenuesByMonth";

	private static final String		VIEWS_SHOW_VISITS			= "/admin/visits";

	private static final String		VIEWS_SHOW_VISITS_PAYMENT	= "/admin/payments";

	private static final String		VIEWS_SHOW_VISITS_DIAGNOSIS	= "/admin/diagnosis";

	@Autowired
	private PaymentService			paymentService;

	@Autowired
	private VisitSecretaryService	visitSecretaryService;


	@GetMapping(value = "/payment/revenues")
	public String showRenevuesByMonth(final ModelMap model) {
		List<Payment> listPayment = this.paymentService.findRevenuesByMonth();

		Map<String, Double> listMonth = new HashMap<String, Double>();
		listMonth.put("January", 0.00);
		listMonth.put("February", 0.00);
		listMonth.put("March", 0.00);
		listMonth.put("April", 0.00);
		listMonth.put("May", 0.00);
		listMonth.put("June", 0.00);
		listMonth.put("July", 0.00);
		listMonth.put("August", 0.00);
		listMonth.put("September", 0.00);
		listMonth.put("October", 0.00);
		listMonth.put("November", 0.00);
		listMonth.put("December", 0.00);

		for (int i = 0; i < listPayment.size(); i++) {

			Payment payment = listPayment.get(i);
			String month = payment.getMoment().getMonth().toString();

			if (month == "JANUARY") {
				Double dou = listMonth.get("January");
				listMonth.replace("January", dou + payment.getFinalPrice());
			} else if (month == "FEBRUARY") {
				Double dou = listMonth.get("February");
				listMonth.replace("February", dou + payment.getFinalPrice());
			} else if (month == "MARCH") {
				Double dou = listMonth.get("March");
				listMonth.replace("March", dou + payment.getFinalPrice());
			} else if (month == "APRIL") {
				Double dou = listMonth.get("April");
				listMonth.replace("April", dou + payment.getFinalPrice());
			} else if (month == "MAY") {
				Double dou = listMonth.get("May");
				listMonth.replace("May", dou + payment.getFinalPrice());
			} else if (month == "JUNE") {
				Double dou = listMonth.get("June");
				listMonth.replace("June", dou + payment.getFinalPrice());
			} else if (month == "JULY") {
				Double dou = listMonth.get("July");
				listMonth.replace("July", dou + payment.getFinalPrice());
			} else if (month == "AUGUST") {
				Double dou = listMonth.get("August");
				listMonth.replace("August", dou + payment.getFinalPrice());
			} else if (month == "SEPTEMBER") {
				Double dou = listMonth.get("September");
				listMonth.replace("September", dou + payment.getFinalPrice());
			} else if (month == "OCTOBER") {
				Double dou = listMonth.get("October");
				listMonth.replace("October", dou + payment.getFinalPrice());
			} else if (month == "NOVEMBER") {
				Double dou = listMonth.get("November");
				listMonth.replace("November", dou + payment.getFinalPrice());
			} else if (month == "DECEMBER") {
				Double dou = listMonth.get("December");
				listMonth.replace("December", dou + payment.getFinalPrice());
			}
		}

		LinkedHashMap<String, Double> sortedMap = listMonth.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		model.addAttribute("listMonth", sortedMap);

		return AdminController.VIEWS_SHOW_REVENUES;
	}

	@GetMapping(value = "/visits")
	public String showVisits(final ModelMap model) {
		List<Visit> listVisit = this.visitSecretaryService.findAllVisits();
		model.addAttribute("listVisit", listVisit);
		return VIEWS_SHOW_VISITS;
	}

	@GetMapping(value = "/visits/{visitId}/payment")
	public String showPaymentVisit(@PathVariable("visitId") int visitId, final ModelMap model) {
		Visit visit = this.visitSecretaryService.findVisitById(visitId);
		model.addAttribute("visit", visit);
		return VIEWS_SHOW_VISITS_PAYMENT;
	}

	@GetMapping(value = "/visits/{visitId}/diagnosis")
	public String showDiagnosisVisit(@PathVariable("visitId") int visitId, final ModelMap model) {
		Visit visit = this.visitSecretaryService.findVisitById(visitId);
		model.addAttribute("visit", visit);
		return VIEWS_SHOW_VISITS_DIAGNOSIS;
	}
}
