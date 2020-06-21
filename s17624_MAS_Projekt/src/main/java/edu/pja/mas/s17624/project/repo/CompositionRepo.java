package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.Composition;
import edu.pja.mas.s17624.project.util.CompositionId;
import org.springframework.data.repository.CrudRepository;

public interface CompositionRepo extends CrudRepository<Composition, CompositionId>
{

}
