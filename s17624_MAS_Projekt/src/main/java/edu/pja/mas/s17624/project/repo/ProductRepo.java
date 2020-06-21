package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Model;
import edu.pja.mas.s17624.project.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long>
{
    List<Product> findByModel(Model model);
}
