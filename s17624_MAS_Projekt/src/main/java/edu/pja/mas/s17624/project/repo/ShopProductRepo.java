package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Shop;
import edu.pja.mas.s17624.project.model.ShopProduct;
import edu.pja.mas.s17624.project.util.ShopProductId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShopProductRepo extends CrudRepository<ShopProduct, ShopProductId>
{
    List<ShopProduct> findByShop(Shop shop);
}
