package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SpringDataCauseRepository extends CauseRepository, Repository<Cause, Integer> {

	
	@Override
	@Transactional
	@Modifying
	@Query("DELETE FROM Cause WHERE id = ?1")
	void delete(int causeId) throws DataAccessException;
	
	@Override
	@Transactional
	@Query("SELECT cause FROM Cause cause WHERE cause.id = ?1")
	public Cause findCauseById(int id) throws DataAccessException;


}