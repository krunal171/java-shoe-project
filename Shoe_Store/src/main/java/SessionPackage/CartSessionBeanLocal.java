package SessionPackage;

import com.mycompany.shoe_store.Cart;
import jakarta.ejb.Local;
import java.util.List;

/**
 * Local interface for Cart management.
 */
@Local
public interface CartSessionBeanLocal {
    void addToCart(Integer userId, Integer shoeId, Integer quantity, String color, String size);
    List<Cart> getCartByUserId(Integer userId);
    void removeFromCart(Integer cartId);
    void updateCartItem(Integer cartId, Integer quantity, String color, String size);
    void clearCart(Integer userId);
}
