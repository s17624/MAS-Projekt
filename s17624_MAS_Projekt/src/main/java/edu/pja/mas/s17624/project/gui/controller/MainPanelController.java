package edu.pja.mas.s17624.project.gui.controller;

import edu.pja.mas.s17624.project.gui.view.CheckoutPanelView;
import edu.pja.mas.s17624.project.gui.view.MainPanelView;
import edu.pja.mas.s17624.project.model.*;
import edu.pja.mas.s17624.project.repo.CategoryRepo;
import edu.pja.mas.s17624.project.repo.ModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class MainPanelController
{

    private MainPanelView view;

    @Autowired
    private ModelRepo modelRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private MainWindowController mainWindowController;

    @Autowired
    private DetailsPanelController detailsPanelController;

    @Autowired
    private CheckoutPanelController checkoutPanelController;

    public MainPanelController()
    {
        this.view = new MainPanelView();
        initListModels();
        initListeners();
        initSearch();
    }

    public void showGUI(MainWindowController mainWindowController)
    {
        mainWindowController.showView(view.getMainPanel());
        initCategories();
        reset();
        updateOrderStatus();
    }

    private void initListeners()
    {
        view.getSearchButton().addActionListener(e ->
        {
            Category cat = null;
            if(view.getCategoryField().getSelectedItem() != "")
            {
                cat = (Category) view.getCategoryField().getSelectedItem();
            }

            updateModelsList(view.getNameField().getText(), cat,
                    view.getSortField().getSelectedItem().toString(),
                    view.getAscendingCheckBox().isSelected());
        });
        view.getResetButton().addActionListener(e ->
        {
            reset();
        });
        view.getSelectButton().addActionListener(e ->
        {
            if(!view.getListModels().isSelectionEmpty())
            {
                SwingUtilities.invokeLater(() -> detailsPanelController.showGUI(mainWindowController,
                        (Model) view.getListModels().getSelectedValue()));
            }
        });
        view.getRemoveButton().addActionListener(e ->
        {
            if(!view.getListOrder().isSelectionEmpty())
            {
                mainWindowController.getTransaction().removeProduct((ProductTransaction) view.getListOrder().getSelectedValue());
                updateOrderStatus();
            }
        });
        view.getCheckoutButton().addActionListener(e ->
        {
            if(mainWindowController.getTransaction().getProducts().size() > 0)
            {
                SwingUtilities.invokeLater(() -> checkoutPanelController.showGUI(mainWindowController));
            }
        });
    }

    private void initListModels()
    {
        view.getListModels().setModel(new DefaultListModel<Model>());
        view.getListOrder().setModel(new DefaultListModel<ProductTransaction>());

    }

    public void initSearch()
    {
        //default fields to search
        String[] fields = {"", "Price", "Name", "Brand"};
        for(String s : fields)
        {
            view.getSortField().addItem(s);
        }
    }
    private void initCategories()
    {
        List<Category> categories = categoryRepo.findAll();
        view.getCategoryField().removeAllItems();
        view.getCategoryField().insertItemAt("", 0);
        for(Category c : categories)
        {
            view.getCategoryField().addItem(c);
        }
    }

    private void reset()
    {
        //reset to default view
        view.getCategoryField().setSelectedItem("");
        view.getNameField().setText("");
        view.getAscendingCheckBox().setSelected(true);
        view.getSortField().setSelectedItem("");
        updateModelsList(null, null, null, true);
    }

    private void updateOrderStatus()
    {
        //update status - products assigned and models
        DefaultListModel<ProductTransaction> products = (DefaultListModel<ProductTransaction>) view.getListOrder().getModel();
        OnlineTransaction t = mainWindowController.getTransaction();
        products.removeAllElements();
        if(t != null)
        {
            for(ProductTransaction p : t.getProducts())
            {
                products.addElement(p);
            }
            view.getOrderLabel().setText(String.format("Order Total: %.2f", t.summarizeTransaction()));
        }
    }


    private void updateModelsList(String name, Category category, String compareField, boolean ascending)
    {
        List<Model> models;
        boolean byName = !((name == null) || name.equals(""));
        boolean byCategory = category != null;

        Set<Category> categories = null;

        if(byCategory)
        {
            categories = category.getAllSubcategories();
        }

        //Finding models
        if(byName && byCategory)
        {
            models = modelRepo.findByNameContainsIgnoreCaseAndCategoriesIn(name, categories);
        }else if(byName)
        {
            models = modelRepo.findByNameContainsIgnoreCase(name);
        }else if(byCategory)
        {
            models = modelRepo.findByCategoriesIn(categories);
        }else
        {
            models = modelRepo.findAll();
        }

        //Sorting models
        if(!(compareField == null || compareField.equals("")))
        {
            Collections.sort(models, new Comparator<Model>()
            {

                @Override
                public int compare(Model o1, Model o2)
                {
                    int orderer = ascending ? 1 : -1;

                    if(compareField == "Price")
                    {
                        return Double.compare(o1.getBasePrice(), o2.getBasePrice()) * orderer;
                    }
                    if(compareField == "Name")
                    {
                        return o1.getName().compareTo(o2.getName()) * orderer;

                    }
                    if(compareField == "Brand")
                    {
                        return o1.getBrand().compareTo(o2.getBrand()) * orderer;
                    }
                    return 0;
                };
            });
        }

        DefaultListModel<Model> listModel = (DefaultListModel<Model>) view.getListModels().getModel();
        listModel.removeAllElements();
        for(Model m : models)
        {
            if(m.isInSales() && m.checkModelAvailability())
            {
                listModel.addElement(m);
            }
        }
    }




}
