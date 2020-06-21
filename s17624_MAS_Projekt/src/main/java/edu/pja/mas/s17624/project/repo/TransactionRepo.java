package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepo extends CrudRepository<Transaction, Long>
{
}
