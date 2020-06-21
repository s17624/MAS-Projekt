package edu.pja.mas.s17624.project.gui.controller;

import ch.qos.logback.core.read.ListAppender;
import edu.pja.mas.s17624.project.gui.view.DetailsPanelView;
import edu.pja.mas.s17624.project.model.*;
import edu.pja.mas.s17624.project.repo.OnlineTransactionRepo;
import edu.pja.mas.s17624.project.repo.ProductRepo;
import edu.pja.mas.s17624.project.repo.ProductTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Component
public class DetailsPanelController
{
    private Model model;

    private DetailsPanelView view;

    @Autowired
    private OnlineTransactionRepo onlineTransactionRepo;

    @Autowired
    private MainWindowController mainWindowController;

    @Autowired
    private MainPanelController mainPanelController;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductTransactionRepo productTransactionRepo;

    public DetailsPanelController()
    {
        this.view = new DetailsPanelView();
        initListModel();
        initListeners();
    }

    public void showGUI(MainWindowController mainWindowController, Model model)
    {
        mainWindowController.showView(view.getDetailPanel());
        this.model = model;
        displayModelInfo();
        initProducts();
        initPhoto();
        initAttributes();
    }

    private void initListeners()
    {
        view.getGoBackButton().addActionListener(e ->
        {
            SwingUtilities.invokeLater(() -> mainPanelController.showGUI(mainWindowController));
        });

        view.getAddButon().addActionListener(e ->
        {
            if(view.getSizeBox().getSelectedItem() != null)
            {
                addToOrder((Product)view.getSizeBox().getSelectedItem(),
                        (int)view.getAmmountSpinner().getValue());
                SwingUtilities.invokeLater(() -> mainPanelController.showGUI(mainWindowController));
            }
        });
    }

    private void displayModelInfo()
    {
        view.getDescArea().setText(model.getDescription());
    }

    private void initListModel()
    {
        view.getAttributesList().setModel(new DefaultListModel());
    }

    private void initAttributes()
    {
        DefaultListModel<String> listAttribute = (DefaultListModel<String>) view.getAttributesList().getModel();
        listAttribute.removeAllElements();

        //Mandatory attributes listing
        listAttribute.addElement(String.format("Type: %s", model.getType()));
        listAttribute.addElement(String.format("Name: %s", model.getName()));
        listAttribute.addElement(String.format("Brand: %s", model.getBrand()));
        listAttribute.addElement(String.format("Price: %.2f", model.getBasePrice()));
        if(model.getBasePrice() != model.getCurrentPrice())
        {
            listAttribute.addElement(String.format("Promotion Price: %.2f", model.getCurrentPrice()));
        }

        //Bonus, not specified attributes listing
        if(model.getYear() != null)
        {
            listAttribute.addElement(String.format("Year: %s", model.getYear().toString()));
        }
        for(Map.Entry<String, String> atr: model.getAttributes().entrySet())
        {
            listAttribute.addElement(String.format("%s: %s", atr.getKey(), atr.getValue()));
        }
    }

    private void initProducts()
    {
        view.getSizeBox().removeAllItems();
        view.getAmmountSpinner().setValue(1);

        List<Product> products = productRepo.findByModel(model);

        //Sizes ascending
        Collections.sort(products, new Comparator<Product>()
        {
            @Override
            public int compare(Product o1, Product o2)
            {
                return o1.getProductSize().getSizeField1().compareTo(o2.getProductSize().getSizeField1());
            }
        });

        for(Product p : products)
        {
            if(p.checkAvailability())
            {
                view.getSizeBox().addItem(p);
            }
        }
    }

    private void initPhoto()
    {
        view.getPhotoLabel().setText("");
        view.getPhotoLabel().setIcon(null);

        view.getPhotoLabel().setHorizontalAlignment(SwingConstants.CENTER);
        view.getPhotoLabel().setVerticalAlignment(SwingConstants.CENTER);


        if(model.getPhotoLink() == null || model.getPhotoLink().equals(""))
        {
            view.getPhotoLabel().setText("No Photo Available");
        }else
        {
            try
            {
                URL url = new URL(model.getPhotoLink());
                Dimension d = view.getPanelPhoto().getSize();

                Image img = ImageIO.read(url).getScaledInstance((int)(d.getWidth()) - 4,
                        (int)(d.getHeight()) -4,
                        Image.SCALE_DEFAULT);
                view.getPhotoLabel().setIcon(new ImageIcon(img));
            }catch (Exception e)
            {
                e.printStackTrace();
                view.getPhotoLabel().setText("No Photo Available");
            }
        }

    }

    private void addToOrder(Product product, int ammount)
    {
        //If new transcation wasn't created yet, creating new one
        //Otherwise, using existing one
        OnlineTransaction transaction;
        if(mainWindowController.getTransaction() == null)
        {
            transaction = new OnlineTransaction(LocalDate.now());
            try
            {
                transaction.setClient(mainWindowController.getClient());

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            mainWindowController.setTransaction(transaction);
        }else
        {
            transaction = mainWindowController.getTransaction();
        }

        //If product is already in transaction - adding ammount
        //Otherwise inserting new records
        ProductTransaction p = transaction.getProduct(product);
        if(p != null)
        {
            p.setAmmount(p.getAmmount() + ammount);
        }else
        {
            p = new ProductTransaction(product, transaction, ammount);
        }
    }

}
