package SessionPackage;

import com.mycompany.shoe_store.Shoe;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * EJB class for Shoe management.
 */
@Stateless
public class ShoeSessionBean implements ShoeSessionBeanLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    public void addShoe(String name, Integer category, Integer brand, Double price, String description, String imageUrl, String colorId, String status) {
        Shoe s = new Shoe();
        s.setShoeName(name);
        s.setCategory(category);
        s.setBrand(brand);
        s.setPrice(price);
        s.setDescription(description);
        s.setImageUrl(imageUrl);
        s.setColorId(colorId);
        s.setStatus(status);
        em.persist(s);
        em.flush();
    }

    @Override
    public List<Shoe> getAllShoes() {
        try {
            return em.createNamedQuery("Shoe.findAll", Shoe.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Shoe getShoeById(Integer shoeId) {
        return em.find(Shoe.class, shoeId);
    }

    @Override
    public void updateShoe(Shoe shoe) {
        em.merge(shoe);
    }

    @Override
    public void deleteShoe(Integer shoeId) {
        Shoe s = em.find(Shoe.class, shoeId);
        if (s != null) {
            em.remove(s);
        }
    }

    @Override
    public List<com.mycompany.shoe_store.Brand> getAllBrands() {
        try {
            return em.createNamedQuery("Brand.findAll", com.mycompany.shoe_store.Brand.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<com.mycompany.shoe_store.Color> getAllColors() {
        try {
            return em.createNamedQuery("Color.findAll", com.mycompany.shoe_store.Color.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<com.mycompany.shoe_store.Categories> getAllCategories() {
        try {
            return em.createNamedQuery("Categories.findAll", com.mycompany.shoe_store.Categories.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addBrand(String name, String country) {
        com.mycompany.shoe_store.Brand b = new com.mycompany.shoe_store.Brand();
        b.setBrandName(name);
        b.setCountry(country);
        em.persist(b);
    }

    @Override
    public void deleteBrand(Integer id) {
        com.mycompany.shoe_store.Brand b = em.find(com.mycompany.shoe_store.Brand.class, id);
        if (b != null) {
            em.remove(b);
        }
    }

    @Override
    public void addColor(String name) {
        com.mycompany.shoe_store.Color c = new com.mycompany.shoe_store.Color();
        c.setColorName(name);
        em.persist(c);
    }

    @Override
    public void deleteColor(Integer id) {
        com.mycompany.shoe_store.Color c = em.find(com.mycompany.shoe_store.Color.class, id);
        if (c != null) {
            em.remove(c);
        }
    }

    @Override
    public void addCategory(String name, String description) {
        com.mycompany.shoe_store.Categories cat = new com.mycompany.shoe_store.Categories();
        cat.setCategoryName(name);
        cat.setDescription(description);
        em.persist(cat);
    }

    @Override
    public void deleteCategory(Integer id) {
        com.mycompany.shoe_store.Categories cat = em.find(com.mycompany.shoe_store.Categories.class, id);
        if (cat != null) {
            em.remove(cat);
        }
    }

    @Override
    public List<Shoe> getFilteredShoes(Integer categoryId, Integer brandId) {
        StringBuilder jpql = new StringBuilder("SELECT s FROM Shoe s WHERE 1=1");
        if (categoryId != null && categoryId != 0) {
            jpql.append(" AND s.category = :categoryId");
        }
        if (brandId != null && brandId != 0) {
            jpql.append(" AND s.brand = :brandId");
        }
        
        var query = em.createQuery(jpql.toString(), Shoe.class);
        if (categoryId != null && categoryId != 0) {
            query.setParameter("categoryId", categoryId);
        }
        if (brandId != null && brandId != 0) {
            query.setParameter("brandId", brandId);
        }
        
        return query.getResultList();
    }
}

