package edu.pja.mas.s17624.project.repo;

import edu.pja.mas.s17624.project.model.ModelPromotion;
import edu.pja.mas.s17624.project.util.ModelPromotionId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ModelPromotionRepo extends CrudRepository<ModelPromotion, ModelPromotionId>
{
}
