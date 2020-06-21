package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Product;
import edu.pja.mas.s17624.project.model.ProductTransaction;
import edu.pja.mas.s17624.project.model.Transaction;
import edu.pja.mas.s17624.project.util.ProductTransactionId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductTransactionRepo extends CrudRepository<ProductTransaction, ProductTransactionId>
{
    List<ProductTransaction> findByTransaction(Transaction transaction);

    Optional<ProductTransaction> findByTransactionAndProduct(Transaction transcation, Product product);

}
