package edu.pja.mas.s17624.project.gui.util;

import edu.pja.mas.s17624.project.model.Model;
import edu.pja.mas.s17624.project.model.Product;
import edu.pja.mas.s17624.project.model.ProductTransaction;
import edu.pja.mas.s17624.project.util.Size;

import javax.swing.*;
import java.awt.*;

public class ProductListCellRenderer extends JLabel implements ListCellRenderer<ProductTransaction>
{

    public ProductListCellRenderer()
    {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ProductTransaction> list, ProductTransaction value, int index, boolean isSelected, boolean cellHasFocus)
    {
        Model m = value.getProduct().getModel();
        String size  = value.getProduct().getProductSize().getSizeField1();

        setText(String.format("%dx: %s, %s, %s, (%s): %.2f",
                value.getAmmount(),
                m.getType(),
                m.getBrand(),
                m.getName(),
                size,
                m.getCurrentPrice()));

        if(isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
