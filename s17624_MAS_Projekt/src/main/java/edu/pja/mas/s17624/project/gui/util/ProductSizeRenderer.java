package edu.pja.mas.s17624.project.gui.util;

import edu.pja.mas.s17624.project.model.Product;
import edu.pja.mas.s17624.project.util.Size;

import javax.swing.*;
import java.awt.*;

public class ProductSizeRenderer extends JLabel implements ListCellRenderer<Product>
{
    @Override
    public Component getListCellRendererComponent(JList<? extends Product> list, Product value, int index, boolean isSelected, boolean cellHasFocus)
    {
        Size size = value.getProductSize();
        String[] sizes = {size.getSizeField1(), size.getSizeField2(), size.getSizeField3()};
        String sizeStr = "";
        boolean start = true;
        for(String s : sizes)
        {
            if(!s.equals("-"))
            {
                if(!start)
                {
                    sizeStr = sizeStr.concat(", ");
                }else
                {
                    start = false;
                }
                sizeStr = sizeStr.concat(s);
            }
        }

        setText(sizeStr);
        return this;
    }
}
