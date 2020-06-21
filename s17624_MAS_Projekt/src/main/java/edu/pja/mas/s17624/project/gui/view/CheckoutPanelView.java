package edu.pja.mas.s17624.project.gui.view;

import javax.swing.*;

public class CheckoutPanelView
{
    private JPanel checkOutPanel;
    private JTable detailsTable;
    private JButton cancelButton;
    private JButton setAddressButton;
    private JButton payButton;
    private JTextField cityField;
    private JTextField postalField;
    private JTextField streetField;
    private JTextField streetNoField;
    private JTextField flatNoField;
    private JLabel totalLabel;
    private JLabel addressLabel;

    public JPanel getCheckOutPanel()
    {
        return checkOutPanel;
    }

    public JTable getDetailsTable()
    {
        return detailsTable;
    }

    public JButton getCancelButton()
    {
        return cancelButton;
    }

    public JButton getPayButton()
    {
        return payButton;
    }

    public JLabel getTotalLabel()
    {
        return totalLabel;
    }

    public JButton getSetAddressButton()
    {
        return setAddressButton;
    }

    public JTextField getCityField()
    {
        return cityField;
    }

    public JTextField getFlatNoField()
    {
        return flatNoField;
    }

    public JTextField getPostalField()
    {
        return postalField;
    }

    public JTextField getStreetField()
    {
        return streetField;
    }

    public JTextField getStreetNoField()
    {
        return streetNoField;
    }

    public JLabel getAddressLabel()
    {
        return addressLabel;
    }
}
