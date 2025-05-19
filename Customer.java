package talabatapp;

import java.io.*;
import java.util.ArrayList;

public class Customer extends User implements Serializable {
    private String address;
    private ArrayList<Product> cart = new ArrayList<>();
    private ArrayList<String> orders = new ArrayList<>();
    

    public Customer(String name, String email, String password, UserRole role) {
        super(name, email, password, role);
    }

    public Customer(String name, String email, String password, UserRole role, String address) {
        super(name, email, password, role.CUSTOMER);
        this.address = address;
    }

    // Register function to add a new customer
    public void register(String name, String email, String password, UserRole role, String address) {
    try {
        ArrayList<User> users = new ArrayList<>();
        File file = new File("D:\\Users.txt");

        // Load existing users if file exists
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (ArrayList<User>) ois.readObject();
            } catch (Exception ex) {
                System.out.println("Error reading existing users: " + ex.getMessage());
            }
        }

        // Add new customer
        Customer newCustomer = new Customer(name, email, password, role, address);
        users.add(newCustomer);

        // Write updated list back
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(users);
            System.out.println("Customer registered successfully.");
        }
    } catch (Exception e) {
        System.out.println("Error registering customer: " + e.getMessage());
    }
}
    
    

    public void addToCart(Product product) {
        try {
            if (product != null) {
                cart.add(product);
                System.out.println("Product added to cart: " + product.getName());
            } else {
                throw new IllegalArgumentException("Product cannot be null.");
            }
        } catch (Exception e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
        }
    }
    
    

    public void removeFromCart(Product product) {
        try {
            if (cart.contains(product)) {
                cart.remove(product);
                System.out.println("Product removed from cart: " + product.getName());
            } else {
                throw new IllegalArgumentException("Product not found in cart.");
            }
        } catch (Exception e) {
            System.out.println("Error removing product from cart: " + e.getMessage());
        }
    }

    // Track an order (Just a sample logic)
    public void trackOrder(int orderId) {
        try {
            if (orderId >= 0 && orderId < orders.size()) {
                System.out.println("Tracking Order: " + orders.get(orderId));
            } else {
                throw new IndexOutOfBoundsException("Order ID not valid.");
            }
        } catch (Exception e) {
            System.out.println("Error tracking order: " + e.getMessage());
        }
    }

    // Getter for cart
    public ArrayList<Product> getCart() {
        return cart;
    }

    // Getter for orders
    public ArrayList<String> getOrders() {
        return orders;
    }    
}