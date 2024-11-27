package utb.fai.RESTAPIServer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository ;

    @GetMapping("/users")
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser")
    public ResponseEntity<MyUser> getUserById(@RequestParam(name = "id") Long id) {
        
        MyUser user = userRepository.findById(id).orElse(null);
        if (user != null)
        {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createUser")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser newUser) {
        
        if (newUser.isUserDataValid()) {
            /*
            Version-Property and Id-Property inspection (default): By default Spring Data JPA inspects first if there is a
            Version-property of non-primitive type. If there is, the entity is considered new if the value of that property is
            null. Without such a Version-property Spring Data JPA inspects the identifier property of the given entity. If
            the identifier property is null, then the entity is assumed to be new. Otherwise, it is assumed to be not new.

            Teraz nerozumiem preco som musel urobit novy objekt savedUser aby to prepisovalo hodnoty id 
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);  ---> id= null   :-)
            */ 
            
            MyUser savedUser = userRepository.save(newUser);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/editUser")
    public ResponseEntity<MyUser> editUser(@RequestParam(name = "id") Long id, @RequestBody MyUser updateUserInformation) {
        MyUser user = userRepository.findById(id).orElse(null);
        if (user == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!updateUserInformation.isUserDataValid())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    
        }
        user.setName(updateUserInformation.getName());
        user.setEmail(updateUserInformation.getEmail());
        user.setPhoneNumber(updateUserInformation.getPhoneNumber());

        MyUser updatedUser = userRepository.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }
    
    @DeleteMapping("/deleteUser")
    public ResponseEntity<MyUser> deleteUser(@RequestParam(name = "id") Long id) {
        MyUser user = userRepository.findById(id).orElse(null);
        if (user == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteAll")
    public ResponseEntity<MyUser> deleteAll() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }
    }
        

}
