/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package SessionPackage;

import com.mycompany.shoe_store.User;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author VICTUS
 */
@Local
public interface RegistrationSessionBeanLocal {
     public String Register(String name, String email, String password, String role, String phone);
     public User Login(String email, String Password);
     public List<User> DisplayRole();
     public boolean isEmailExists(String email);
     public void deleteUser(Integer userid);
     public void updateUser(User user);
     public User getUserById(Integer userid);
     public List<User> getAllUsers();
}
