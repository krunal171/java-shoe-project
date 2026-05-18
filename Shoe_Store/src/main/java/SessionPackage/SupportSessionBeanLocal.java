package SessionPackage;

import com.mycompany.shoe_store.ContactSupport;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface SupportSessionBeanLocal {
    void submitQuery(ContactSupport query);
    List<ContactSupport> getAllQueries();
}
