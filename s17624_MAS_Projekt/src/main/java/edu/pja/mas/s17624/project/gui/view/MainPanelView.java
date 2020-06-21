package edu.pja.mas.s17624.project.gui.view;

import edu.pja.mas.s17624.project.gui.util.ProductListCellRenderer;

import javax.swing.*;

public class MainPanelView
{
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JList listModels;
    private JList listOrder;
    private JButton searchButton;
    private JTextField nameField;
    private JComboBox categoryField;
    private JButton resetButton;
    private JButton selectButton;
    private JButton checkoutButton;
    private JButton removeButton;
    private JComboBox sortField;
    private JCheckBox ascendingCheckBox;
    private JLabel orderLabel;


    public JPanel getMainPanel()
    {
        return mainPanel;
    }

    public JList getListModels()
    {
        return listModels;
    }

    public JList getListOrder()
    {
        return listOrder;
    }

    public JTextField getNameField()
    {
        return nameField;
    }

    public JComboBox getCategoryField()
    {
        return categoryField;
    }

    public JButton getSelectButton()
    {
        return selectButton;
    }

    public JButton getCheckoutButton()
    {
        return checkoutButton;
    }

    public JButton getRemoveButton()
    {
        return removeButton;
    }

    public JButton getResetButton()
    {
        return resetButton;
    }

    public JButton getSearchButton()
    {
        return searchButton;
    }

    public JComboBox getSortField()
    {
        return sortField;
    }

    public JCheckBox getAscendingCheckBox()
    {
        return ascendingCheckBox;
    }

    public JLabel getOrderLabel()
    {
        return orderLabel;
    }

    private void createUIComponents()
    {
        listOrder = new JList();
        listOrder.setCellRenderer(new ProductListCellRenderer());
    }
}
