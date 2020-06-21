package edu.pja.mas.s17624.project.model;

import edu.pja.mas.s17624.project.util.ShopProductId;
import edu.pja.mas.s17624.project.util.Size;
import edu.pja.mas.s17624.project.util.SizeConverter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Mas_Product")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Convert(converter = SizeConverter.class)
    @NotNull
    private Size productSize;

    @OneToMany(mappedBy = "parentProduct")
    private Set<Composition> partsOf = new HashSet<>();

    @OneToMany(mappedBy = "childProduct")
    private Set<Composition> builtOf = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "model_Id", referencedColumnName = "id")
    private Model model;

    @OneToMany(mappedBy = "product",
    fetch = FetchType.EAGER)
    private Set<ShopProduct> shops = new HashSet<>();

    @OneToMany(mappedBy = "product",
    fetch = FetchType.EAGER,
    cascade = CascadeType.MERGE)
    private Set<ProductTransaction> transactions = new HashSet<>();

    protected Product()
    {

    }

    public Product(Model model, Size size)
    {
        setModel(model);
        this.productSize = size;
    }

    public Size getProductSize()
    {
        return productSize;
    }

    public void setProductSize(Size productSize)
    {
        this.productSize = productSize;
    }

    public Set<Composition> getPartsOf()
    {
        return partsOf;
    }

    public Set<Composition> getBuiltOf()
    {
        return builtOf;
    }

    public long getId()
    {
        return id;
    }

    public Model getModel()
    {
        return model;
    }

    public Set<ShopProduct> getShops()
    {
        return shops;
    }

    public Set<ProductTransaction> getTransactions()
    {
        return transactions;
    }

    public void addPart(Composition composition)
    {
        if(builtOf.contains(composition))
        {
            builtOf.add(composition);
            composition.setParentProduct(this);
        }
    }

    public void addBuild(Composition composition)
    {
        if(partsOf.contains(composition))
        {
            partsOf.add(composition);
            composition.setChildProduct(this);
        }
    }

    public void addTransaction(ProductTransaction transaction)
    {
        if(!transactions.contains(transaction))
        {
            transactions.add(transaction);
            transaction.setProduct(this);
        }
    }

    public void removeTransaction(ProductTransaction transaction)
    {
        if(transactions.contains(transaction))
        {
            transactions.remove(transaction);
            transaction.remove();
        }
    }

    public void removePart(Composition composition)
    {
        if(builtOf.contains(composition))
        {
            builtOf.remove(composition);
            composition.remove();
        }
    }

    public void removeBuild(Composition composition)
    {
        if(partsOf.contains(composition))
        {
            partsOf.remove(composition);
            composition.remove();
        }
    }

    public void setModel(Model model)
    {
        if(this.model != null)
        {
            this.model.removeProduct(this);
        }
        model.addProduct(this);
        this.model = model;
    }

    public void addShop(ShopProduct shopProduct)
    {
        if(!shops.contains(shopProduct))
        {
            shops.add(shopProduct);
            shopProduct.setProduct(this);
        }
    }

    public void removeShop(ShopProduct shopProduct)
    {
        if(shops.contains(shopProduct))
        {
            shops.remove(shopProduct);
            shopProduct.remove();
        }
    }

    //checking if stocks of given size/product are avaiable
    public boolean checkAvailability()
    {
        for(ShopProduct s : shops)
        {
            if(s.getStock() > 0)
            {
                return true;
            }
        }
        return false;
    }

    //returnings stocks of given products
    public int checkAvailableStocks()
    {
        int stock = 0;
        for(ShopProduct s : shops)
        {
            stock += s.getStock();
        }
        return stock;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(!(obj instanceof Product))
        {
            return false;
        }
        Product p = (Product) obj;
        return (Objects.equals(this.getModel(), p.getModel()) &&
                Objects.equals(this.getProductSize(), p.getProductSize()));
    }
}
