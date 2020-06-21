package edu.pja.mas.s17624.project.util;

import javax.persistence.AttributeConverter;

public class ContactConverter implements AttributeConverter<Contact, String>
{
    private final static String sep = "; ";

    @Override
    public String convertToDatabaseColumn(Contact contact)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(contact.getEmail()).append(sep).append(contact.getPhoneNumber());

        return sb.toString();
    }

    @Override
    public Contact convertToEntityAttribute(String s)
    {
        String[] contact = s.split(sep);

        return new Contact(contact[0], contact[1]);
    }
}
