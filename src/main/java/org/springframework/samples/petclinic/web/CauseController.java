package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Causes;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CauseController {

	private final ClinicService clinicService;

	@Autowired
	public CauseController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

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
}