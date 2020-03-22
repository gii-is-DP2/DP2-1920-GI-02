
package org.group2.petclinic.web;

import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.VisitSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visits")
public class VisitSecretaryController {

	@Autowired
	private VisitSecretaryService visitSecretaryService;


	@GetMapping()
	public String listPayments(final Secretary secretary, final ModelMap modelMap) {
		String vista = "/visits/noPay";
		Iterable<Visit> visits = this.visitSecretaryService.findVisitsNoPayment();
		modelMap.addAttribute("visits", visits);
		return vista;
	}

}
