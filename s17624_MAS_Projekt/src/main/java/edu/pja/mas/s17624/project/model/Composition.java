package edu.pja.mas.s17624.project.model;

import edu.pja.mas.s17624.project.util.CompositionId;
import edu.pja.mas.s17624.project.util.ModelPromotionId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "Mas_Composition")
public class Composition
{

    @EmbeddedId
    private CompositionId compositionId = new CompositionId();

    @Positive
    private int count;

    @NotBlank
    private String role;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("parentId")
    @NotNull
    @JoinColumn(name = "parent_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product parentProduct;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("childId")
    @NotNull
    @JoinColumn(name = "child_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product childProduct;

    protected Composition()
    {

    }

    public Composition(Product parentProduct, Product childProduct, int count, String role)
    {
        setChildProduct(childProduct);
        setParentProduct(parentProduct);
        this.count = count;
        this.role = role;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public Product getParentProduct()
    {
        return parentProduct;
    }

    public Product getChildProduct()
    {
        return childProduct;
    }

    public CompositionId getCompositionId()
    {
        return compositionId;
    }

    public void setChildProduct(Product childProduct)
    {
        this.childProduct = childProduct;
        childProduct.addPart(this);
    }

    public void setParentProduct(Product parentProduct)
    {
        this.parentProduct = parentProduct;
        parentProduct.addBuild(this);
    }

    public void remove()
    {
        if(parentProduct != null)
        {
            if(parentProduct.getBuiltOf().contains(this))
            {
                parentProduct.removePart(this);
            }
            parentProduct = null;
        }

        if(childProduct != null)
        {
            if(childProduct.getPartsOf().contains(this))
            {
                childProduct.removeBuild(this);
            }
            childProduct = null;
        }
    }

}
