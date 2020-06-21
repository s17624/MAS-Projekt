package edu.pja.mas.s17624.project.util;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ShopProductId implements Serializable
{
    private long productId;
    private long shopId;

    public ShopProductId()
    {

    }

    public ShopProductId(long productId, long shopId)
    {
        this.productId = productId;
        this.shopId = shopId;
    }

    public long getProductId()
    {
        return productId;
    }

    public long getShopId()
    {
        return shopId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(!(obj instanceof ShopProductId))
        {
            return false;
        }
        ShopProductId p = (ShopProductId) obj;
        return (Objects.equals(this.getShopId(), p.getShopId()) &&
                Objects.equals(this.getProductId(), p.getProductId()));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getShopId(), getProductId());
    }
}
