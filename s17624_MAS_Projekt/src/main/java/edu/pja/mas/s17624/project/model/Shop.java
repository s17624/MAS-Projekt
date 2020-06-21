package edu.pja.mas.s17624.project.model;

import edu.pja.mas.s17624.project.util.Address;
import edu.pja.mas.s17624.project.util.AddressConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Mas_Shop")
public class Shop
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Convert(converter = AddressConverter.class)
    @NotNull
    private Address address;

    @Transient
    private static final int maxEmployees = 4;

    @OneToMany(mappedBy = "shop", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Employment> employments = new HashSet<>();

    @OneToMany(mappedBy = "shop", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ShopTransaction> shopTransactions = new HashSet<>();

    @OneToMany(mappedBy = "shop", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ShopProduct> products = new HashSet<>();

    protected Shop()
    {

    }

    public Shop(Address address)
    {
        this.address = address;
    }

    public long getId()
    {
        return id;
    }

    public Address getAddress()
    {
        return address;
    }

    public static int getMaxEmployees()
    {
        return maxEmployees;
    }


    public int getCurrentEmploymentsCount()
    {
        int activeEmployments = 0;
        for(Employment e : employments)
        {
            if(e.getDateTo() == null)
            {
                activeEmployments++;
            }
        }
        return activeEmployments;
    }

    public Set<Employment> getEmployments()
    {
        return employments;
    }

    public Set<ShopTransaction> getShopTransactions()
    {
        return shopTransactions;
    }

    public Set<ShopProduct> getProducts()
    {
        return products;
    }

    public void addAddress(Address address)
    {
        this.address = address;
    }

    public void addEmployment(Employment employment) throws Exception
    {
        if(getCurrentEmploymentsCount() > maxEmployees)
        {
            throw new Exception("Maximum number of active employments reached!");
        }
        if(!employments.contains(employment))
        {
            employments.add(employment);
            employment.setShop(this);
        }
    }

    public void addShopTransaction(ShopTransaction shopTransaction)
    {
        if(!shopTransactions.contains(shopTransaction))
        {
            shopTransactions.add(shopTransaction);
            shopTransaction.setShop(this);
        }
    }

    public void addProduct(ShopProduct product)
    {
        if(!products.contains(product))
        {
            products.add(product);
            product.setShop(this);
        }
    }

    public void removeEmployment(Employment employment) throws Exception
    {
        if(employments.contains(employment))
        {
            employments.remove(employment);
            employment.remove();
        }
    }

    public void removeShopTransaction(ShopTransaction shopTransaction)
    {
        if(shopTransactions.contains(shopTransaction))
        {
            shopTransactions.remove(shopTransaction);
            shopTransaction.setShop(null);
        }
    }

    public void removeProduct(ShopProduct product)
    {
        if(products.contains(product))
        {
            products.remove(product);
            product.remove();
        }

    }
}
