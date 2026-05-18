package SessionPackage;

import com.mycompany.shoe_store.Shoe;
import jakarta.ejb.Local;
import java.util.List;

/**
 * Local interface for Shoe management.
 */
@Local
public interface ShoeSessionBeanLocal {
    void addShoe(String name, Integer category, Integer brand, Double price, String description, String imageUrl, String colorId, String status);
    List<Shoe> getAllShoes();
    Shoe getShoeById(Integer shoeId);
    void updateShoe(Shoe shoe);
    void deleteShoe(Integer shoeId);
    
    // New methods for dropdowns
    List<com.mycompany.shoe_store.Brand> getAllBrands();
    void addBrand(String name, String country);
    void deleteBrand(Integer id);

    List<com.mycompany.shoe_store.Color> getAllColors();
    void addColor(String name);
    void deleteColor(Integer id);

    List<com.mycompany.shoe_store.Categories> getAllCategories();
    void addCategory(String name, String description);
    void deleteCategory(Integer id);

    List<Shoe> getFilteredShoes(Integer categoryId, Integer brandId);
}
