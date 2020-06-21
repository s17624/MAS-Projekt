package edu.pja.mas.s17624.project.util;

import edu.pja.mas.s17624.project.model.Product;

import java.io.Serializable;
import java.util.Objects;

public class Size implements Serializable
{
    private String sizeField1;
    private String sizeField2;
    private String sizeField3;

    public Size(String sizeField1)
    {
        this(sizeField1, null, null);
    }

    public Size(String sizeField1, String sizeField2, String sizeField3)
    {
        setSizeField1(sizeField1);
        setSizeField2(sizeField2);
        setSizeField3(sizeField3);
    }

    public String getSizeField1()
    {
        return sizeField1;
    }

    public void setSizeField1(String sizeField1)
    {
        if(sizeField1 == null || sizeField1.equals(""))
        {
            throw new IllegalArgumentException("Size field 1 is mandatory!");
        }
        this.sizeField1 = sizeField1;
    }

    public String getSizeField2()
    {
        return sizeField2;
    }

    public void setSizeField2(String sizeField2)
    {
        if(sizeField2 == null)
        {
            sizeField2 = "-";
        }
        this.sizeField2 = sizeField2;
    }

    public String getSizeField3()
    {
        return sizeField3;
    }

    public void setSizeField3(String sizeField3)
    {
        if(sizeField3 == null)
        {
            sizeField3 = "-";
        }
        this.sizeField3 = sizeField3;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(!(obj instanceof Size))
        {
            return false;
        }
        Size s = (Size) obj;
        return (Objects.equals(this.getSizeField1(), s.getSizeField1()) &&
                Objects.equals(this.getSizeField2(), s.getSizeField2()) &&
                Objects.equals(this.getSizeField3(), s.getSizeField3()));
    }
}
