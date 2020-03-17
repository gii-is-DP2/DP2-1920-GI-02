
package org.group2.petclinic.web;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;


	@GetMapping()
	public String listPayments(final ModelMap modelMap) {
		String vista = "payments/listPayments";
		Iterable<Payment> payments = this.paymentService.findAllPayments();
		modelMap.addAttribute("payments", payments);
		return vista;
	}

}
