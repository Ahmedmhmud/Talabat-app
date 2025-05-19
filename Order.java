package talabatapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;


public class Order {
    private int id;
    private double payment;
    private String status;
    private String customer;
    private String seller;
    private String date;
    private String customerAddress;
    private List<Product> products;
    private List<Integer> quantities;
    private static List<Order> orders=new ArrayList<>();
    
    public Order(){
      this.products=new ArrayList<>();
      this.quantities=new ArrayList<>();
       orders.add(this);
    }
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Order(int id, String seller,double payment, String status, String date, String customer, String customerAddress) {
        this.id = id;
        this.payment = payment;
        this.status = status;
        this.customer =customer;
        this.seller=seller;
        this.date = date;
        this.customerAddress = customerAddress;    
        this.products=new ArrayList<>();
        this.quantities=new ArrayList<>(); 
         orders.add(this);
    }

    
    
    public Order(String seller,double payment, String status, String customer, String customerAddress) {
        this.id = this.hashCode();
        this.payment = payment;
        this.status = status;
        this.customer =customer;
        this.seller=seller;
        Date date = new Date();
        this.date=date.toString();
        this.customerAddress = customerAddress;    
        this.products=new ArrayList<>();
        this.quantities=new ArrayList<>(); 
         orders.add(this);
    }

    public int getId() {
        return id;
    }
    public double getPayment() { 
        return payment;
    }
    public void setPayment(double payment) { 
        this.payment = payment; 
    }

    public String getStatus() {
        return status; 
    }
    public void setStatus(String status) {
        this.status = status; 
    }

    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) { 
        this.customer = customer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerAddress() { 
        return customerAddress;
    }
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    
    @Override
    public String toString(){
        return "Order{"+"Products:"+products+",Quantities:"+quantities+"}";
    }
    public void addProductToCart(Product product,int quantity){
        if(product.getQuantity()>= quantity)
            product.testQuantity(quantity);
        products.add(product);
        quantities.add(quantity); 
        
    }
    public double getTotalPrice(){
        double total=0;
        for(int i=0;i<products.size();i++)
            total += products.get(i).getPrice()*quantities.get(i);
        return total;
    }
    public void saveOrder(String filepath) {
    try (FileWriter writer = new FileWriter(filepath, true)) {
        writer.append(id+","+seller+","+payment + "," + status + "," + date + "," + customer + "," + customerAddress );
    } catch (IOException e) {
        System.out.println("file not found");
    }
    
    }
}