package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Causes;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CauseController {
	
	private static final String VIEWS_CAUSE_CREATE_OR_UPDATE_FORM = "causes/createOrUpdateCauseForm";

	private final ClinicService clinicService;

	@Autowired
	public CauseController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	
	
	// LISTAR CAUSAS
	
	@GetMapping(value = { "/causes" })
	public String showCauseList(Map<String, Object> model) {
		Causes causes = new Causes();
		causes.getCauseList().addAll(this.clinicService.findCauses());
		model.put("causes", causes);
		return "causes/causesList";
	}

	@GetMapping(value = { "/causes.xml"})
	public @ResponseBody Causes showResourcesCauseList() {
		Causes causes = new Causes();
		causes.getCauseList().addAll(this.clinicService.findCauses());
		return causes;
	}
	
	
	
	// CREAR CAUSAS
	
	@GetMapping(value = "/causes/new")
	public String initCreationForm(Map<String, Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/causes/new")
	public String processCreationForm(@Valid Cause cause, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.clinicService.saveCause(cause);
			return "redirect:/causes" ;
		}
	}
	
	
	
	// EDITAR CAUSAS
	
	@GetMapping(value = "/causes/{causeId}/edit")
	public String initUpdateForm(@PathVariable("causeId") int causeId, Model model) {
		Cause cause = this.clinicService.findCauseById(causeId);
		model.addAttribute(cause);
		return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/causes/{causeId}/edit")
	public String processUpdateForm(@Valid Cause cause, BindingResult result,
			@PathVariable("causeId") int causeId) {
		if (result.hasErrors()) {
			return VIEWS_CAUSE_CREATE_OR_UPDATE_FORM;
		}
		else {
			cause.setId(causeId);
			this.clinicService.saveCause(cause);
			return "redirect:/causes";
		}
	}
	
	
	
	// ELIMICAR CAUSAS
	
	@GetMapping(path="/causes/delete/{causeId}")
	public String borrarCausa(@PathVariable("causeId") int causeId){
		String view="redirect:/causes";
		clinicService.deleteCause(causeId);
		return view;
	}
	
	
}