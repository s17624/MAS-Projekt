package edu.pja.mas.s17624.project.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "Mas_Model")
public class Model
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @Positive
    private double basePrice;

    @Positive
    private Double year;

    @NotBlank
    private String type;

    private boolean inSales;

    @Nullable
    private String photoLink;

    @Nullable
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Mas_Model_Attributes", joinColumns = @JoinColumn(name = "Model_Id"))
    @MapKeyColumn(name = "Attribute_Name")
    @Column(name = "Attribute_Value")
    private Map<String, String> attributes = new HashMap<>();

    @ManyToMany(mappedBy = "models",
            fetch = FetchType.LAZY)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "model",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "model",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ModelPromotion> promotions = new HashSet<>();

    protected Model()
    {

    }

    public Model(Category category, String name, String brand, double basePrice, String type, boolean inSales)
    {
        addCategory(category);
        this.name = name;
        this.brand = brand;
        this.basePrice = basePrice;
        this.type = type;
        this.inSales = inSales;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getBrand()
    {
        return brand;
    }

    public double getBasePrice()
    {
        return basePrice;
    }

    public Double getYear()
    {
        return year;
    }

    public String getType()
    {
        return type;
    }

    public boolean isInSales()
    {
        return inSales;
    }

    public Map<String, String> getAttributes()
    {
        return attributes;
    }

    public Set<Category> getCategories()
    {
        return categories;
    }

    public Set<Product> getProducts()
    {
        return products;
    }

    public Set<ModelPromotion> getPromotions()
    {
        return promotions;
    }

    public double getPriceFrom(LocalDate localDate)
    {
        double currentPrice = basePrice;
        for(ModelPromotion m : promotions)
        {
            LocalDate from = m.getPromotion().getDateFrom();
            LocalDate to = m.getPromotion().getDateTo();
            if(localDate.isAfter(from) && localDate.isBefore(to))
            {
                currentPrice *= (100 - m.getDiscount())/100;
            }
        }
        return currentPrice;
    }

    public double getCurrentPrice()
    {
        return getPriceFrom(LocalDate.now());
    }

    public String getPhotoLink()
    {
        return photoLink;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPhotoLink(String photoLink)
    {
        this.photoLink = photoLink;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public void setBasePrice(double basePrice)
    {
        this.basePrice = basePrice;
    }

    public void setYear(Double year)
    {
        this.year = year;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setInSales(boolean inSales)
    {
        this.inSales = inSales;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addAttribute(String attribute, String value)
    {
        removeAttribute(attribute);
        attributes.put(attribute, value);
    }

    public boolean removeAttribute(String attribute)
    {
        boolean deleted = attributes.containsKey(attribute);
        if(deleted)
        {
            attributes.remove(attribute);
        }
        return deleted;
    }

    public void addPromotion(ModelPromotion promotion)
    {
        if(promotions.contains(promotion))
        {
            promotions.add(promotion);
            promotion.setModel(this);
        }
    }

    public void addProduct(Product product)
    {
        if(!products.contains(product))
        {
            products.add(product);
            product.setModel(this);
        }
    }

    public void addCategory(Category category)
    {
        if(!categories.contains(category))
        {
            categories.add(category);
            category.addModel(this);
        }
    }

    public void removePromotion(ModelPromotion promotion)
    {
        if(promotions.contains(promotion))
        {
            promotions.remove(promotion);
            promotion.remove();
        }
    }

    public void removeProduct(Product product)
    {
        if(products.contains(product))
        {
            products.remove(product);
            product.setModel(null);
        }
    }

    public void removeCategory(Category category)
    {
        if(categories.contains(category))
        {
            categories.remove(category);
            category.removeModel(this);
        }
    }

    //checking if stocks for model are avaialble
    public boolean checkModelAvailability()
    {
        boolean result = false;
        if(inSales)
        {
            for(Product p: products)
            {
                if(p.checkAvailability());
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return String.format("%s, %s, %s: %.2f", type, brand, name, getCurrentPrice());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(!(obj instanceof Model))
        {
            return false;
        }
        Model m = (Model) obj;
        return (Objects.equals(this.getName(), m.getName()) &&
                Objects.equals(this.getBrand(), m.getBrand()) &&
                Objects.equals(this.getYear(), m.getYear()) &&
                Objects.equals(this.getType(), m.getType()));
    }
}
