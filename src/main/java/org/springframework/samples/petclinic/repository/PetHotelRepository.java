package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PetHotel;

public interface PetHotelRepository {
	
	List<PetHotel> findByPetId(int id);
	
	void save(PetHotel petHotel) throws DataAccessException;

}
