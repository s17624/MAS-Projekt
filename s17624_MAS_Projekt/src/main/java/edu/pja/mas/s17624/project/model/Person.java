package edu.pja.mas.s17624.project.model;

import edu.pja.mas.s17624.project.util.Address;
import edu.pja.mas.s17624.project.util.AddressConverter;
import edu.pja.mas.s17624.project.util.Contact;
import edu.pja.mas.s17624.project.util.ContactConverter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@Table(name = "Mas_Person")
public class Person
{
    enum PersonRole
    {
        PERSON, CLIENT, EMPLOYEE;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Column(columnDefinition = "DATE")
    @Past
    @NotNull
    private LocalDate birthDate;

    @Convert(converter = ContactConverter.class)
    private Contact contactInfo;

    @Convert(converter = AddressConverter.class)
    private Address addressInfo;

    @Transient
    public final static double minimalPayrate = 20d;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PersonRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Mas_Person_Role", joinColumns = @JoinColumn(name = "Person_Id"))
    @Column(name = "Role_Name")
    @NotEmpty
    private Set<PersonRole> roles = EnumSet.of(PersonRole.PERSON);

    @Column(columnDefinition = "DATE")
    @PastOrPresent
    private LocalDate registrationDate;

    /*
    @OneToMany(mappedBy = "client",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapKeyColumn(name = "transaction_Id")
    private Map<Long, OnlineTransaction> onlineTransactions = new HashMap<>();
     */

    @OneToMany(mappedBy = "client",
    fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<OnlineTransaction> onlineTransactions = new HashSet<>();

    @OneToMany(mappedBy = "employee",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ShopTransaction> shopTransactions = new HashSet<>();

    @OneToMany(mappedBy = "employee",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Employment> employments = new HashSet<>();

    protected Person()
    {

    }

    public Person(String name, String surname, LocalDate birthDate, Contact contactInfo, Address addressInfo)
    {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.contactInfo = contactInfo;
        this.addressInfo = addressInfo;
        this.roles.add(PersonRole.EMPLOYEE);
    }

    public Person(String name, String surname, LocalDate birthDate, Contact contactInfo, Address addressInfo, LocalDate registrationDate)
    {
        this(name, surname, birthDate, contactInfo, addressInfo);
        this.registrationDate = registrationDate;
        this.roles.add(PersonRole.PERSON);
        this.roles.add(PersonRole.CLIENT);
    }

    public static Person hiredEmployee(String name, String surname, LocalDate birthDate, Contact contactInfo, Address addressInfo, LocalDate registrationDate)
    {
        Person p = new Person(name, surname, birthDate, contactInfo, addressInfo, registrationDate);
        p.roles.add(PersonRole.EMPLOYEE);
        return p;
    }

    public long getId()
    {
        return id;
    }

    public int getAge()
    {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    public Contact getContactInfo()
    {
        return contactInfo;
    }

    public Address getAddressInfo()
    {
        return addressInfo;
    }

    public Set<PersonRole> getRoles()
    {
        return roles;
    }

    public LocalDate getRegistrationDate() throws Exception
    {
        if(!this.roles.contains(PersonRole.CLIENT))
        {
            throw new Exception("Person is not a client!");
        }
        return registrationDate;
    }

    /*
    public Map<Long, OnlineTransaction> getOnlineTransactions()
    {
        return onlineTransactions;
    }
     */

    public Set<OnlineTransaction> getOnlineTransactions()
    {
        return onlineTransactions;
    }

    public Set<ShopTransaction> getShopTransactions()
    {
        return shopTransactions;
    }

    public Set<Employment> getEmployments()
    {
        return employments;
    }

    public void setContactInfo(Contact contactInfo)
    {
        this.contactInfo = contactInfo;
    }

    public void setAddressInfo(Address addressInfo)
    {
        this.addressInfo = addressInfo;
    }

    protected void setName(String name)
    {
        this.name = name;
    }

    protected void setSurname(String surname)
    {
        this.surname = surname;
    }

    protected void setBirthDate(LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }

    protected void setRoles(Set<PersonRole> roles)
    {
        this.roles = roles;
    }

    protected void setRegistrationDate(LocalDate registrationDate) throws Exception
    {
        if(!this.roles.contains(PersonRole.CLIENT))
        {
            throw new Exception("Person is not a client!");
        }
        this.registrationDate = registrationDate;
    }

    /*
    public void addOnlineTransaction(OnlineTransaction onlineTransaction) throws Exception
    {

        if(!roles.contains(PersonRole.CLIENT))
        {
            throw new Exception("Person is not a Client!");
        }
        Long key = Long.valueOf(onlineTransaction.getId());
        if(!onlineTransactions.containsKey(key))
        {
            this.onlineTransactions.put(key, onlineTransaction);
            onlineTransaction.setClient(this);
        }
    }
    */

    public void addOnlineTransaction(OnlineTransaction onlineTransaction) throws Exception
    {

        if(!roles.contains(PersonRole.CLIENT))
        {
            throw new Exception("Person is not a Client!");
        }
        if(!onlineTransactions.contains(onlineTransaction))
        {
            this.onlineTransactions.add(onlineTransaction);
            onlineTransaction.setClient(this);
        }
    }

    public void addShopTransaction(ShopTransaction shopTransaction) throws Exception
    {
        if(!roles.contains(PersonRole.EMPLOYEE))
        {
            throw new Exception("Person is not an Employee!");
        }
        if(!shopTransactions.contains(shopTransaction))
        {
            this.shopTransactions.add(shopTransaction);
            shopTransaction.setEmployee(this);
        }
    }

    public void addEmployment(Employment employment) throws Exception
    {
        if(!roles.contains(PersonRole.EMPLOYEE))
        {
            throw new Exception("Person is not an Employee!");
        }
        if(!employments.contains(employment))
        {
            for(Employment e : employments)
            {
                if(e.getDateTo() == null)
                {
                    e.setDateTo(employment.getDateFrom().minusDays(1));
                    break;
                }
            }
            this.employments.add(employment);
            employment.setEmployee(this);
        }
    }

    /*
    public void removeOnlineTransaction(OnlineTransaction onlineTransaction) throws Exception
    {
        Long key = Long.valueOf(onlineTransaction.getId());
        if(onlineTransactions.containsKey(key))
        {
            onlineTransactions.remove(key);
            onlineTransaction.setClient(null);
        }
    }
     */

    public void removeOnlineTransaction(OnlineTransaction onlineTransaction) throws Exception
    {
        if(onlineTransactions.contains(onlineTransaction))
        {
            onlineTransactions.remove(onlineTransaction);
            onlineTransaction.setClient(null);
        }
    }

    public void removeShopTransaction(ShopTransaction shopTransaction) throws Exception
    {
        if(shopTransactions.contains(shopTransaction))
        {
            shopTransactions.remove(shopTransaction);
            shopTransaction.setEmployee(null);
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


}
