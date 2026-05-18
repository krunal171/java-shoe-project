package CDIPackage;

import SessionPackage.OrderSessionBeanLocal;
import SessionPackage.ShoeSessionBeanLocal;
import com.mycompany.shoe_store.Orderdetails;
import com.mycompany.shoe_store.Orders;
import com.mycompany.shoe_store.Shoe;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "purchaseHistoryBeanCDI")
@SessionScoped
public class PurchaseHistoryBeanCDI implements Serializable {

    @EJB
    private OrderSessionBeanLocal orderSessionBean;

    @EJB
    private ShoeSessionBeanLocal shoeSessionBean;

    @Inject
    private RegisterBeanCDI registerBean;

    private List<Orders> userOrders;
    private Integer currentUserId;
    
    // For Review Modal
    private int selectedShoeId;
    private int selectedRating = 5;
    private String reviewComment;

    public PurchaseHistoryBeanCDI() {
    }

    @PostConstruct
    public void init() {
        // We initialize later when page loads to ensure correct user
    }

    public void loadUserOrders() {
        if (registerBean != null && registerBean.getCurrent() != null) {
            currentUserId = registerBean.getCurrent().getUserid();
            userOrders = orderSessionBean.getOrdersByUserId(currentUserId);
        }
    }

    public List<Orders> getUserOrders() {
        loadUserOrders();
        return userOrders;
    }

    public List<Orderdetails> getOrderDetails(Integer orderId) {
        return orderSessionBean.getOrderDetailsByOrderId(orderId);
    }
    
    public Shoe getShoe(Integer shoeId) {
        return shoeSessionBean.getShoeById(shoeId);
    }

    public boolean hasReviewed(Integer shoeId) {
        if (currentUserId == null) return false;
        return orderSessionBean.hasReviewed(currentUserId, shoeId);
    }

    public void prepareReview(Integer shoeId) {
        this.selectedShoeId = shoeId;
        this.selectedRating = 5;
        this.reviewComment = "";
    }

    public String submitReview() {
        if (currentUserId != null && selectedShoeId > 0) {
            orderSessionBean.addReview(currentUserId, selectedShoeId, selectedRating, reviewComment);
        }
        return "PurchaseHistory.xhtml?faces-redirect=true";
    }

    public int getSelectedShoeId() { return selectedShoeId; }
    public void setSelectedShoeId(int selectedShoeId) { this.selectedShoeId = selectedShoeId; }

    public int getSelectedRating() { return selectedRating; }
    public void setSelectedRating(int selectedRating) { this.selectedRating = selectedRating; }

    public String getReviewComment() { return reviewComment; }
    public void setReviewComment(String reviewComment) { this.reviewComment = reviewComment; }
}
