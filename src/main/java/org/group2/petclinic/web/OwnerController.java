
package org.group2.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.service.AuthoritiesService;
import org.group2.petclinic.service.OwnerService;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.UserService;
import org.group2.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OwnerController {

	private static final String	VIEWS_OWNER_CREATE_OR_UPDATE_FORM	= "owners/createOrUpdateOwnerForm";

	// SERVICES ---------------------------------------------------------------

	private final OwnerService	ownerService;
	private final PetService	petService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public OwnerController(final OwnerService ownerService, final PetService petService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.ownerService = ownerService;
		this.petService = petService;
	}

	// SET ALLOWED FIELDS -----------------------------------------------------

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	// VALIDATOR --------------------------------------------------------------

	// MODEL ATTRIBUTES -------------------------------------------------------

	// VIEWS ------------------------------------------------------------------

	@GetMapping(value = "/owners/new")
	public String initCreationForm(final Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/new")
	public String processCreationForm(@Valid final Owner owner, final BindingResult result) {
		if (result.hasErrors()) {
			return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			//creating owner, user and authorities
			this.ownerService.saveOwner(owner);

			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping(value = "/owners/find")
	public String initFindForm(final Map<String, Object> model) {
		model.put("owner", new Owner());
		return "owners/findOwners";
	}

	@GetMapping(value = "/owners")
	public String processFindForm(Owner owner, final BindingResult result, final Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Owner> results = this.ownerService.findOwnerByLastName(owner.getLastName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		} else if (results.size() == 1) {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		} else {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") final int ownerId, final Model model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		model.addAttribute(owner);
		return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result, @PathVariable("ownerId") final int ownerId) {
		if (result.hasErrors()) {
			return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			owner.setId(ownerId);
			this.ownerService.saveOwner(owner);
			return "redirect:/owners/{ownerId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param ownerId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") final int ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(this.ownerService.findOwnerById(ownerId));
		return mav;
	}

	//----- NEW METHODS (Miguel)

	/**
	 *
	 * (Miguel)
	 *
	 * View that shows the profile of the authenticated owner
	 *
	 */
	@GetMapping("/owner/profile")
	public ModelAndView showOwnerInfo() {
		ModelAndView mav = new ModelAndView("owner/profileView");
		String ownerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject(this.ownerService.findOwnerByUsername(ownerUsername));
		return mav;
	}

	/**
	 *
	 * (Miguel)
	 *
	 * View that shows lets the authenticated owner edit his profile
	 *
	 */
	@GetMapping(value = "/owner/profile/edit")
	public String initUpdateOwnerForm(final Model model) {
		String ownerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute(this.ownerService.findOwnerByUsername(ownerUsername));
		return "owner/profileForm";
	}

	@PostMapping(value = "/owner/profile/edit")
	public String processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result) {
		if (result.hasErrors()) {
			return "owner/profileForm";
		} else {
			String authenticatedOwnerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
			Owner authenticatedOwner = this.ownerService.findOwnerByUsername(authenticatedOwnerUsername);
			owner.setId(authenticatedOwner.getId());
			this.ownerService.saveOwner(owner);
			return "redirect:/owner/profile";
		}
	}

	/**
	 *
	 * (Miguel)
	 *
	 * View that shows the profile of the authenticated owner
	 *
	 */
	@GetMapping("/owner/pets")
	public ModelAndView showOwnersPets() {
		ModelAndView mav = new ModelAndView("owner/petsList");
		String ownerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject(this.ownerService.findOwnerByUsername(ownerUsername));
		return mav;
	}

	@GetMapping(value = "/owner/pets/new")
	public String initCreationForm(final ModelMap model) {
		String ownerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Owner owner = this.ownerService.findOwnerByUsername(ownerUsername);
		Pet pet = new Pet();
		owner.addPet(pet);
		model.put("pet", pet);
		model.addAttribute("types", this.petService.findPetTypes());
		return "owner/createOrUpdatePetForm";
	}

	@PostMapping(value = "/owner/pets/new")
	public String processCreationForm(@Valid final Pet pet, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			String ownerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
			Owner owner = this.ownerService.findOwnerByUsername(ownerUsername);
			owner.addPet(pet);
			model.put("pet", pet);
			model.addAttribute("types", this.petService.findPetTypes());
			return "owner/createOrUpdatePetForm";
		} else {
			try {
				String ownerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
				Owner owner = this.ownerService.findOwnerByUsername(ownerUsername);
				owner.addPet(pet);
				this.petService.savePet(pet);
			} catch (DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return "owner/createOrUpdatePetForm";
			}
			return "redirect:/owner/pets";
		}
	}

	@GetMapping(value = "/owner/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") final int petId, final ModelMap model) {
		Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		model.addAttribute("types", this.petService.findPetTypes());
		return "owner/createOrUpdatePetForm";
	}

	@PostMapping(value = "owner/pets/{petId}/edit")
	public String processUpdateForm(@Valid final Pet pet, final BindingResult result, @PathVariable("petId") final int petId, final ModelMap model) {
		//String authenticatedOwnerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		//Owner authenticatedOwner = this.ownerService.findOwnerByUsername(authenticatedOwnerUsername);
		//authenticatedOwner.addPet(pet);
		model.addAttribute("types", this.petService.findPetTypes());
		if (result.hasErrors()) {
			model.put("pet", pet);
			return "owner/createOrUpdatePetForm";
		} else {
			Pet petToUpdate = this.petService.findPetById(petId);
			BeanUtils.copyProperties(pet, petToUpdate, "id", "owner", "visits");
			try {
				this.petService.savePet(petToUpdate);
			} catch (DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return "owner/createOrUpdatePetForm";
			}
			return "redirect:/owner/pets";
		}
	}

}
