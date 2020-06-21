package edu.pja.mas.s17624.project.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Mas_Employement")
public class Employment
{
    public enum EmployeePosition
    {
        EMLPOYEE, MANAGER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "DATE")
    @NotNull
    private LocalDate dateFrom;

    @Column(columnDefinition = "DATE")
    @Nullable
    private LocalDate dateTo;

    @Min((int)Person.minimalPayrate)
    private double payRate;

    @Enumerated(EnumType.STRING)
    private EmployeePosition position;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "employee_Id", referencedColumnName = "id")
    private Person employee;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "shop_Id", referencedColumnName = "id")
    private Shop shop;

    protected Employment()
    {

    }

    public Employment(Shop shop, Person employee, LocalDate dateFrom, LocalDate dateTo, double payRate, EmployeePosition position) throws Exception
    {
        setShop(shop);
        setEmployee(employee);
        setDateFrom(dateFrom);
        setDateTo(dateTo);
        this.payRate = payRate;
        this.position = position;
    }

    public long getId()
    {
        return id;
    }

    public LocalDate getDateFrom()
    {
        return dateFrom;
    }

    public LocalDate getDateTo()
    {
        return dateTo;
    }

    public double getPayRate()
    {
        return payRate;
    }

    public EmployeePosition getPosition()
    {
        return position;
    }

    public Person getEmployee()
    {
        return employee;
    }

    public Shop getShop()
    {
        return shop;
    }

    public void setDateFrom(LocalDate dateFrom)
    {
        if(dateTo != null)
        {
            if(dateFrom.isAfter(dateTo))
            {
                throw new IllegalArgumentException("Promotion start cannot be after ending date!");
            }
        }
        this.dateFrom = dateFrom;
    }

    public void setDateTo(@Nullable LocalDate dateTo)
    {
        if(dateTo != null)
        {
            if(dateTo.isBefore(dateFrom))
            {
                throw new IllegalArgumentException("Promotion end date must be after starting date!");
            }
        }
        this.dateTo = dateTo;
    }

    public void setPayRate(double payRate)
    {
        this.payRate = payRate;
    }

    public void setPosition(EmployeePosition position)
    {
        this.position = position;
    }

    public void setEmployee(Person employee) throws Exception
    {
        this.employee = employee;
        employee.addEmployment(this);
    }

    public void setShop(Shop store) throws Exception
    {
        this.shop = store;
        store.addEmployment(this);
    }

    public void remove() throws Exception
    {
        if(employee != null)
        {
            if(employee.getEmployments().contains(this))
            {
                employee.removeEmployment(this);
            }
            employee = null;
        }

        if(shop != null)
        {
            if(shop.getEmployments().contains(this))
            {
                shop.removeEmployment(this);
            }
            shop = null;
        }
    }
}
