package edu.pja.mas.s17624.project;

import edu.pja.mas.s17624.project.model.*;
import edu.pja.mas.s17624.project.repo.*;
import edu.pja.mas.s17624.project.util.Address;
import edu.pja.mas.s17624.project.util.Contact;
import edu.pja.mas.s17624.project.util.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer
{
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CompositionRepo compositionRepo;

    @Autowired
    private EmploymentRepo employmentRepo;

    @Autowired
    private ModelPromotionRepo modelPromotionRepo;

    @Autowired
    private ModelRepo modelRepo;

    @Autowired
    private OnlineTransactionRepo onlineTransactionRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductTransactionRepo productTransactionRepo;

    @Autowired
    private PromotionRepo promotionRepo;

    @Autowired
    private ShopProductRepo shopProductRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ShopTransactionRepo shopTransactionRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    public void initData()
    {
        try
        {


            System.out.println("PERSONS");
            Person e1 = new Person("Json", "Zbigniew", LocalDate.of(1990, 01, 01),
                    new Contact("json@zbigniew.pl", "123456789"),
                    new Address("Warsaw", "Warsowian", "00-999", "1", null));
            Person c1 = new Person("Test", "Client", LocalDate.of(2000, 01, 01),
                    new Contact("test@gmail.com", "987654321"),
                    new Address("Testcity", "Teststreet", "99-999", "99", "9"),
                    LocalDate.of(2020, 1, 1));

            personRepo.save(c1);
            personRepo.save(e1);

            System.out.println("SHOPS");
            Shop s1 = new Shop(new Address("Virtual", "Streety", "00-000", "1", "3"));

            shopRepo.save(s1);

            System.out.println("EMPLOYMENTS");
            Employment ep1 = new Employment(s1, e1, LocalDate.of(2019, 1, 1),
                    null, 20d, Employment.EmployeePosition.EMLPOYEE);

            employmentRepo.save(ep1);

            System.out.println("CATEGORES");
            Category cat1 = new Category("Urban Skate");
            Category cat2 = new Category("SSM Frames");
            Category cat3 = new Category("Freestyle Wheels");
            Category cat4 = new Category("Urban Skating");

            cat4.addSubCategory(cat1);
            cat4.addSubCategory(cat2);
            cat4.addSubCategory(cat3);

            System.out.println("MODELS");
            Model m1 = new Model(cat1,"Red Imperial", "Powerslide", 999d, "Skate", true);
            Model m2 = new Model(cat2,"Spider", "Powerslide", 400d, "Frame", true);
            Model m3 = new Model(cat3,"Red Hurricane", "Powerslide", 20d, "Wheel", true);

            m1.addAttribute("Color", "Red");
            m1.addAttribute("Shell Material", "Composite");
            m1.setPhotoLink("https://static.skatepro.com/product/1500/powerslide-imperial-pro-80-red-freeskates-x1.jpg");
            m1.setDescription("Lorem Ipsum BEST SKATE!");

            categoryRepo.save(cat1);
            categoryRepo.save(cat2);
            categoryRepo.save(cat3);
            categoryRepo.save(cat4);

            modelRepo.save(m1);
            modelRepo.save(m2);
            modelRepo.save(m3);

            System.out.println("PRODUCTS");
            Product p1 = new Product(m1, new Size("40EU", "247mm", "7US"));
            Product p2 = new Product(m1, new Size("43EU", "275mm", "9US"));
            Product p3 = new Product(m1, new Size("45EU", "289mm", "11US"));
            Product p4 = new Product(m2, new Size("243mm"));
            Product p5 = new Product(m3, new Size("80mm"));

            productRepo.save(p1);
            productRepo.save(p2);
            productRepo.save(p3);
            productRepo.save(p4);
            productRepo.save(p5);

            System.out.println("PROMOTIONS");
            Promotion pr1 = new Promotion("Test Promotion", "Lorem Ipsum",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 12, 31));

            promotionRepo.save(pr1);

            ModelPromotion mp1 = new ModelPromotion(m1, pr1, 10d);

            System.out.println("MODEL PROMOTIONS");
            modelPromotionRepo.save(mp1);

            System.out.println("COMPOSITION");
            Composition comp1 = new Composition(p1, p2, 1,"Frames");
            Composition comp2 = new Composition(p1, p3, 8, "Wheels");

            compositionRepo.save(comp1);
            compositionRepo.save(comp2);

            System.out.println("SHOP TRANSACTIONS");
            ShopTransaction st1 = new ShopTransaction(e1, s1, LocalDateTime.of(2020, 2, 1, 0, 0));

            shopTransactionRepo.save(st1);

            System.out.println("PRODUCT TRANSACTIONS");
            ProductTransaction pt1 = new ProductTransaction(p1, st1, 1);

            productTransactionRepo.save(pt1);

            System.out.println("SHOP PRODUCTS");
            ShopProduct sp1 = new ShopProduct(p1, s1, 10);
            ShopProduct sp2 = new ShopProduct(p2, s1, 10);
            ShopProduct sp3 = new ShopProduct(p3, s1, 10);
            ShopProduct sp4 = new ShopProduct(p4, s1, 5);
            ShopProduct sp5 = new ShopProduct(p5, s1, 80);

            shopProductRepo.save(sp1);
            shopProductRepo.save(sp2);
            shopProductRepo.save(sp3);
            shopProductRepo.save(sp4);
            shopProductRepo.save(sp5);

            System.out.println("FINISHED");

        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
