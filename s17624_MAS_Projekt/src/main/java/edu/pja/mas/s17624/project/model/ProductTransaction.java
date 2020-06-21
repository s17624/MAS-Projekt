package edu.pja.mas.s17624.project.model;

import edu.pja.mas.s17624.project.util.ProductTransactionId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "Mas_ProductTransaction")
public class ProductTransaction
{

    @EmbeddedId
    private ProductTransactionId productTransactionId = new ProductTransactionId();

    @Positive
    private int ammount;

    @Positive
    private double transactionPrice;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @MapsId("productId")
    @NotNull
    @JoinColumn(name = "product_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @MapsId("transactionId")
    @NotNull
    @JoinColumn(name = "transaction_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Transaction transaction;

    protected ProductTransaction()
    {

    }

    public ProductTransaction(Product product, Transaction transaction, int ammount)
    {
        setProduct(product);
        setTransaction(transaction);
        this.ammount = ammount;
        setTransactionPrice();
    }

    public ProductTransactionId getProductTransactionId()
    {
        return productTransactionId;
    }

    public int getAmmount()
    {
        return ammount;
    }

    public double getTransactionPrice()
    {
        return transactionPrice;
    }

    public Product getProduct()
    {
        return product;
    }

    public Transaction getTransaction()
    {
        return transaction;
    }

    public void setAmmount(int ammount)
    {
        this.ammount = ammount;
    }

    private void setTransactionPrice()
    {
        this.transactionPrice = product.getModel().getCurrentPrice();

    }

    public void setProduct(Product product)
    {
        this.product = product;
        product.addTransaction(this);
    }

    public void setTransaction(Transaction transaction)
    {
        this.transaction = transaction;
        transaction.addProduct(this);
    }

    public void remove()
    {
        if(transaction != null)
        {
            if(transaction.getProducts().contains(this))
            {
                transaction.removeProduct(this);
            }
            transaction = null;
        }

        if(product != null)
        {
            if(product.getTransactions().contains(this))
            {
                product.removeTransaction(this);
            }
            product = null;
        }
    }
}
