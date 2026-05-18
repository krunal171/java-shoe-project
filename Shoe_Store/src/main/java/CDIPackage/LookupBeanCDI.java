package CDIPackage;

import SessionPackage.ShoeSessionBeanLocal;
import com.mycompany.shoe_store.Brand;
import com.mycompany.shoe_store.Categories;
import com.mycompany.shoe_store.Color;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 * CDI Managed Bean specifically for managing lookup tables (Brands, Categories, Colors).
 */
@Named(value = "lookupBeanCDI")
@SessionScoped
public class LookupBeanCDI implements Serializable {

    @EJB
    private ShoeSessionBeanLocal sbean;

    private String newBrandName;
    private String newBrandCountry;
    private String newCategoryName;
    private String newCategoryDescription;
    private String newColorName;

    public LookupBeanCDI() {
    }

    // Getters and Setters for input fields
    public String getNewBrandName() { return newBrandName; }
    public void setNewBrandName(String newBrandName) { this.newBrandName = newBrandName; }

    public String getNewBrandCountry() { return newBrandCountry; }
    public void setNewBrandCountry(String newBrandCountry) { this.newBrandCountry = newBrandCountry; }

    public String getNewCategoryName() { return newCategoryName; }
    public void setNewCategoryName(String newCategoryName) { this.newCategoryName = newCategoryName; }

    public String getNewCategoryDescription() { return newCategoryDescription; }
    public void setNewCategoryDescription(String newCategoryDescription) { this.newCategoryDescription = newCategoryDescription; }

    public String getNewColorName() { return newColorName; }
    public void setNewColorName(String newColorName) { this.newColorName = newColorName; }


    // --- BRAND OPERATIONS ---
    public List<Brand> getAllBrands() {
        return sbean.getAllBrands();
    }

    public String addBrand() {
        if (newBrandName != null && !newBrandName.trim().isEmpty()) {
            sbean.addBrand(newBrandName, newBrandCountry);
            newBrandName = "";
            newBrandCountry = "";
        }
        return "CategoryManagement.xhtml?faces-redirect=true";
    }

    public String deleteBrand(Integer id) {
        try {
            sbean.deleteBrand(id);
        } catch (Exception e) {
            // Fails silently if constraint violation (linked to an existing shoe)
        }
        return "CategoryManagement.xhtml?faces-redirect=true";
    }

    // --- CATEGORY OPERATIONS ---
    public List<Categories> getAllCategories() {
        return sbean.getAllCategories();
    }

    public String addCategory() {
        if (newCategoryName != null && !newCategoryName.trim().isEmpty()) {
            sbean.addCategory(newCategoryName, newCategoryDescription);
            newCategoryName = "";
            newCategoryDescription = "";
        }
        return "CategoryManagement.xhtml?faces-redirect=true";
    }

    public String deleteCategory(Integer id) {
        try {
            sbean.deleteCategory(id);
        } catch (Exception e) {
            // Concept constraint failsafe
        }
        return "CategoryManagement.xhtml?faces-redirect=true";
    }

    // --- COLOR OPERATIONS ---
    public List<Color> getAllColors() {
        return sbean.getAllColors();
    }

    public String addColor() {
        if (newColorName != null && !newColorName.trim().isEmpty()) {
            sbean.addColor(newColorName);
            newColorName = "";
        }
        return "CategoryManagement.xhtml?faces-redirect=true";
    }

    public String deleteColor(Integer id) {
        try {
            sbean.deleteColor(id);
        } catch (Exception e) {
            // Concept constraint failsafe
        }
        return "CategoryManagement.xhtml?faces-redirect=true";
    }
}
