
package org.group2.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.VetService;
import org.group2.petclinic.service.VisitService;
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

@Controller
public class VisitController {

	// SERVICES ---------------------------------------------------------------

	private final VisitService	visitService;
	private final PetService	petService;
	private final VetService	vetService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public VisitController(final VisitService visitService, final PetService petService, final VetService vetService) {
		this.visitService = visitService;
		this.petService = petService;
		this.vetService = vetService;
	}

	// SET ALLOWED FIELDS -----------------------------------------------------

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	// MODEL ATTRIBUTES -------------------------------------------------------

	/**
	 * Called before each and every @GetMapping or @PostMapping annotated method. 2 goals:
	 * - Make sure we always have fresh data
	 * - Since we do not use the session scope, make sure that Pet object always
	 * has an id (Even though id is not part of the form
	 * fields)
	 */
	@ModelAttribute("visit")
	public Visit loadPetWithVisit(@PathVariable("petId") final int petId) {
		Pet pet = this.petService.findPetById(petId);
		Visit visit = new Visit();
		pet.addVisit(visit);
		return visit;
	}

	/**
	 * All vets. Necesarry for the dropdown menu in visit/new
	 */
	@ModelAttribute("vets")
	public Collection<Vet> populateVets() {
		return this.vetService.findVets();
	}

	/**
	 * All visit types. Necesarry for the dropdown menu in visit/new
	 */
	@ModelAttribute("visitTypes")
	public Collection<VisitType> populateVisitTypes() {
		return this.visitService.findVisitTypes();
	}

	// VIEWS ------------------------------------------------------------------

	@GetMapping(value = "/owners/*/pets/{petId}/visits")
	public String showVisits(@PathVariable final int petId, final Map<String, Object> model) {
		model.put("visits", this.petService.findPetById(petId).getVisits());
		return "visitList";
	}

	@GetMapping(value = "/owners/*/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable("petId") final int petId, final ModelMap modelMap) {
		return "pets/createOrUpdateVisitForm";
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid final Visit visit, final BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		} else {
			this.petService.saveVisit(visit);
			return "redirect:/owners/{ownerId}";
		}
	}

}
