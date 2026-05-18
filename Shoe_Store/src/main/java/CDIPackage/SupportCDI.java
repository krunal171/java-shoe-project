package CDIPackage;

import SessionPackage.SupportSessionBeanLocal;
import com.mycompany.shoe_store.ContactSupport;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named(value = "supportCDI")
@RequestScoped
public class SupportCDI implements Serializable {

    @EJB
    private SupportSessionBeanLocal supportBean;
    
    @Inject
    private RegisterBeanCDI registerBean;

    private String name;
    private String email;
    private String subject;
    private String message;

    public SupportCDI() {
    }
    
    @PostConstruct
    public void init() {
        if (registerBean != null && registerBean.getCurrent() != null) {
            this.name = registerBean.getCurrent().getName();
            this.email = registerBean.getCurrent().getEmail();
        }
    }

    public String submit() {
        try {
            ContactSupport query = new ContactSupport();
            query.setName(name);
            query.setEmail(email);
            query.setSubject(subject);
            query.setMessage(message);
            
            supportBean.submitQuery(query);
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Your query has been submitted successfully! We will contact you soon.", null));
            
            clearFields();
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Submission failed: " + e.getMessage(), null));
            return null;
        }
    }

    private void clearFields() {
        this.name = null;
        this.email = null;
        this.subject = null;
        this.message = null;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
