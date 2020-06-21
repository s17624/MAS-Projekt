package edu.pja.mas.s17624.project.gui.controller;

import edu.pja.mas.s17624.project.gui.view.CheckoutPanelView;
import edu.pja.mas.s17624.project.model.Model;
import edu.pja.mas.s17624.project.model.OnlineTransaction;
import edu.pja.mas.s17624.project.model.ProductTransaction;
import edu.pja.mas.s17624.project.model.Transaction;
import edu.pja.mas.s17624.project.repo.OnlineTransactionRepo;
import edu.pja.mas.s17624.project.repo.ProductTransactionRepo;
import edu.pja.mas.s17624.project.util.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

@Component
public class CheckoutPanelController
{
    private CheckoutPanelView view;

    private Address address;

    @Autowired
    MainPanelController mainPanelController;

    @Autowired
    MainWindowController mainWindowController;

    @Autowired
    OnlineTransactionRepo onlineTransactionRepo;

    @Autowired
    ProductTransactionRepo productTransactionRepo;

    public CheckoutPanelController()
    {
        this.view = new CheckoutPanelView();
        initListeners();
        initTableModel();

    }

    public void showGUI(MainWindowController mainWindowController)
    {
        mainWindowController.showView(view.getCheckOutPanel());
        updateOrderStatus();
    }

    private void initListeners()
    {
        view.getPayButton().addActionListener(e ->
        {
            if (address != null)
            {
                pay();
            }
        });
        view.getSetAddressButton().addActionListener(e ->
        {
            if (!((view.getStreetField() == null || view.getStreetField().equals("")) &&
                    (view.getPostalField() == null || view.getPostalField().equals("")) &&
                    (view.getCityField() == null || view.getCityField().equals("")) &&
                    (view.getStreetNoField() == null || view.getStreetNoField().equals(""))))
            {
                setAddress();
            }
        });
        view.getCancelButton().addActionListener(e ->
        {
            SwingUtilities.invokeLater(() -> mainPanelController.showGUI(mainWindowController));
        });
    }

    private void initTableModel()
    {
        //Create model with uneditable cells
        DefaultTableModel tableModel = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        view.getDetailsTable().setModel(tableModel);
        tableModel.addColumn("Type");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Name");
        tableModel.addColumn("Size");
        tableModel.addColumn("Ammount");
        tableModel.addColumn("Current Price");
        tableModel.addColumn("Row Total");

    }

    private void updateOrderStatus()
    {
        DefaultTableModel tableModel = (DefaultTableModel) view.getDetailsTable().getModel();
        view.getTotalLabel().setText(String.format("Order Total: %.2f", mainWindowController.getTransaction().summarizeTransaction()));

        //Removing records first
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
        {
            tableModel.removeRow(i);
        }

        for (ProductTransaction p : mainWindowController.getTransaction().getProducts())
        {
            Model m = p.getProduct().getModel();
            tableModel.addRow(new Object[]
                    {
                            m.getType(),
                            m.getBrand(),
                            m.getName(),
                            p.getProduct().getProductSize().getSizeField1(),
                            p.getAmmount(),
                            String.format("%.2f", p.getTransactionPrice()),
                            String.format("%.2f", p.getTransactionPrice() * p.getAmmount())
                    });
        }

    }

    private void setAddress()
    {
        String city = view.getCityField().getText();
        String street = view.getStreetField().getText();
        String postal = view.getPostalField().getText();
        String streetNo = view.getStreetNoField().getText();
        String flatNo = view.getFlatNoField().getText();

        if(flatNo.equals("") || flatNo == null)
        {
            flatNo = "-";
        }
        address = new Address(city, street, postal, streetNo, flatNo);
        try
        {
            mainWindowController.getTransaction().setDeliveryAddress(address);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        view.getAddressLabel().setText(String.format("Street: %s, City: %s, Postal: %s, StreetNo: %s, FlatNo: %s",
                city, street, postal, streetNo, flatNo));
    }

    private void pay()
    {
        try
        {
            mainWindowController.getTransaction().setStatus(OnlineTransaction.TransactionStatus.PAID);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        onlineTransactionRepo.save(mainWindowController.getTransaction());


        for(ProductTransaction p : mainWindowController.getTransaction().getProducts())
        {
            productTransactionRepo.save(p);
        }

        System.exit(0);
    }
}
