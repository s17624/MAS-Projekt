package edu.pja.mas.s17624.project.model;

import edu.pja.mas.s17624.project.util.Address;
import edu.pja.mas.s17624.project.util.AddressConverter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "transaction_Id")
@DiscriminatorValue("ONLINE")
@Table(name = "Mas_OnlineTransaction")
public class OnlineTransaction extends Transaction
{
    public enum TransactionStatus
    {
        OPEN, PAID, SENT, CANCELLED
    }

    @Column(columnDefinition = "DATE")
    @PastOrPresent
    private LocalDate orderDate;

    @Column(columnDefinition = "DATE")
    @PastOrPresent
    @Nullable
    private LocalDate realizationDate;

    @Nullable
    private String note;

    @NotNull
    @Convert(converter = AddressConverter.class)
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "client_Id", referencedColumnName = "id")
    private Person client;

    protected OnlineTransaction()
    {

    }

    public OnlineTransaction(LocalDate orderDate)
    {
        status = TransactionStatus.OPEN;
        setOrderDate(orderDate);
    }

    public LocalDate getOrderDate()
    {
        return orderDate;
    }

    @Nullable
    public LocalDate getRealizationDate()
    {
        return realizationDate;
    }

    @Nullable
    public String getNote()
    {
        return note;
    }

    public Address getDeliveryAddress()
    {
        return deliveryAddress;
    }

    public TransactionStatus getStatus()
    {
        return status;
    }

    public Person getClient()
    {
        return client;
    }

    public void setOrderDate(LocalDate orderDate)
    {
        this.orderDate = orderDate;
    }

    public void setRealizationDate(@Nullable LocalDate realizationDate) throws Exception
    {
        if(!status.equals(TransactionStatus.PAID))
        {
            throw new Exception("You can set date only for paid transaction!");
        }
        this.realizationDate = realizationDate;
        setStatus(TransactionStatus.SENT);
    }

    public void setNote(@Nullable String note)
    {
        this.note = note;
    }

    public void setDeliveryAddress(Address deliveryAddress) throws Exception
    {
        if(!status.equals(TransactionStatus.OPEN))
        {
            throw new Exception("Delivery addres can be set only for opened transactions!");
        }
        this.deliveryAddress = deliveryAddress;
    }

    public void setStatus(TransactionStatus status) throws Exception
    {
        if(status.equals(TransactionStatus.SENT) || status.equals(TransactionStatus.CANCELLED))
        {
            throw new Exception("Cannot change status from SENT or CANCELLED!");
        }
        this.status = status;
    }

    public void setClient(Person client) throws Exception
    {
        if(!status.equals(TransactionStatus.OPEN))
        {
            throw new Exception("Client can be set only for OPEN transactions!");
        }
        this.client = client;
        client.addOnlineTransaction(this);;
    }
}
