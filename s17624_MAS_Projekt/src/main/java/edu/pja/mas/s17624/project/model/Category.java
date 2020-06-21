package edu.pja.mas.s17624.project.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Mas_Category")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String categoryName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @Nullable
    @JoinColumn(name = "parent_Category_Id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
    fetch = FetchType.EAGER)
    private Set<Category> subCategories = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Mas_Model_Category",
            joinColumns = @JoinColumn(name = "category_Id"),
            inverseJoinColumns = @JoinColumn(name = "model_Id"))
    private Set<Model> models = new HashSet<>();

    protected Category()
    {

    }

    public Category(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Category getParentCategory()
    {
        return parentCategory;
    }

    public void setParentCategory(Category category)
    {
        if(parentCategory != null)
        {
            if(category != null)
            {
                parentCategory.removeSubCategory(this);
            }
        }
        category.addSubCategory(this);
        parentCategory = category;
    }

    public Set<Category> getSubCategories()
    {
        return subCategories;
    }

    public void addSubCategory(Category category)
    {
        if(!subCategories.contains(category))
        {
            subCategories.add(category);
            category.setParentCategory(this);
        }
    }

    public void removeSubCategory(Category category)
    {
        if(subCategories.contains(category))
        {
            category.setParentCategory(null);
            subCategories.remove(category);
        }
    }

    public void addModel(Model model)
    {
        if(!models.contains(model))
        {
            models.add(model);
            model.addCategory(this);
        }
    }

    public void removeModel(Model model)
    {
        if(models.contains(model))
        {
            models.remove(model);
            model.removeCategory(this);
        }
    }

    public Set<Category> getAllSubcategories()
    {
        //Getting this category and first level subcategories
        HashSet<Category> categories = new HashSet<>();
        categories.add(this);
        for(Category c : subCategories)
        {
            if(!categories.contains(c))
            {
                categories.add(c);
            }
        }
        return categories;
    }

    @Override
    public String toString()
    {
        return categoryName;
    }
}
