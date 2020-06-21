package edu.pja.mas.s17624.project.util;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductTransactionId implements Serializable
{
    private long productId;
    private long transactionId;

    public ProductTransactionId()
    {

    }

    public ProductTransactionId(long productId, long transactionId)
    {
        this.productId = productId;
        this.transactionId = transactionId;
    }

    public long getProductId()
    {
        return productId;
    }

    public long getTransactionId()
    {
        return transactionId;
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
        ProductTransactionId p = (ProductTransactionId) obj;
        return (Objects.equals(this.getTransactionId(), p.getTransactionId()) &&
                Objects.equals(this.getProductId(), p.getProductId()));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getTransactionId(), getProductId());
    }
}
