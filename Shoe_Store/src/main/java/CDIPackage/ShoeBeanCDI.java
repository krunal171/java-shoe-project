package CDIPackage;

import SessionPackage.ShoeSessionBeanLocal;
import com.mycompany.shoe_store.Shoe;
import com.mycompany.shoe_store.Brand;
import com.mycompany.shoe_store.Color;
import com.mycompany.shoe_store.Categories;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.ServletContext;
import java.io.Serializable;
import java.util.List;

/**
 * CDI Managed Bean for Shoe management.
 */
@Named(value = "shoeBeanCDI")
@SessionScoped
public class ShoeBeanCDI implements Serializable {

    @EJB
    private ShoeSessionBeanLocal sbean;

    @EJB
    private SessionPackage.CartSessionBeanLocal cbean;

    @EJB
    private SessionPackage.OrderSessionBeanLocal orderBean;

    @jakarta.inject.Inject
    private RegisterBeanCDI registerBeanCDI;

    private String shoeName;
    private String category;
    private String brand;
    private Double price;
    private String description;
    private String imageUrl;
    private String colorId;
    private String[] selectedColors;
    private String status = "Available";
    private String selectedSize = "8"; // Default size
    private String selectedColor;
    private Integer quantity = 1;
    private Part imageFile;
    private Shoe selectedShoe;
    private Integer editingCartId;
    
    // Filtering properties
    private Integer filterCategoryId;
    private Integer filterBrandId;

    public Shoe getSelectedShoe() {
        return selectedShoe;
    }

    public void setSelectedShoe(Shoe selectedShoe) {
        this.selectedShoe = selectedShoe;
    }

    public ShoeBeanCDI() {
    }

