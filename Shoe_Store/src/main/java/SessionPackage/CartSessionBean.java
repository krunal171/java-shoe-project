package SessionPackage;

import com.mycompany.shoe_store.Cart;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * EJB class for Cart management.
 */
@Stateless
public class CartSessionBean implements CartSessionBeanLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    public void addToCart(Integer userId, Integer shoeId, Integer quantity, String color, String size) {
        Cart c = new Cart(userId, shoeId, quantity, color, size);
        em.persist(c);
        em.flush();
    }

    @Override
    public List<Cart> getCartByUserId(Integer userId) {
        try {
            return em.createNamedQuery("Cart.findByUserid", Cart.class)
                     .setParameter("userid", userId)
                     .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void removeFromCart(Integer cartId) {
        Cart c = em.find(Cart.class, cartId);
        if (c != null) {
            em.remove(c);
        }
    }

    @Override
    public void updateCartItem(Integer cartId, Integer quantity, String color, String size) {
        Cart c = em.find(Cart.class, cartId);
        if (c != null) {
            c.setQuantity(quantity);
            c.setSelectedColor(color);
            c.setSelectedSize(size);
            em.merge(c);
        }
    }

    @Override
    public void clearCart(Integer userId) {
        List<Cart> items = getCartByUserId(userId);
        if (items != null) {
            for (Cart item : items) {
                em.remove(item);
            }
        }
    }
}
