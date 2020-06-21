package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.OnlineTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OnlineTransactionRepo extends CrudRepository<OnlineTransaction, Long>
{

}
