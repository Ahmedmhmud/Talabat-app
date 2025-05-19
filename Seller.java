package talabatapp;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Seller extends User implements Managable  {
    double revenue;
    int numberOfSoldItems;

    public Seller(String email){
        super(email);
        revenue = 0;
        numberOfSoldItems = 0;
    }

    public Seller(String name, String email, String password, UserRole role) {
        super(name, email, password, role);
        revenue = 0;
        numberOfSoldItems = 0;
    }
    
    
    
    @Override
    public void add(Object obj){
        Product p = (Product) obj;  
        try (PrintWriter writer = new PrintWriter(new FileWriter(SellerScene.PRODUCTS_FILE_PATH, true))) {
                // Prepare the product details to write into the CSV
                String productDetails = p.getId() + "," + p.getName() + "," + p.getSeller() + "," + p.getPrice() + "," + p.getNumOfSoldPieces() + "," + p.getRevenue() + "," + p.getQuantity();
                writer.println(productDetails);  // Write the row to the CSV
                System.out.println("Product added to CSV: " + p);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    @Override
    public void edit(Object obj){
    Product updatedProduct = (Product) obj;
    int updatedProductId = updatedProduct.getId();
    List<Product> products = new ArrayList<>();
    boolean isEdited = false;

    // Read existing products from the CSV
    try (Scanner scanner = new Scanner(new File(SellerScene.PRODUCTS_FILE_PATH))) {
        if (scanner.hasNextLine()) scanner.nextLine(); // Skip header line

        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            int currentProductId = Integer.parseInt(data[0]);

            if (currentProductId == updatedProductId) {
                // If product ID matches, update only name or price if provided
                String newName = updatedProduct.getName().isEmpty() ? data[1] : updatedProduct.getName();
                double newPrice = updatedProduct.getPrice() == 0 ? Double.parseDouble(data[3]) : updatedProduct.getPrice();
                int newQuantity = updatedProduct.getQuantity() == 0 ? Integer.parseInt(data[6]) : updatedProduct.getQuantity();
                Product p = new Product(Integer.parseInt(data[0]), newName,this.email, newPrice, Integer.parseInt(data[4]), Double.parseDouble(data[5]),newQuantity);
                products.add(p);  // Add the updated product
                isEdited = true;
            } else {
                Product p = new Product(Integer.parseInt(data[0]), data[1],this.email, Double.parseDouble(data[3]), Integer.parseInt(data[4]), Double.parseDouble(data[5]),Integer.parseInt(data[6]));
                products.add(p);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (isEdited) {
        // Rewrite the CSV with updated products
        try (PrintWriter writer = new PrintWriter(new FileWriter(SellerScene.PRODUCTS_FILE_PATH))) {
            writer.println(SellerScene.PRODUCTS_HEADER); // Add header back
            for (Product p : products) {
                writer.println(p.getId() + "," + p.getName() + "," + p.getSeller() + "," + p.getPrice() + "," + p.getNumOfSoldPieces() + "," + p.getRevenue() + "," + p.getQuantity());
            }
            System.out.println("Product with ID " + updatedProductId + " edited successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Product with ID " + updatedProductId + " not found.");
    }
        
    }
    
    @Override
    public void remove(Object obj){
        int productId = (Integer) obj;
        List<Product> products = new ArrayList<>();
        boolean isRemoved = false;
    
    // Read existing products from the CSV
    try (Scanner scanner = new Scanner(new File(SellerScene.PRODUCTS_FILE_PATH))) {
        if (scanner.hasNextLine()) scanner.nextLine(); // Skip header line
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            
            if (Integer.parseInt(data[0]) != productId) {
                Product p = new Product(Integer.parseInt(data[0]), data[1],this.email, Double.parseDouble(data[3]), Integer.parseInt(data[4]), Double.parseDouble(data[5]),Integer.parseInt(data[6]));
                products.add(p);
            } else {
                isRemoved = true;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (isRemoved) {
        // Rewrite the CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(SellerScene.PRODUCTS_FILE_PATH))) {
            writer.println(SellerScene.PRODUCTS_HEADER); // Write header back
            for (Product p : products) {
                writer.println(p.getId() + "," + p.getName() + "," + p.getSeller() + "," + p.getPrice() + "," + p.getNumOfSoldPieces() + "," + p.getRevenue() + "," + p.getQuantity());
            }
            System.out.println("Product with ID " + productId + " removed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Product with ID " + productId + " not found.");
    }
        
    }
    
    @Override
    public void search(Object obj){
    String nameToSearch = ((String) obj).toLowerCase();
    List<Product> matchingProducts = new ArrayList<>();

    try (Scanner scanner = new Scanner(new File(SellerScene.PRODUCTS_FILE_PATH))) {
        if (scanner.hasNextLine()) scanner.nextLine(); // Skip header line

        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            String productName = data[1].toLowerCase();

            if (productName.contains(nameToSearch)) {
                Product p = new Product(Integer.parseInt(data[0]), data[1],this.email, Double.parseDouble(data[3]), Integer.parseInt(data[4]), Double.parseDouble(data[5]),Integer.parseInt(data[6]));
                matchingProducts.add(p);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Display search results
    if (matchingProducts.isEmpty()) {
        System.out.println("No products found matching the name: " + nameToSearch);
    } else {
        System.out.println("Products matching '" + nameToSearch + "':");
        for (Product p : matchingProducts) {
            System.out.println(p);
        }
    }
        
    }
    
   
    public List<Product> viewProductsReport() {
    String targetSeller = this.email;
    int maxPiecesSold = 0;
    double maxRevenue = 0;
    List<Product> products = new ArrayList<>();
    Product maxSoldProduct = null;
    Product maxRevenueProduct = null;

    System.out.println("\n=== Seller Products Report for " + this.email + " ===");
    // Read all products and filter by the current seller
    try (Scanner scanner = new Scanner(new File(SellerScene.PRODUCTS_FILE_PATH))) {
        if (scanner.hasNextLine()) scanner.nextLine(); // Skip header line

        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            
            // Assuming the CSV columns are:
            // 0 - ID, 1 - Name, 2 - Seller, 3 - Price, 4 - Sold Pieces, 5 - Revenue
            String seller = data[2];

            // If the product belongs to the current seller
            if (seller.equals(targetSeller)) {
                int numOfSoldPieces = Integer.parseInt(data[4]);
                double productRevenue = Double.parseDouble(data[5]);

                // Display each product's pieces sold
                System.out.println(data[1] + ": " + numOfSoldPieces + " pieces sold, Revenue: $" + productRevenue);

                // Create the product object
                Product product = new Product(Integer.parseInt(data[0]), data[1], seller, Double.parseDouble(data[3]));
                product.setNumOfSoldPieces(numOfSoldPieces);
                product.setRevenue(productRevenue);

                // Track the maximum sold product
                if (numOfSoldPieces > maxPiecesSold) {
                    maxPiecesSold = numOfSoldPieces;
                    maxSoldProduct = product;
                }

                // Track the maximum revenue product
                if (productRevenue > maxRevenue) {
                    maxRevenue = productRevenue;
                    maxRevenueProduct = product;
                }
            }
        }
        products.add(maxSoldProduct);
        products.add(maxRevenueProduct);
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Display the max products
    if (maxSoldProduct != null) {
        System.out.println("Max Sold Product: " + maxSoldProduct.getName() + " - " + maxSoldProduct.getNumOfSoldPieces() + " pieces");
    } else {
        System.out.println("No products found for the seller.");
    }

    if (maxRevenueProduct != null) {
        System.out.println("Max Revenue Product: " + maxRevenueProduct.getName() + " - $" + maxRevenueProduct.getRevenue());
    } else {
        System.out.println("No revenue data found for the seller.");
    }
     return products;
}
    
    public double viewOrdersReports(){
        String targetSeller = this.email;
        
        try (Scanner scanner = new Scanner(new File(SellerScene.ORDERS_FILE_PATH))) {
        if (scanner.hasNextLine()) scanner.nextLine(); // Skip header line

        System.out.println("List of orders:");
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            // 0-id 1-seller,2-payment,3-status,4-date,5-customer,6-address
             String seller = data[1];
             
            if (seller.equals(targetSeller)) {
                System.out.println("Order To " + data[5] + " [" + data[6] + "] on " + data[4] + ":");
                System.out.println("COD: $ " + data[2]);
                System.out.println("STATUS: " + data[3]);
                revenue += Double.parseDouble(data[2]);
            }
        }
            return revenue;
    
}catch (IOException e) {
        e.printStackTrace();
    }
        return -1;      
    }
    
    public void changeOrderStatus(int id){
        List<Order> orders = new ArrayList<>();
        boolean isUpdated = false;
        
        try (Scanner scanner = new Scanner(new File(SellerScene.ORDERS_FILE_PATH))) {
        if (scanner.hasNextLine()) scanner.nextLine(); // Skip header line
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            // 0-id 1-seller,2-payment,3-status,4-date,5-customer,6-address
             String seller = data[0];
             if (id == Integer.parseInt(data[0])) {
                 String newStatus = data[3].equals("pending") ? "shipped" : "pending";
                 Order updated = new Order(id, data[1], Double.parseDouble(data[2]), newStatus, data[4], data[5], data[6]);
                 orders.add(updated);
                 isUpdated = true;
                 
             }else{
                 Order order = new Order(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), data[3], data[4], data[5], data[6]);
                 orders.add(order);
             }
        }
    
}catch (IOException e) {
        e.printStackTrace();
    }
     // Rewwrite csv file
     if (isUpdated){
     try (PrintWriter writer = new PrintWriter(new FileWriter(SellerScene.ORDERS_FILE_PATH))) {
            writer.println(SellerScene.ORDERS_HEADER); // Add header back
            for (Order p : orders) {
                writer.println(p.getId() + "," + p.getSeller() + "," + p.getPayment() + "," + p.getStatus() + "," + p.getDate() + "," + p.getCustomer() + "," + p.getCustomerAddress());
            }
            System.out.println("Order with ID " + id + " edited successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    }
    
    public List<Product> getSellerProducts() {
    List<Product> products = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(SellerScene.PRODUCTS_FILE_PATH))) {
        String line;
        br.readLine(); // Skip the header
        
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data[2].equals(this.getEmail())) {
                Product product = new Product(
                    Integer.parseInt(data[0]),    // id
                    data[1],                      // name
                    data[2],                      // seller
                    Double.parseDouble(data[3]),  // price
                    Integer.parseInt(data[4]),    // numOfSoldPieces
                    Double.parseDouble(data[5]),  // revenue
                    Integer.parseInt(data[6])     // quantity
                );
                products.add(product);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return products;
}
    
    public List<Order> getSellerPendingOrders() {
    List<Order> orders = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(SellerScene.ORDERS_FILE_PATH))) {
        String line;
        br.readLine(); // Skip the header
        
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data[1].equals(this.getEmail()) && data[3].equals("pending")) {
                Order order = new Order(
                    Integer.parseInt(data[0]),    // id
                    data[1],                      // seller
                    Double.parseDouble(data[2]),  // payment
                    data[3],                      // status
                    data[4],                      // date
                    data[5],                      // customer
                    data[6]                       // address
                );
                orders.add(order);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return orders;
}
    
}