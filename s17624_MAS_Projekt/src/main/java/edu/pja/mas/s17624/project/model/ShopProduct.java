package edu.pja.mas.s17624.project.model;

import edu.pja.mas.s17624.project.util.ShopProductId;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Mas_Shop_Product")
public class ShopProduct
{
    @EmbeddedId
    private ShopProductId shopProductId = new ShopProductId();

    @PositiveOrZero
    private int stock;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("shopId")
    @JoinColumn(name = "shop_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Shop shop;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("productId")
    @JoinColumn(name = "product_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    protected ShopProduct()
    {

    }

    public ShopProduct(Product product, Shop shop, int stock)
    {
        setProduct(product);
        setShop(shop);
        this.stock = stock;
    }

    public ShopProductId getShopProductId()
    {
        return shopProductId;
    }

    public Product getProduct()
    {
        return product;
    }

    public Shop getShop()
    {
        return shop;
    }

    public int getStock()
    {
        return stock;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    public void setShop(Shop shop)
    {
        this.shop = shop;
        shop.addProduct(this);

    }

    public void setProduct(Product product)
    {
        this.product = product;
        product.addShop(this);
    }

    public void remove()
    {
        if(shop != null)
        {
            if(shop.getProducts().contains(this))
            {
                shop.removeProduct(this);
            }
            shop = null;
        }

        if(product != null)
        {
            if(product.getShops().contains(this))
            {
                product.removeShop(this);
            }
            product = null;
        }
    }

}
