package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Category;
import edu.pja.mas.s17624.project.util.CompositionId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepo extends CrudRepository<Category, CompositionId>
{
    @Override
    List<Category> findAll();

}
