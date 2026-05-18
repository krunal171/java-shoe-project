package SessionPackage;

import com.mycompany.shoe_store.ContactSupport;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SupportSessionBean implements SupportSessionBeanLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    public void submitQuery(ContactSupport query) {
        em.persist(query);
    }

    @Override
    public List<ContactSupport> getAllQueries() {
        try {
            return em.createNamedQuery("ContactSupport.findAll", ContactSupport.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
