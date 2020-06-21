package edu.pja.mas.s17624.project.model;

import edu.pja.mas.s17624.project.util.ModelPromotionId;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "Mas_Model_Promotion")
public class ModelPromotion
{
    @EmbeddedId
    private ModelPromotionId modelPromotionId = new ModelPromotionId();

    @Positive
    @Max((int)maxDiscount)
    private double discount;

    private static final double maxDiscount = 75;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("modelId")
    @NotNull
    @JoinColumn(name = "model_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Model model;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("promoId")
    @NotNull
    @JoinColumn(name = "promo_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Promotion promotion;

    protected ModelPromotion()
    {

    }

    public ModelPromotion(Model model, Promotion promotion, double discount)
    {
        setModel(model);
        setPromotion(promotion);
        this.discount = discount;
    }

    public ModelPromotionId getModelPromotionId()
    {
        return modelPromotionId;
    }

    public double getDiscount()
    {
        return discount;
    }

    public Model getModel()
    {
        return model;
    }

    public Promotion getPromotion()
    {
        return promotion;
    }

    public void setDiscount(double discount)
    {
        this.discount = discount;
    }

    public void setModel(Model model)
    {
        this.model = model;
        model.addPromotion(this);
    }

    public void setPromotion(Promotion promotion)
    {
        this.promotion = promotion;
        promotion.addModel(this);
    }

    public void remove()
    {
        if(model != null)
        {
            if(model.getPromotions().contains(this))
            {
                model.removePromotion(this);
            }
            model = null;
        }

        if(promotion != null)
        {
            if(promotion.getModels().contains(this))
            {
                promotion.removeModel(this);
            }
            promotion = null;
        }
    }
}
