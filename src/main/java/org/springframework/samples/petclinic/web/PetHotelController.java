package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetHotel;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetHotelController {
	
	private final ClinicService clinicService;

	@Autowired
	public PetHotelController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("petHotel")
	public PetHotel loadPetWithPetHotel(@PathVariable("petId") int petId) {
		Pet pet = this.clinicService.findPetById(petId);
		PetHotel petHotel = new PetHotel();
		petHotel.setStart(LocalDate.now());
		petHotel.setFinish(LocalDate.now().plusDays((long)2));
		pet.addPetHotel(petHotel);
		return petHotel;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
	@GetMapping(value = "/owners/*/pets/{petId}/petHotels/new")
	public String initNewPetHotelForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "pets/createOrUpdatePetHotelForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/petHotels/new")
	public String processNewPetHotelForm(@Valid PetHotel petHotel, BindingResult result) {
		
		if (result.hasErrors()) {
			return "pets/createOrUpdatePetHotelForm";
		}
		else {
			this.clinicService.savePetHotelBooking(petHotel);
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/owners/*/pets/{petId}/petHotels")
	public String showPetHotels(@PathVariable int petId, Map<String, Object> model) {
		model.put("petHotels", this.clinicService.findPetById(petId).getPetHotels());
		return "petHotelList";
	}
	
	
    @GetMapping(value = "/owners/{ownerId}/pets/{petId}/petHotels/{petHotelId}/delete")
    public String delete(@PathVariable("petHotelId") int hotelId,@PathVariable("ownerId") int ownerId, @PathVariable("petId") int petId) {
        this.clinicService.deletePetHotelBooking(hotelId);
        return "redirect:/owners/"+ownerId;
     
    }

}
