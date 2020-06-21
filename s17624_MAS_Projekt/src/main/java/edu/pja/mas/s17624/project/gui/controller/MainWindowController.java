package edu.pja.mas.s17624.project.gui.controller;

import edu.pja.mas.s17624.project.gui.view.MainWindow;
import edu.pja.mas.s17624.project.model.OnlineTransaction;
import edu.pja.mas.s17624.project.model.Person;
import edu.pja.mas.s17624.project.model.ProductTransaction;
import edu.pja.mas.s17624.project.model.Transaction;
import edu.pja.mas.s17624.project.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainWindowController
{
    private MainWindow view;

    private Person client;

    private OnlineTransaction transaction;

    private List<ProductTransaction> products = new ArrayList<>();

    @Autowired
    PersonRepo personRepo;

    @Autowired
    private MainPanelController mainPanelController;

    public MainWindowController()
    {
        view = new MainWindow();
    }

    public void showGUI()
    {
        //Assuming that we are already logged in, gettting Test client
        //To create order later on
        client = personRepo.findById(638l).get();
        view.setVisible(true);
        view.setTitle(String.format("LOGGED IN AS: %s %s", client.getName(), client.getSurname()));
        SwingUtilities.invokeLater(() -> mainPanelController.showGUI(this));
    }


    public void showView(JPanel panel)
    {
        view.getContentPane().removeAll();
        view.getContentPane().add(panel);
        view.revalidate();
        view.repaint();
    }

    public OnlineTransaction getTransaction()
    {
        return transaction;
    }

    public void setTransaction(OnlineTransaction transaction)
    {
        this.transaction = transaction;
    }

    public Person getClient()
    {
        return client;
    }


}