    // Getters and Setters
    public String getShoeName() { return shoeName; }
    public void setShoeName(String shoeName) { this.shoeName = shoeName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getColorId() { return colorId; }
    public void setColorId(String colorId) { this.colorId = colorId; }

    public String[] getSelectedColors() {
        return selectedColors;
    }

    public void setSelectedColors(String[] selectedColors) {
        this.selectedColors = selectedColors;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSelectedSize() { return selectedSize; }
    public void setSelectedSize(String selectedSize) { this.selectedSize = selectedSize; }

    public String getSelectedColor() { return selectedColor; }
    public void setSelectedColor(String selectedColor) { this.selectedColor = selectedColor; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Part getImageFile() {
        return imageFile;
    }
    public void setImageFile(Part imageFile) { this.imageFile = imageFile; }
    
    public Integer getFilterCategoryId() { return filterCategoryId; }
    public void setFilterCategoryId(Integer filterCategoryId) { this.filterCategoryId = filterCategoryId; }

    public Integer getFilterBrandId() { return filterBrandId; }
    public void setFilterBrandId(Integer filterBrandId) { this.filterBrandId = filterBrandId; }

    public Integer getEditingCartId() {
        return editingCartId;
    }

    public void setEditingCartId(Integer editingCartId) {
        this.editingCartId = editingCartId;
    }

    public List<Shoe> getAllShoes() {
        return sbean.getAllShoes();
    }

    public List<Shoe> getFilteredShoesList() {
        return sbean.getFilteredShoes(filterCategoryId, filterBrandId);
    }
    
    public void setFilterCategory(Integer id) {
        this.filterCategoryId = id;
    }
    
    public void setFilterBrand(Integer id) {
        this.filterBrandId = id;
    }
    
    public void clearFilters() {
        this.filterCategoryId = null;
        this.filterBrandId = null;
    }

    public List<Brand> getAllBrands() {
        return sbean.getAllBrands();
    }

    public List<Color> getAllColors() {
        return sbean.getAllColors();
    }

    public List<Categories> getAllCategories() {
        return sbean.getAllCategories();
    }

    public String getBrandName(Integer id) {
        if (id == null) return "Unknown";
        List<Brand> brands = sbean.getAllBrands();
        if (brands != null) {
            for (Brand b : brands) {
                if (b.getBrandId().equals(id)) return b.getBrandName();
            }
        }
        return "Unknown";
    }

    public String getCategoryName(Integer id) {
        if (id == null) return "Unknown";
        List<Categories> cats = sbean.getAllCategories();
        if (cats != null) {
            for (Categories c : cats) {
                if (c.getCategoryId().equals(id)) return c.getCategoryName();
            }
        }
        return "Unknown";
    }

    public String getColorName(Integer id) {
        if (id == null) return "Unknown";
        List<Color> colors = sbean.getAllColors();
        if (colors != null) {
            for (Color c : colors) {
                if (c.getColorId().equals(id)) return c.getColorName();
            }
        }
        return "Unknown";
    }

    public String getColorNames(String ids) {
        if (ids == null || ids.trim().isEmpty()) return "Unknown";
        String[] idArray = ids.split(",");
        java.util.List<String> names = new java.util.ArrayList<>();
        for (String idStr : idArray) {
            try {
                names.add(getColorName(Integer.parseInt(idStr.trim())));
            } catch (Exception e) {}
        }
        return names.isEmpty() ? "Unknown" : String.join(", ", names);
    }

    public java.util.List<Color> getSelectedShoeColors() {
        if (selectedShoe == null || selectedShoe.getColorId() == null || selectedShoe.getColorId().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        String[] ids = selectedShoe.getColorId().split(",");
        java.util.List<Color> allColors = sbean.getAllColors();
        java.util.List<Color> selected = new java.util.ArrayList<>();
        for (String id : ids) {
            for (Color c : allColors) {
                if (c.getColorId().toString().equals(id.trim())) {
                    selected.add(c);
                    break;
                }
            }
        }
        return selected;
    }

    public String getColorHex(String name) {
        if (name == null) return "#6c757d";
        name = name.toLowerCase();
        if (name.contains("black")) return "#03071E";
        if (name.contains("blue") || name.contains("teal")) return "#1282A2";
        if (name.contains("red") || name.contains("crimson")) return "#E63946";
        if (name.contains("white") || name.contains("pearl")) return "#F1FAEE";
        if (name.contains("grey") || name.contains("gray")) return "#CED4DA";
        if (name.contains("green") || name.contains("olive")) return "#2D6A4F";
        if (name.contains("yellow") || name.contains("gold")) return "#FFD60A";
        if (name.contains("orange") || name.contains("amber")) return "#FB8500";
        if (name.contains("brown") || name.contains("bronze")) return "#432818";
        if (name.contains("pink") || name.contains("rose")) return "#FFB7C5";
        return "#6c757d"; // default gray
    }

    public String addShoe() {
        Integer pCategory = null;
        Integer pBrand = null;
        
        try { if(category != null && !category.isEmpty()) pCategory = Integer.parseInt(category); } catch(Exception e){}
        try { if(brand != null && !brand.isEmpty()) pBrand = Integer.parseInt(brand); } catch(Exception e){}
        
        String pColorIds = "";
        if (selectedColors != null && selectedColors.length > 0) {
            pColorIds = String.join(",", selectedColors);
        }

        String uploadedFileName = saveUploadedImage();
        if (uploadedFileName != null) {
            this.imageUrl = uploadedFileName;
        }

        sbean.addShoe(shoeName, pCategory, pBrand, price, description, imageUrl, pColorIds, status);
        clearFields();
        return "ShoeManagement.xhtml?faces-redirect=true";
    }

    private String saveUploadedImage() {
        if (imageFile != null && imageFile.getSize() > 0) {
            try {
                String fileName = Paths.get(imageFile.getSubmittedFileName()).getFileName().toString();
                String uploadDir = "e:/8th sem project/Sem8_Project_new/Shoe_Store/src/main/webapp/Photos";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String uniqueFileName = UUID.randomUUID() + "_" + fileName;
                File savedFile = new File(dir, uniqueFileName);

                try (InputStream input = imageFile.getInputStream()) {
                    Files.copy(input, savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Also copy to the continuously-running active deployment folder so images appear instantly!
                FacesContext context = FacesContext.getCurrentInstance();
                if (context != null) {
                    ServletContext sc = (ServletContext) context.getExternalContext().getContext();
                    String deployPath = sc.getRealPath("/Photos");
                    if (deployPath != null) {
                        File deployDir = new File(deployPath);
                        if (!deployDir.exists()) deployDir.mkdirs();
                        File deployFile = new File(deployDir, uniqueFileName);
                        Files.copy(savedFile.toPath(), deployFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                return uniqueFileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String deleteShoe(Integer id) {
        sbean.deleteShoe(id);
        return "ShoeManagement.xhtml?faces-redirect=true";
    }

    public String viewShoeDetails(Shoe s) {
        this.selectedShoe = s;
        this.editingCartId = null;
        this.selectedColor = null;
        this.selectedSize = "8";
        this.quantity = 1;
        return "ShoeDetail.xhtml?faces-redirect=true";
    }

    public String editCartItem(com.mycompany.shoe_store.Cart item) {
        this.selectedShoe = getShoeById(item.getShoeid());
        this.editingCartId = item.getCartid();
        this.selectedColor = item.getSelectedColor();
        this.selectedSize = item.getSelectedSize();
        this.quantity = item.getQuantity();
        return "ShoeDetail.xhtml?faces-redirect=true";
    }

    public String updateCartItem() {
        if (editingCartId != null) {
            cbean.updateCartItem(editingCartId, quantity, selectedColor, selectedSize);
            editingCartId = null;
        }
        return "Cart.xhtml?faces-redirect=true";
    }

    public String editShoe(Shoe s) {
        this.selectedShoe = s;
        if (s.getColorId() != null && !s.getColorId().isEmpty()) {
            this.selectedColors = s.getColorId().split(",");
        } else {
            this.selectedColors = new String[0];
        }
        return "/admin/EditShoe.xhtml?faces-redirect=true";
    }

    public String updateShoe() {
        try {
            if (selectedColors != null && selectedColors.length > 0) {
                selectedShoe.setColorId(String.join(",", selectedColors));
            } else {
                selectedShoe.setColorId("");
            }

            // Check if a new image was uploaded
            String newImage = saveUploadedImage();
            if (newImage != null) {
                selectedShoe.setImageUrl(newImage);
            }

            sbean.updateShoe(selectedShoe);
            return "ShoeManagement.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String addToCart() {
        if (registerBeanCDI.getCurrent() == null) {
            return "Login.xhtml?faces-redirect=true";
        }
        
        if (selectedShoe != null) {
            cbean.addToCart(
                registerBeanCDI.getCurrent().getUserid(),
                selectedShoe.getShoeId(),
                quantity,
                selectedColor,
                selectedSize
            );
        }
        
        return "Shoes.xhtml?faces-redirect=true";
    }

    public int getCartCount() {
        if (registerBeanCDI.getCurrent() == null) {
            return 0;
        }
        java.util.List<com.mycompany.shoe_store.Cart> cart = cbean.getCartByUserId(registerBeanCDI.getCurrent().getUserid());
        return (cart != null) ? cart.size() : 0;
    }

    public java.util.List<com.mycompany.shoe_store.Cart> getCartItems() {
        if (registerBeanCDI.getCurrent() == null) {
            return new java.util.ArrayList<>();
        }
        return cbean.getCartByUserId(registerBeanCDI.getCurrent().getUserid());
    }

    public Shoe getShoeById(Integer id) {
        return sbean.getShoeById(id);
    }

    public double getCartTotal() {
        java.util.List<com.mycompany.shoe_store.Cart> items = getCartItems();
        double total = 0;
        for (com.mycompany.shoe_store.Cart item : items) {
            Shoe s = getShoeById(item.getShoeid());
            if (s != null) {
                total += s.getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    public void removeFromCart(Integer cartId) {
        cbean.removeFromCart(cartId);
    }

    public List<com.mycompany.shoe_store.Reviews> getSelectedShoeReviews() {
        if (selectedShoe == null) return new java.util.ArrayList<>();
        return orderBean.getReviewsByShoeId(selectedShoe.getShoeId());
    }

    public double getAverageRating() {
        List<com.mycompany.shoe_store.Reviews> reviews = getSelectedShoeReviews();
        if (reviews.isEmpty()) return 0.0;
        double sum = 0;
        for (com.mycompany.shoe_store.Reviews r : reviews) {
            sum += r.getRating();
        }
        return sum / reviews.size();
    }

    public int getReviewCount() {
        return getSelectedShoeReviews().size();
    }

    private void clearFields() {
        this.shoeName = "";
        this.category = null;
        this.brand = null;
        this.price = null;
        this.description = "";
        this.imageUrl = "";
        this.colorId = null;
        this.imageFile = null;
    }
}
