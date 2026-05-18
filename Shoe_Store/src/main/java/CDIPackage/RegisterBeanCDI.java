/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIPackage;

import SessionPackage.RegistrationSessionBeanLocal;
import com.mycompany.shoe_store.User;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

/**
 *
 * @author VICTUS
 */
@Named(value = "registerBeanCDI")
@SessionScoped
public class RegisterBeanCDI implements Serializable {

    @EJB
    RegistrationSessionBeanLocal rbean;

    public RegisterBeanCDI() {
    }
    String name;
    String email;
    String password;
    String newPassword;
    String role = "Client";
    String phone;
    User current;
    User selectedUser;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public RegistrationSessionBeanLocal getRbean() {
        return rbean;
    }

    public void setRbean(RegistrationSessionBeanLocal rbean) {
        this.rbean = rbean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public String saveStudent() {
//        User u = rbean.SearchCourse(courseid);
//        Studentmaster s = new Studentmaster(studentid, studentname);
//
//        s.setCourseid(c);
//        String msg = Coursesession.InsertStudent(s);
//        clear();
//        return "DisplayStudent.xhtml";
//    }

    public String register() {
        if (rbean.isEmailExists(email)) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "this mail is already uesed", null));
            return null;
        }
        
        if (role.equals("Admin") || role.equals("Client")) {
            try {
                String msg = rbean.Register(name, email, password, role, phone);
                return "Login.xhtml?faces-redirect=true";
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed: " + ex.getMessage(), null));
                ex.printStackTrace();
                return null;
            }
        } else {
            return "Error";
        }
    }

    public String login() {
        try {
            User r = rbean.Login(email, password);

            if (r == null) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Email or Password", null));
                return "Login.xhtml?faces-redirect=true";
            } else {
                this.current = r; // Store login session
                if (r.getRole().equals("Admin")) {
                    return "admin/Dashboard.xhtml?faces-redirect=true";
                } else {
                    return "HomePage.xhtml?faces-redirect=true";
                }
            }

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed: " + ex.getMessage(), null));
            ex.printStackTrace();
            return null;
        }
    }

    public String addUserByAdmin() {
        if (rbean.isEmailExists(email)) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "This email is already in use", null));
            return null;
        }
        
        if (role.equals("Admin") || role.equals("Client")) {
            try {
                rbean.Register(name, email, password, role, phone);
                // Clear the form fields upon successful addition
                this.name = null;
                this.email = null;
                this.password = null;
                this.phone = null;
                this.role = "Client";
                return "UserManagement.xhtml?faces-redirect=true";
            } catch (Exception ex) {
                Throwable cause = ex;
                while (cause != null) {
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to add user: " + cause.getMessage(), null));
                    cause = cause.getCause();
                }
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public java.util.List<User> getAllUsersList() {
        return rbean.getAllUsers();
    }

    public String logout() {
        this.current = null;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/HomePage.xhtml?faces-redirect=true";
    }

    public void deleteUser(User u) {
        try {
            rbean.deleteUser(u.getUserid());
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "User deleted successfully", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to delete user: " + ex.getMessage(), null));
        }
    }

    public String editUser(User u) {
        this.selectedUser = u;
        this.newPassword = ""; // Reset the temporary password field
        return "EditUser.xhtml?faces-redirect=true";
    }

    public String updateUser() {
        try {
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                selectedUser.setPassword(newPassword);
            }
            rbean.updateUser(selectedUser);
            return "UserManagement.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to update user: " + ex.getMessage(), null));
            return null;
        }
    }

    public String getUserName(Integer id) {
        if (id == null) return "Unknown";
        User u = rbean.getUserById(id);
        return u != null ? u.getName() : "Unknown User";
    }
}
