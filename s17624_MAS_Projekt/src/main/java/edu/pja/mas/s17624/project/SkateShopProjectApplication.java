package edu.pja.mas.s17624.project;

import edu.pja.mas.s17624.project.gui.controller.MainWindowController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class SkateShopProjectApplication
{

    public static void main(String[] args)
    {
        //SpringApplication.run(SkateShopProjectApplication.class, args);
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(SkateShopProjectApplication.class).headless(false).run(args);
        //ctx.getBean(DataInitializer.class).initData(); // DATA LOAD, ALREADY IN DB, JUST AN EXAMPLE OF POPULATION

        SwingUtilities.invokeLater(()->ctx.getBean(MainWindowController.class).showGUI());
    }

}
