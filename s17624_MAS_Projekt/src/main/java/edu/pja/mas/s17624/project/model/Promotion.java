package edu.pja.mas.s17624.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Mas_Promotion")
public class Promotion
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String promotionName;

    private String description;

    @Column(columnDefinition = "DATE")
    @NotNull
    private LocalDate dateFrom;

    @Column(columnDefinition = "DATE")
    @NotNull
    private LocalDate dateTo;

    @OneToMany(mappedBy = "promotion", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ModelPromotion> models = new HashSet<>();

    protected Promotion()
    {

    }

    public Promotion(String promotionName, String description, LocalDate dateFrom, LocalDate dateTo)
    {
        this.promotionName = promotionName;
        this.description = description;
        setDateFrom(dateFrom);
        setDateTo(dateTo);
    }

    public long getId()
    {
        return id;
    }

    public String getPromotionName()
    {
        return promotionName;
    }

    public String getDescription()
    {
        return description;
    }

    public LocalDate getDateFrom()
    {
        return dateFrom;
    }

    public LocalDate getDateTo()
    {
        return dateTo;
    }

    public Set<ModelPromotion> getModels()
    {
        return models;
    }

    public void setPromotionName(String promotionName)
    {
        this.promotionName = promotionName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setDateFrom(LocalDate dateFrom)
    {
        if(dateTo != null)
        {
            if(dateFrom.isAfter(dateTo))
            {
                throw new IllegalArgumentException("Promotion start cannot be after ending date!");
            }
        }
        this.dateFrom = dateFrom;
    }

    public void setDateTo(LocalDate dateTo)
    {
        if(dateTo != null)
        {
            if (dateTo.isBefore(dateFrom))
            {
                throw new IllegalArgumentException("Promotion end date must be after starting date!");
            }
        }
        this.dateTo = dateTo;
    }

    public void addModel(ModelPromotion model)
    {
        if(models.contains(model))
        {
            models.add(model);
            model.setPromotion(this);
        }

    }

    public void removeModel(ModelPromotion model)
    {
        if(models.contains(model))
        {
            models.remove(model);
            model.remove();
        }
    }
}
