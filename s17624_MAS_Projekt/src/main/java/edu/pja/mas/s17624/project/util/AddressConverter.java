package edu.pja.mas.s17624.project.util;

import javax.persistence.AttributeConverter;

public class AddressConverter implements AttributeConverter<Address, String>
{
    private final static String sep = "; ";

    @Override
    public String convertToDatabaseColumn(Address address)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(address.getCity()).append(sep).append(address.getStreet())
                .append(sep).append(address.getPostalCode())
                .append(sep).append(address.getStreetNo())
                .append(sep).append(address.getFlatNo());

        return sb.toString();
    }

    @Override
    public Address convertToEntityAttribute(String s)
    {
        String[] address = s.split(sep);
        return new Address(address[0], address[1], address[2], address[3], address[4]);
    }
}
