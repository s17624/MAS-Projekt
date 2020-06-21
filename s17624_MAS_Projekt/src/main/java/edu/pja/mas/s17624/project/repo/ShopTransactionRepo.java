package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Shop;
import edu.pja.mas.s17624.project.model.ShopTransaction;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShopTransactionRepo extends CrudRepository<ShopTransaction, Long>
{
    List<ShopTransaction> findByShopAndTransactionDateBetween(Shop shop, LocalDateTime from, LocalDateTime to);
}
