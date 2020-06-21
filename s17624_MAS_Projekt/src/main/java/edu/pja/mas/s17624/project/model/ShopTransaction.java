package edu.pja.mas.s17624.project.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "transaction_Id")
@DiscriminatorValue("SHOP")
@Table(name = "Mas_Shop_Transaction")
public class ShopTransaction extends Transaction
{
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime transactionDate;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "shop_Id", referencedColumnName = "id")
    private Shop shop;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employee_Id", referencedColumnName = "id")
    private Person employee;

    protected ShopTransaction()
    {

    }

    public ShopTransaction(Person employee, Shop shop, LocalDateTime transactionDate) throws Exception
    {
        super();
        setEmployee(employee);
        setShop(shop);
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getTransactionDate()
    {
        return transactionDate;
    }

    public Shop getShop()
    {
        return shop;
    }

    public Person getEmployee()
    {
        return employee;
    }

    public void setTransactionDate(LocalDateTime transactionDate)
    {
        this.transactionDate = transactionDate;
    }

    public void setShop(Shop shop)
    {
        this.shop = shop;
        shop.addShopTransaction(this);
    }

    public void setEmployee(Person person) throws Exception
    {
        this.employee = person;
        person.addShopTransaction(this);
    }
}
