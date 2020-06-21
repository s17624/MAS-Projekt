package edu.pja.mas.s17624.project.util;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ModelPromotionId implements Serializable
{
    private long modelId;
    private long promoId;

    public ModelPromotionId()
    {

    }

    public ModelPromotionId(long modelId, long promoId)
    {
        this.modelId = modelId;
        this.promoId = promoId;
    }

    public long getModelId()
    {
        return modelId;
    }

    public long getPromoId()
    {
        return promoId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(!(obj instanceof ModelPromotionId))
        {
            return false;
        }
        ModelPromotionId m = (ModelPromotionId) obj;
        return (Objects.equals(this.getModelId(), m.getModelId()) &&
                Objects.equals(this.getPromoId(), m.getPromoId()));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getModelId(), getPromoId());
    }


}
