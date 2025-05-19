package talabatapp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AdminFileGenerator implements Serializable{
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        
        list.add(new Admin("Ahmed", "ahmed@gmail.com", "123", UserRole.ADMIN));

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\Users.txt"));
            out.writeObject(list);
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing admin file: " + e.getMessage());
        }
    }
}

