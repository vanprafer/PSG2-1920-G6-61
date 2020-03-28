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
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.PetHotel;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.repository.PetHotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicService {

	private PetRepository petRepository;

	private VetRepository vetRepository;

	private OwnerRepository ownerRepository;

	private VisitRepository visitRepository;

	private PetHotelRepository petHotelRepository;

	private CauseRepository causeRepository;

	@Autowired
	public ClinicService(PetRepository petRepository, VetRepository vetRepository, OwnerRepository ownerRepository,
			VisitRepository visitRepository, PetHotelRepository petHotelRepository, CauseRepository causeRepository) {
		this.petRepository = petRepository;
		this.vetRepository = vetRepository;
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
		this.petHotelRepository = petHotelRepository;
		this.causeRepository = causeRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) throws DataAccessException {
		return ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
		return ownerRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveOwner(Owner owner) throws DataAccessException {
		ownerRepository.save(owner);
	}

	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional
	public void savePet(Pet pet) throws DataAccessException {
		petRepository.save(pet);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "vets")
	public Collection<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Collection<Specialty> findSpecialities() throws DataAccessException {
		return vetRepository.findSpecialityTypes();
	}
	

	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}
	
	public void deletePet(int petId) throws DataAccessException{
		Collection<Visit> visits = visitRepository.findByPetId(petId);
		for(Visit v : visits) {
			visitRepository.delete(v.getId());
		}
		Collection<PetHotel> hotels = petHotelRepository.findByPetId(petId);
		for(PetHotel ph : hotels){
			petHotelRepository.delete(ph.getId());
		}
		petRepository.delete(petId);
	}
	
	@Transactional(readOnly = true)
	public Vet findVetById(int id) throws DataAccessException{
		return vetRepository.findVetById(id);
	}
	
	@Transactional
	public void saveVet(Vet vet) throws DataAccessException{
		vetRepository.save(vet);
	}

	public void deleteVisit(int visitId) throws DataAccessException{
		visitRepository.delete(visitId);
	}

	public void deleteOwner(int ownerId) throws DataAccessException{
		ownerRepository.delete(ownerId);
		
	}
		
	@Transactional(readOnly = true)
	public Collection<PetHotel> findPetHotelById(int id) throws DataAccessException {
		return petHotelRepository.findByPetId(id);
	}
	@Transactional(readOnly = true)
	public PetHotel findPetHotelByHotelId(int id) throws DataAccessException {
		return petHotelRepository.findById(id);
	}
	
	@Transactional
	public void savePetHotelBooking(PetHotel petHotel) throws DataAccessException {
		petHotelRepository.save(petHotel);
	}
	
	public void deletePetHotelBooking(int petHotelId) throws DataAccessException{
		petHotelRepository.delete(petHotelId);
		
	}
	
	public void deleteVet(int vetId) throws DataAccessException{
		vetRepository.delete(vetId);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "causes")
	public Collection<Cause> findCauses() throws DataAccessException {
		return causeRepository.findAll();
	}

}
