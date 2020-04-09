package org.group2.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.VisitTypeService;
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
public class VisitTypeController {

	private final VisitTypeService visitTypeService;

	@Autowired
	public VisitTypeController(VisitTypeService visitTypeService) {
		this.visitTypeService = visitTypeService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/admin/visitTypes")
	public String showVisitTypes(final ModelMap modelMap) {
		
		String view = "/admin/visitTypesList";
		Iterable<VisitType> visitTypes = this.visitTypeService.findVisitTypes();
		modelMap.addAttribute("visitTypes", visitTypes);
		
		return view;
	}
	
	@GetMapping(value = "/admin/visitTypes/new")
	public String initNewVisitTypeForm(Map<String, Object> model) {
		VisitType visitType = new VisitType();
		model.put("visitType", visitType);
		return "admin/createVisitTypeForm";
	}

	@PostMapping(value = "/admin/visitTypes/new")
	public String processNewVisitTypeForm(@Valid VisitType visitType, BindingResult result) {
		
		if (result.hasErrors()) {
			return "admin/createVisitTypeForm";
		}
		else {
			this.visitTypeService.saveVisitType(visitType);
			return "redirect:/admin/visitTypes";
		}
	}
	
	@GetMapping(value = "/admin/visitTypes/{visitTypeId}/edit")
	public String initUpdateVisitTypeForm(@PathVariable("visitTypeId") int visitTypeId, ModelMap model) {
		VisitType visitType = this.visitTypeService.findVisitTypeById(visitTypeId);
		model.put("visitType", visitType);
		return "admin/updateVisitTypeForm";
	}

	@PostMapping(value = "/admin/visitTypes/{visitTypeId}/edit")
	public String processUpdateVisitTypeForm(@Valid VisitType visitType, BindingResult result, @PathVariable("visitTypeId") final int visitTypeId, ModelMap model) {
		
		if (result.hasErrors()) {
			model.put("VisitType", visitType);
			return "admin/updateVisitTypeForm";
		}
		else {
			visitType.setId(visitTypeId);
			this.visitTypeService.saveVisitType(visitType);
			return "redirect:/admin/visitTypes";
		}
	}
	

	@ModelAttribute("visitTypes")
	public Collection<VisitType> populateVisitTypes() {
		return this.visitTypeService.findVisitTypes();
	}

}
