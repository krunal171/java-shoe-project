package CDIPackage;

import SessionPackage.OrderSessionBeanLocal;
import SessionPackage.RegistrationSessionBeanLocal;
import SessionPackage.ShoeSessionBeanLocal;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(value = "dashboardBeanCDI")
@RequestScoped
public class DashboardBeanCDI {

    @Inject
    private RegistrationSessionBeanLocal rsbl;

    @Inject
    private ShoeSessionBeanLocal ssbl;

    @Inject
    private OrderSessionBeanLocal osbl;

    public int getTotalUsers() {
        var users = rsbl.getAllUsers();
        return users != null ? users.size() : 0;
    }

    public int getTotalShoes() {
        var shoes = ssbl.getAllShoes();
        return shoes != null ? shoes.size() : 0;
    }

    public int getTotalCategories() {
        var cats = ssbl.getAllCategories();
        return cats != null ? cats.size() : 0;
    }

    public int getTotalOrders() {
        var orders = osbl.getAllOrders();
        return orders != null ? orders.size() : 0;
    }
}
