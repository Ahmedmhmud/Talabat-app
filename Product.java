package talabatapp;

import java.io.FileWriter;
import java.io.IOException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Product {
    private int id;
    private String name;
    private String seller;
    private double price;
    private int numOfSoldPieces;
    private double revenue;
    private int quantity;
    private IntegerProperty rating = new SimpleIntegerProperty(this, "rating", 4);

    public Product(int id, String name, String seller, double price, int numOfSoldPieces, double revenue, int quantity) {
        this.id = id;
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.numOfSoldPieces = numOfSoldPieces;
        this.revenue = revenue;
        this.quantity = quantity;
        this.rating.set(4);
    }


    public Product(String name, String seller, double price, int quantity) {
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.quantity = quantity;
        this.id = this.hashCode();
        this.rating.set(4);
    }
    
    public Product(int id, String name, String seller, double price) {
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.id = id;
        this.rating.set(4);
    }


    public String getName() {
        return name;
    }

    public String getSeller() {
        return seller;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumOfSoldPieces(int numOfSoldPieces) {
        this.numOfSoldPieces = numOfSoldPieces;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public int getNumOfSoldPieces() {
        return numOfSoldPieces;
    }

    public double getRevenue() {
        return revenue;
    }
    
   public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int Quantity) {
        this.quantity = Quantity;
    }

    public int getRating() {
        return rating.get();
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }
    
      public IntegerProperty ratingProperty() {
        return rating;
    }

    
    

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", seller=" + seller + ", price=" + price + ", rating="+ rating +'}';
    }


    public void testQuantity(int quantity){
        if(this.quantity >= quantity)
            this.quantity -= quantity;
        else 
            System.out.println("Sorry,quantity is not enough for"+name);
    }
    
    public void saveProduct(String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {  
            writer.append(name + "," + id + "," + price + "," + quantity + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the product.");
        }
    }
    

    
  
}