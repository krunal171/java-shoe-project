package CDIPackage;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



// ===================

@Named(value = "checkoutBeanCDI")
@SessionScoped
public class CheckoutBeanCDI implements Serializable {

    @jakarta.inject.Inject
    private RegisterBeanCDI registerBean;

    @jakarta.ejb.EJB
    private SessionPackage.OrderSessionBeanLocal orderBean;

    @jakarta.ejb.EJB
    private SessionPackage.CartSessionBeanLocal cartBean;

    @jakarta.inject.Inject
    private ShoeBeanCDI shoeBean;

    // Address fields
    private String fullName;
    private String mobileNumber;
    private String pincode;
    private String houseNo;
    private String area;
    private String landmark;
    private String city;
    private String state;
    private boolean addressExists = false;

    // Payment fields
    private String paymentMethod; // "COD", "DEBIT", "ONLINE"
    private String cardNumber;
    private String cardExpiry;
    private String cardCVV;
    private String upiOption; // "GOOGLE_PAY", "PHONEPE", "BHIM"

    public CheckoutBeanCDI() {
    }

    @jakarta.annotation.PostConstruct
    public void init() {
        if (registerBean.getCurrent() != null) {
            Integer userId = registerBean.getCurrent().getUserid();
            com.mycompany.shoe_store.UserAddresses lastAddr = orderBean.getLatestAddressByUserId(userId);
            
            if (lastAddr != null) {
                this.fullName = lastAddr.getFullname();
                this.mobileNumber = lastAddr.getMobile();
                this.pincode = lastAddr.getPincode();
                this.houseNo = lastAddr.getHouseNo();
                this.area = lastAddr.getArea();
                this.landmark = lastAddr.getLandmark();
                this.city = lastAddr.getCity();
                this.state = lastAddr.getState();
                this.addressExists = true;
            } else {
                this.fullName = registerBean.getCurrent().getName();
                this.mobileNumber = registerBean.getCurrent().getPhone();
                this.addressExists = false;
            }
        }
    }

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getHouseNo() { return houseNo; }
    public void setHouseNo(String houseNo) { this.houseNo = houseNo; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getLandmark() { return landmark; }
    public void setLandmark(String landmark) { this.landmark = landmark; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public boolean isAddressExists() { return addressExists; }
    public void setAddressExists(boolean addressExists) { this.addressExists = addressExists; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardExpiry() { return cardExpiry; }
    public void setCardExpiry(String cardExpiry) { this.cardExpiry = cardExpiry; }

    public String getCardCVV() { return cardCVV; }
    public void setCardCVV(String cardCVV) { this.cardCVV = cardCVV; }

    public String getUpiOption() { return upiOption; }
    public void setUpiOption(String upiOption) { this.upiOption = upiOption; }

    public void saveAddress() {
        this.addressExists = true;
    }

    public void editAddress() {
        this.addressExists = false;
    }

    public String placeOrder() {
        if (registerBean.getCurrent() == null) return "Login.xhtml?faces-redirect=true";
        
        // Ensure cart is not empty
        if (shoeBean.getCartItems().isEmpty()) {
            jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null, 
                new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR, "Error: Your cart is empty!", "Please add items to your cart before checking out."));
            return null;
        }
        
        Integer userId = registerBean.getCurrent().getUserid();
        
        // 1. Save Address (Updates existing or inserts new)
        com.mycompany.shoe_store.UserAddresses addr = new com.mycompany.shoe_store.UserAddresses();
        addr.setUserid(userId);
        addr.setFullname(fullName);
        addr.setMobile(mobileNumber);
        addr.setPincode(pincode);
        addr.setHouseNo(houseNo);
        addr.setArea(area);
        addr.setLandmark(landmark);
        addr.setCity(city);
        addr.setState(state);
        
        Integer savedAddressId = orderBean.saveAddress(addr);
        
        // 2. Create Order
        com.mycompany.shoe_store.Orders order = new com.mycompany.shoe_store.Orders();
        order.setUserid(userId);
        order.setOrderdate(new java.util.Date());
        order.setTotalamount((int) shoeBean.getCartTotal());
        
        // Map payment method to database enum values: 'Cash','Card','Upi','Netbanking'
        String mappedPayment = "Cash";
        if ("DEBIT".equals(paymentMethod)) mappedPayment = "Card";
        else if ("ONLINE".equals(paymentMethod)) mappedPayment = "Upi";
        else if ("COD".equals(paymentMethod)) mappedPayment = "Cash";
        
        order.setPaymentmethod(mappedPayment);
        order.setOrderstatus("Placed");
        order.setAddressId(savedAddressId);
        
        // 3. Prepare Order Details
        java.util.List<com.mycompany.shoe_store.Orderdetails> details = new java.util.ArrayList<>();
        for (com.mycompany.shoe_store.Cart cartItem : shoeBean.getCartItems()) {
            com.mycompany.shoe_store.Orderdetails d = new com.mycompany.shoe_store.Orderdetails();
            d.setShoeid(cartItem.getShoeid());
            d.setQuantity(cartItem.getQuantity());
            
            com.mycompany.shoe_store.Shoe s = shoeBean.getShoeById(cartItem.getShoeid());
            if (s != null) {
                d.setPrice(s.getPrice().intValue());
                d.setSubtotal(s.getPrice().intValue() * cartItem.getQuantity());
            }
            details.add(d);
        }
        
        // 4. Save Order and Details
        Integer orderId = orderBean.placeOrder(order, details);
        
        if (orderId == null) {
            jakarta.faces.context.FacesContext.getCurrentInstance().addMessage(null, 
                new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR, "Database Error", "Could not save your order. Please contact support."));
            return null;
        }
        
        // 5. Clear Cart
        cartBean.clearCart(userId);
        
        // Reset checkout state
        this.addressExists = false;

        // Add success message
        jakarta.faces.context.FacesContext facesContext = jakarta.faces.context.FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new jakarta.faces.application.FacesMessage(
            jakarta.faces.application.FacesMessage.SEVERITY_INFO, 
            "Order Placed Successfully!", 
            "Your order has been confirmed and will be delivered soon."
        ));
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        
        return "HomePage.xhtml?faces-redirect=true";
    }

    public java.util.List<com.mycompany.shoe_store.Orders> getPreviousOrders() {
        if (registerBean.getCurrent() == null) return new java.util.ArrayList<>();
        return orderBean.getOrdersByUserId(registerBean.getCurrent().getUserid());
    }

    public java.util.List<com.mycompany.shoe_store.Orders> getAllOrdersList() {
        return orderBean.getAllOrders();
    }

    public String updateOrderStatus(com.mycompany.shoe_store.Orders o, String newStatus) {
        try {
            o.setOrderstatus(newStatus);
            orderBean.updateOrder(o);
            return "OrderManagement.xhtml?faces-redirect=true"; 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String deleteOrder(Integer id) {
        try {
            orderBean.deleteOrder(id);
            return "OrderManagement.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
