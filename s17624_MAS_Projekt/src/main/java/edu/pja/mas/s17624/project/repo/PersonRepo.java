package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface PersonRepo extends CrudRepository<Person, Long>
{

    @Override
    List<Person> findAll();

    @Override
    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.onlineTransactions t WHERE p.id = :id")
    Optional<Person> findById(Long id);
}
