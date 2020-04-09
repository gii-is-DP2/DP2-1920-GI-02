
package org.group2.petclinic.web;

import java.util.Map;

import org.group2.petclinic.service.LoremApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	private LoremApiService loremApiService;


	@Autowired
	public WelcomeController(LoremApiService loremApiService) {
		this.loremApiService = loremApiService;
	}

	@GetMapping({
		"/", "/welcome"
	})
	public String welcome(Map<String, Object> model) {
		String bannerURL = this.loremApiService.getRandomImageURL("pet");

		model.put("bannerURL", bannerURL);

		return "welcome";
	}
}
