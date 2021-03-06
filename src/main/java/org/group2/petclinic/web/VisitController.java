
package org.group2.petclinic.web;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.Valid;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.LoremApiService;
import org.group2.petclinic.service.OwnerService;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.VetService;
import org.group2.petclinic.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class VisitController {

	// SERVICES ---------------------------------------------------------------

	private final VisitService		visitService;
	private final PetService		petService;
	private final VetService		vetService;
	private final OwnerService		ownerService;
	private final LoremApiService	loremApiService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public VisitController(final VisitService visitService,
		final PetService petService, final VetService vetService,
		final OwnerService ownerService, final LoremApiService loremApiService) {
		this.visitService = visitService;
		this.petService = petService;
		this.vetService = vetService;
		this.ownerService = ownerService;
		this.loremApiService = loremApiService;
	}

	// SET ALLOWED FIELDS -----------------------------------------------------

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	// VALIDATOR --------------------------------------------------------------

	@InitBinder("visit")
	public void initVisitBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new VisitValidator(this.visitService));
	}

	// MODEL ATTRIBUTES -------------------------------------------------------

	/**
	 * All vets. Necessary for the dropdown menu in visit/new
	 */
	@ModelAttribute("vets")
	public Collection<Vet> populateVets() {
		return this.vetService.findVets();
	}

	/**
	 * All visit types. Necessary for the dropdown menu in visit/new
	 */
	@ModelAttribute("visitTypes")
	public Collection<VisitType> populateVisitTypes() {
		return this.visitService.findVisitTypes();
	}

	// VIEWS ------------------------------------------------------------------

	/**
	 *
	 * owners/{ownerId}/pets/{petId}(visits/new
	 * View for an admin to schedule a visit for an owner
	 *
	 */
	@GetMapping(value = "/owners/*/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable("petId") final int petId,
		final ModelMap modelMap) {
		Pet pet = this.petService.findPetById(petId);
		Visit visit = new Visit();
		pet.addVisit(visit);
		visit.setPet(pet);
		modelMap.addAttribute("visit", visit);
		return "pets/createOrUpdateVisitForm";
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@PathVariable("petId") final int petId,
		@Valid final Visit visit, final BindingResult result) {
		Pet pet = this.petService.findPetById(petId);
		pet.addVisit(visit);
		visit.setPet(pet);
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		} else {
			this.visitService.saveVisit(visit);
			System.out.println("MIG_SAVE");
			return "redirect:/owners/{ownerId}";
		}
	}

	// MIGUEL ------------------------------------------------------------------

	/**
	 *
	 * owner/schedule-visit:
	 * View for the owner to schedule a visit.
	 *
	 */
	@GetMapping(value = "owner/schedule-visit")
	public String initScheduleVisitForm(final ModelMap modelMap) {
		String ownerUsername = SecurityContextHolder.getContext()
			.getAuthentication().getName();
		Owner owner = this.ownerService.findOwnerByUsername(ownerUsername);
		Collection<Pet> petsOfOwner = this.petService
			.findPetsByOwnerId(owner.getId());
		modelMap.addAttribute("petsOfOwner", petsOfOwner);

		Visit visit = new Visit();
		modelMap.addAttribute("visit", visit);

		return "owner/scheduleVisitForm";
	}

	@PostMapping(value = "owner/schedule-visit")
	public String processScheduleVisitForm(@Valid final Visit visit,
		final BindingResult result, final ModelMap modelMap) {
		if (result.hasErrors()) {
			String ownerUsername = SecurityContextHolder.getContext()
				.getAuthentication().getName();
			Owner owner = this.ownerService.findOwnerByUsername(ownerUsername);
			Collection<Pet> petsOfOwner = this.petService
				.findPetsByOwnerId(owner.getId());
			modelMap.addAttribute("petsOfOwner", petsOfOwner);

			return "owner/scheduleVisitForm";
		} else {
			this.visitService.saveVisit(visit);
			return "redirect:/";
		}
	}

	@GetMapping(value = "/vet/visits")
	public String showVisitsVet(final ModelMap modelMap) {
		String view = "/vet/visitsList";
		String vetUsername = SecurityContextHolder.getContext()
			.getAuthentication().getName();
		Vet vet = this.vetService.findVetByUsername(vetUsername);
		Iterable<Visit> futureVisits = this.visitService.findFutureVisitsByVet(vet);
		modelMap.addAttribute("futureVisits", futureVisits);
		Iterable<Visit> pastVisits = this.visitService.findPastVisitsByVet(vet);
		modelMap.addAttribute("pastVisits", pastVisits);
		
		return view;
	}

	// JOSEMA ------------------------------------------------------------------

	@GetMapping(value = "/vet/visits/{visitId}")
	public String showVisitForVet(@PathVariable("visitId") final int visitId,
		final ModelMap modelMap) {
		Visit visit = this.visitService.findVisitById(visitId);
		modelMap.addAttribute("visit", visit);
		Boolean bool = true;
		LocalDateTime today = LocalDateTime.now();
		if(visit.getMoment().isAfter(today))
			bool=false;
		modelMap.addAttribute("bool", bool);
		return "vet/visitDetails";
	}

	// MIGUEL -----------------------------------------------------------------

	@GetMapping(value = "/owner/visits")
	public String showVisitsOwner(final ModelMap modelMap) {
		String view = "owner/visitsList";
		String ownerUsername = SecurityContextHolder.getContext()
			.getAuthentication().getName();
		Owner owner = this.ownerService.findOwnerByUsername(ownerUsername);
		Iterable<Visit> futureVisits = this.visitService.findFutureVisitsByOwner(owner);
		modelMap.addAttribute("futureVisits", futureVisits);
		Iterable<Visit> pastVisits = this.visitService.findPastVisitsByOwner(owner);
		modelMap.addAttribute("pastVisits", pastVisits);
		return view;
	}

	@GetMapping(value = "/owner/visits/{visitId}")
	public String showVisitForOwner(@PathVariable("visitId") final int visitId,
		final ModelMap modelMap) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails.getAuthorities().toString().contains("owner")) {
			Owner log = ownerService.findOwnerByUsername(userDetails.getUsername());
			Owner ofVisit = visitService.findVisitById(visitId).getPet().getOwner();
			if (!log.getUser().getUsername().equals(ofVisit.getUser().getUsername())) {
				return "/exception";
			}
		}
		
		Visit visit = this.visitService.findVisitById(visitId);
		modelMap.addAttribute("visit", visit);

		PetType petType = visit.getPet().getType();
		String bannerURL = this.loremApiService.getRandomImageURL(petType.getName());

		modelMap.addAttribute("bannerURL", bannerURL);
		return "owner/visitDetails";
	}

}
