package edu.pja.mas.s17624.project.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact implements Serializable
{
    private String email;
    private String phoneNumber;

    public Contact(String email, String phoneNumber)
    {
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setEmail(String email)
    {
        Pattern mailPattern = Pattern.compile("[^@]+@[^\\.]+\\..+");
        Matcher mailMatch = mailPattern.matcher(email);
        if(!mailMatch.matches())
        {
            throw new IllegalArgumentException("Invalid mail!");
        }
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        if(!(phoneNumber == null || phoneNumber.equals("-")))
        {
            Pattern phonePattern = Pattern.compile("^\\+?\\d{9,}$");
            Matcher phoneMatch = phonePattern.matcher(phoneNumber);
            if(!phoneMatch.matches())
            {
                throw new IllegalArgumentException("Invalid phone number!");
            }
            this.phoneNumber = phoneNumber;
        }else
        {
            this.phoneNumber = "-";
        }
    }
}
