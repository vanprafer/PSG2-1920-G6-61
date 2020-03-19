package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.PetHotel;
import org.springframework.samples.petclinic.repository.PetHotelRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SpringDataPetHotelRepository extends PetHotelRepository, Repository<PetHotel, Integer> {
	@Override
	@Transactional
	@Modifying
	@Query("DELETE FROM PetHotel WHERE id = ?1")
	public void delete(int petHotelId) throws DataAccessException;
}