package edu.pja.mas.s17624.project.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TRANSACTION_TYPE")
@Table(name = "Mas_Transaction")
public abstract class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Positive
    @Nullable
    private Double additionalDiscount;

    @OneToMany(mappedBy = "transaction", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ProductTransaction> products = new HashSet<>();

    public Transaction()
    {

    }

    public long getId()
    {
        return id;
    }

    public Set<ProductTransaction> getProducts()
    {
        return products;
    }


    public Transaction(Double additionalDiscount)
    {
        this.additionalDiscount = additionalDiscount;
    }

    @Nullable
    public Double getAdditionalDiscount()
    {
        return additionalDiscount;
    }

    public void setAdditionalDiscount(@Nullable Double additionalDiscount)
    {
        this.additionalDiscount = additionalDiscount;
    }

    public void addProduct(ProductTransaction product)
    {
        if(!products.contains(product))
        {
            products.add(product);
            product.setTransaction(this);
        }
    }

    public void removeProduct(ProductTransaction product)
    {
        if(products.contains(product))
        {
            products.remove(product);
            product.remove();
        }
    }

    public double summarizeTransaction()
    {
        double total = 0;
        for(ProductTransaction p : products)
        {
            total += (p.getTransactionPrice() * p.getAmmount());
        }
        return total;
    }


    //get product transaction having given product
    public ProductTransaction getProduct(Product product)
    {
        for(ProductTransaction p: products)
        {
            if(p.getProduct().equals(product))
            {
                return p;
            }
        }
        return null;
    }
}
