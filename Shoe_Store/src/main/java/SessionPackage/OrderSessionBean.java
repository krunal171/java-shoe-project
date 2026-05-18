package SessionPackage;

import com.mycompany.shoe_store.Orders;
import com.mycompany.shoe_store.Orderdetails;
import com.mycompany.shoe_store.Reviews;
import com.mycompany.shoe_store.UserAddresses;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderSessionBean implements OrderSessionBeanLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    public Integer saveAddress(UserAddresses address) {
        try {
            // Check if user already has an address
            UserAddresses existing = getLatestAddressByUserId(address.getUserid());

            if (existing != null) {
                // Update existing address details instead of adding a new one
                existing.setFullname(address.getFullname());
                existing.setMobile(address.getMobile());
                existing.setPincode(address.getPincode());
                existing.setHouseNo(address.getHouseNo());
                existing.setArea(address.getArea());
                existing.setLandmark(address.getLandmark());
                existing.setCity(address.getCity());
                existing.setState(address.getState());
                
                em.merge(existing);
                em.flush();
                return existing.getAddressid();
            } else {
                // No address found, add it for the first time
                em.persist(address);
                em.flush(); // To get the generated ID
                return address.getAddressid();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer placeOrder(Orders order, List<Orderdetails> details) {
        try {
            em.persist(order);
            em.flush(); // To get the generated ID
            
            for (Orderdetails detail : details) {
                detail.setOrderid(order.getOrderid());
                em.persist(detail);
            }
            return order.getOrderid();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserAddresses getLatestAddressByUserId(Integer userId) {
        try {
            List<UserAddresses> addresses = em.createQuery(
                "SELECT a FROM UserAddresses a WHERE a.userid = :uid ORDER BY a.addressid DESC", 
                UserAddresses.class)
                .setParameter("uid", userId)
                .setMaxResults(1)
                .getResultList();
            return addresses.isEmpty() ? null : addresses.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Orders> getOrdersByUserId(Integer userId) {
        try {
            return em.createQuery("SELECT o FROM Orders o WHERE o.userid = :uid ORDER BY o.orderid DESC", Orders.class)
                     .setParameter("uid", userId)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public java.util.List<com.mycompany.shoe_store.Orders> getAllOrders() {
        try {
            return em.createQuery("SELECT o FROM Orders o ORDER BY o.orderid DESC", com.mycompany.shoe_store.Orders.class)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateOrder(com.mycompany.shoe_store.Orders order) {
        try {
            em.merge(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(Integer orderid) {
        try {
            // Delete order details first
            em.createQuery("DELETE FROM Orderdetails d WHERE d.orderid = :oid")
              .setParameter("oid", orderid)
              .executeUpdate();
              
            // Delete the order
            com.mycompany.shoe_store.Orders order = em.find(com.mycompany.shoe_store.Orders.class, orderid);
            if (order != null) {
                em.remove(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Orderdetails> getOrderDetailsByOrderId(Integer orderid) {
        try {
            return em.createQuery("SELECT d FROM Orderdetails d WHERE d.orderid = :oid", Orderdetails.class)
                     .setParameter("oid", orderid)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addReview(int userid, int shoeid, int rating, String comment) {
        // Manually calculate next ID because DB table might not have AUTO_INCREMENT set
        Integer maxId = (Integer) em.createQuery("SELECT MAX(r.reviewid) FROM Reviews r").getSingleResult();
        int nextId = (maxId == null) ? 1 : maxId + 1;

        Reviews review = new Reviews();
        review.setReviewid(nextId);
        review.setUserid(userid);
        review.setShoeid(shoeid);
        review.setRating(rating);
        review.setComment(comment);
        em.persist(review);
        em.flush();
    }

    @Override
    public boolean hasReviewed(int userid, int shoeid) {
        try {
            Long count = em.createQuery("SELECT COUNT(r) FROM Reviews r WHERE r.userid = :uid AND r.shoeid = :sid", Long.class)
                           .setParameter("uid", userid)
                           .setParameter("sid", shoeid)
                           .getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<Reviews> getReviewsByShoeId(int shoeid) {
        try {
            return em.createQuery("SELECT r FROM Reviews r WHERE r.shoeid = :sid ORDER BY r.createdAt DESC", Reviews.class)
                     .setParameter("sid", shoeid)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
}
