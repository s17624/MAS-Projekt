package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Category;
import edu.pja.mas.s17624.project.model.Model;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ModelRepo extends CrudRepository<Model, Long>
{
    @Override
    List<Model> findAll();

    List<Model> findByNameContainsIgnoreCase(String name);

    List<Model> findByCategoriesIn(Set<Category> categories);

    List<Model> findByNameContainsIgnoreCaseAndCategoriesIn(String name, Set<Category> categories);
}
