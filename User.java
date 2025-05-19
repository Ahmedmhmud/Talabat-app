package talabatapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    protected String name;
    protected String email;
    protected String password;
    protected UserRole role;

    public User(String email){
        this.email = email;
    }
    
    public User(String email, String password, UserRole role){
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public static UserRole logIn(String email, String password){
        try {
            ObjectInputStream data = new ObjectInputStream(new FileInputStream("D:\\Users.txt"));
            ArrayList<User> list = (ArrayList<User>)data.readObject();
            data.close();
            for(User a : list){
                if(a.getEmail().equals(email) && a.getPassword().equals(password)){
                    if(a instanceof Admin){
                        return UserRole.ADMIN;
                    } else if(a instanceof Customer){
                        return UserRole.CUSTOMER;
                    } else if(a instanceof Seller){
                        return UserRole.SELLER;
                    } else
                        return null;
                }
                else if(a.getEmail().equals(email) && !a.getPassword().equals(password)){
                    return null;
                }
            }
            System.out.println("Email not found");
            return null;
        } catch (FileNotFoundException ex) {
            System.out.println("File is not Found");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IOException");
        } catch (ClassNotFoundException ex) {
            System.out.println("No Admins Found");
        }
        return null;
    }
}