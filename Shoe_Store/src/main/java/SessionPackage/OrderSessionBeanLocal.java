package SessionPackage;

import com.mycompany.shoe_store.Orders;
import com.mycompany.shoe_store.Orderdetails;
import com.mycompany.shoe_store.UserAddresses;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface OrderSessionBeanLocal {
    Integer saveAddress(UserAddresses address);
    Integer placeOrder(Orders order, List<Orderdetails> details);
    UserAddresses getLatestAddressByUserId(Integer userId);
    List<Orders> getOrdersByUserId(Integer userId);
    List<Orders> getAllOrders();
    void updateOrder(Orders order);
    void deleteOrder(Integer orderid);
    List<Orderdetails> getOrderDetailsByOrderId(Integer orderid);
    void addReview(int userid, int shoeid, int rating, String comment);
    boolean hasReviewed(int userid, int shoeid);
    List<com.mycompany.shoe_store.Reviews> getReviewsByShoeId(int shoeid);
}
