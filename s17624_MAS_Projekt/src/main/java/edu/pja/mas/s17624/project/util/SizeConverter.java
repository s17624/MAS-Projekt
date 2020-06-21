package edu.pja.mas.s17624.project.util;

import javax.persistence.AttributeConverter;

public class SizeConverter implements AttributeConverter<Size, String>
{
    private final static String sep = "; ";

    @Override
    public String convertToDatabaseColumn(Size size)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(size.getSizeField1()).append(sep)
                .append(size.getSizeField2()).append(sep)
                .append(size.getSizeField3());
        return sb.toString();
    }

    @Override
    public Size convertToEntityAttribute(String s)
    {
        String[] size = s.split(sep);
        return new Size(size[0], size[1], size[2]);
    }
}
