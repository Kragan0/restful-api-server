package utb.fai.RESTAPIServer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(name="name", nullable = false, length=100)
    private String name;
    
    @Column(name="email", nullable= false, unique=true, length = 100)
    private String email;

    @Column(name="phoneNumber", nullable= false, length = 15)
    private String phoneNumber;

    
    public MyUser() {}

    public MyUser(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean isUserDataValid() {
        // Add your validation logic here (e.g., email and phone number format
        // validation)
        
        if (this.name == null || this.name.trim().isEmpty())
        {
            return false;
        }

        // Regex na email
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; 
        if (!this.email.matches(emailPattern))
        {
            return false;
        }        
        // Regex na tel. čislo
        String phoneNumberPattern = "^(\\+?[1-9]\\d{1,14}|0\\d{9,14})$";
        if (!this.phoneNumber.matches(phoneNumberPattern))
        {
            return false;
        }
        
        return true;
    }

    // TODO: Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public void setEmail(String email){
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
