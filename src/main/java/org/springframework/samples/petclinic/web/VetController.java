/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {
	
	private static final String VIEWS_VET_CREATE_OR_UPDATE_FORM = "vets/createOrUpdateVetForm";

	private final ClinicService clinicService;

	@Autowired
	public VetController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@ModelAttribute("specialties")
	public Collection<Specialty> populateSpecialitiesTypes() {
		return this.clinicService.findSpecialities();
	}


	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		return vets;
	}
	
	@GetMapping(value = "/vets/new")
	public String initCreationForm(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/vets/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}
		else {
			System.out.println(result.getModel().get("_specialties"));
			this.clinicService.saveVet(vet);
			return "redirect:/vets" ;
		}
	}
}
