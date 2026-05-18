/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package SessionPackage;

import com.mycompany.shoe_store.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author VICTUS
 */
@Stateless
public class RegistrationSessionBean implements RegistrationSessionBeanLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);

    }

    public String Register(String name, String email, String password, String role, String phone) {
        User r = new User();
        r.setName(name);
        r.setEmail(email);
        r.setPassword(password);
        r.setRole(role);
        r.setPhone(phone);

        em.persist(r);
        em.flush();
        return "User Registration";
    }

    public User Login(String email, String password) {
    try {
        User user = em.createQuery(
                "SELECT r FROM User r WHERE r.email = :e AND r.password = :p",
                User.class)
                .setParameter("e", email)
                .setParameter("p", password)
                .getSingleResult();

        return user; 

    } catch (Exception ex) {
        return null;
    }
}

    public List<User> DisplayRole() {
        try {
            return em.createNamedQuery("User.findAll").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        try {
            Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void deleteUser(Integer userid) {
        User u = em.find(User.class, userid);
        if (u != null) {
            em.remove(u);
        }
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public User getUserById(Integer userid) {
        return em.find(User.class, userid);
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return em.createNamedQuery("User.findAll", User.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
