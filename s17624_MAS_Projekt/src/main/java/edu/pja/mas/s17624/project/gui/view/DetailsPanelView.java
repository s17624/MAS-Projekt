package edu.pja.mas.s17624.project.gui.view;

import edu.pja.mas.s17624.project.gui.util.ProductSizeRenderer;

import javax.swing.*;

public class DetailsPanelView
{
    private JPanel detailPanel;
    private JButton addButon;
    private JButton goBackButton;
    private JComboBox sizeBox;
    private JTextArea descArea;
    private JSpinner ammountSpinner;
    private JLabel photoLabel;
    private JPanel panelPhoto;
    private JList attributesList;


    public JPanel getDetailPanel()
    {
        return detailPanel;
    }

    public JTextArea getDescArea()
    {
        return descArea;
    }

    private void createUIComponents()
    {
        ammountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        sizeBox = new JComboBox();
        sizeBox.setRenderer(new ProductSizeRenderer());
    }


    public JComboBox getSizeBox()
    {
        return sizeBox;
    }

    public JButton getAddButon()
    {
        return addButon;
    }

    public JButton getGoBackButton()
    {
        return goBackButton;
    }

    public JLabel getPhotoLabel()
    {
        return photoLabel;
    }

    public JSpinner getAmmountSpinner()
    {
        return ammountSpinner;
    }

    public JPanel getPanelPhoto()
    {
        return panelPhoto;
    }

    public JList getAttributesList()
    {
        return attributesList;
    }
}
