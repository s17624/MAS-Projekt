package edu.pja.mas.s17624.project.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address
{
    private String city;
    private String street;
    private String postalCode;
    private String streetNo;
    private String flatNo;

    public Address(String city, String street, String postalCode, String streetNo, String flatNo)
    {
        setCity(city);
        setStreet(street);
        setPostalCode(postalCode);
        setStreetNo(streetNo);
        setFlatNo(flatNo);
    }

    public String getCity()
    {
        return city;
    }

    public String getStreet()
    {
        return street;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public String getStreetNo()
    {
        return streetNo;
    }

    public String getFlatNo()
    {
        return flatNo;
    }

    public void setCity(String city)
    {
        if(city == null || city.equals(""))
        {
            throw new IllegalArgumentException("City cannot be null!");
        }
        this.city = city;
    }

    public void setStreet(String street)
    {
        if(street == null || street.equals(""))
        {
            throw new IllegalArgumentException("Street cannot be empty!");
        }
        this.street = street;
    }

    public void setPostalCode(String postalCode)
    {
        Pattern postPattern = Pattern.compile("^\\d{2}-\\d{3}$");
        Matcher postMatch = postPattern.matcher(postalCode);
        if(!postMatch.matches())
        {
            throw new IllegalArgumentException("Invalid postal code!");
        }
        this.postalCode = postalCode;
    }

    public void setStreetNo(String streetNo)
    {
        Pattern noPattern = Pattern.compile("^\\d+\\w?$");
        Matcher noMatch = noPattern.matcher(streetNo);
        if(!noMatch.matches())
        {
            throw new IllegalArgumentException("Invalid street number!");
        }
        this.streetNo = streetNo;
    }

    public void setFlatNo(String flatNo)
    {
        if(!(flatNo == null || flatNo.equals("-")))
        {
            Pattern noPattern = Pattern.compile("^\\d+$");
            Matcher noMatch = noPattern.matcher(flatNo);
            if(!noMatch.matches())
            {
                throw new IllegalArgumentException("Invalid flat number!");
            }
            this.flatNo = flatNo;
        }else
        {
            this.flatNo = "-";
        }

    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(!(obj instanceof Address))
        {
            return false;
        }
        Address a = (Address) obj;
        return (Objects.equals(this.getCity(), a.getCity()) &&
                Objects.equals(this.getStreet(), a.getStreet()) &&
                Objects.equals(this.getPostalCode(), a.getPostalCode()) &&
                Objects.equals(this.getStreetNo(), a.getStreetNo()) &&
                Objects.equals(this.getFlatNo(), a.getFlatNo()));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getCity(), getStreet(), getPostalCode(), getStreetNo(), getFlatNo());
    }
}

