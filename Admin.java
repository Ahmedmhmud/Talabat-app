package talabatapp;

import java.io.*;
import java.util.*;

public class Admin extends User implements Managable {
    private ArrayList<Customer> customers;
    private ArrayList<Seller> sellers;
    private ArrayList<User> users;

    private static final String FILE_PATH = "D:\\Users.txt";

    public Admin() {
        super("", "", "", UserRole.ADMIN);
        this.customers = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.users = new ArrayList<>();
        loadUsersFromFile();
    }
    
    public Admin(String email, String password, UserRole role){
        super(email, password, role);
        this.customers = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.users = new ArrayList<>();
        loadUsersFromFile();
    }

    public Admin(String name, String email, String password, UserRole role) {
        super(name, email, password, role);
        this.customers = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.users = new ArrayList<>();
        loadUsersFromFile();
    }

    // تحميل المستخدمين من الملف
    private void loadUsersFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (ArrayList<User>) in.readObject();
            for (User user : users) {
                if (user instanceof Customer)
                    customers.add((Customer) user);
                else if (user instanceof Seller)
                    sellers.add((Seller) user);
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(" Users file does not exist , It will be created on first save, users file does not exist");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // حفظ المستخدمين في الملف
    private void saveUsersToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(users);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            users.add(user);

            if (user instanceof Customer)
                customers.add((Customer) user);
            else if (user instanceof Seller)
                sellers.add((Seller) user);

            saveUsersToFile();
            System.out.println("User added : " + user.getName());
        }
    }

    @Override
    public void edit(Object obj) {
        if (obj instanceof User) {
            User updatedUser = (User) obj;
            boolean found = false;

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getEmail().equals(updatedUser.getEmail())) {
                    users.set(i, updatedUser);
                    found = true;
                    break;
                }
            }

            if (found) {
                saveUsersToFile();
                System.out.println("User edited : " + updatedUser.getName());
            } else {
                System.out.println("User not found .");
            }
        }
    }

    @Override
    public void remove(Object obj) {
        if (obj instanceof User) {
            User userToRemove = (User) obj;
            users.removeIf(user -> user.getEmail().equals(userToRemove.getEmail()));
            customers.removeIf(c -> c.getEmail().equals(userToRemove.getEmail()));
            sellers.removeIf(s -> s.getEmail().equals(userToRemove.getEmail()));
            saveUsersToFile();
            System.out.println("User removed : " + userToRemove.getName());
        }
    }

    @Override
    public void search(Object obj) {
        String nameToSearch = (String) obj;
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(nameToSearch)) {
                System.out.println("User found : " + user.getName());
                return;
            }
        }
        System.out.println("User not found .");
    }

    public void viewUserReports() {
        System.out.println("===== User reports =====");
        for (User user : users) {
            System.out.println("Name : " + user.getName() + " | Email: " + user.getEmail() + " | Role: " + user.getRole());
        }
    }

    public List<Order> viewOrderReports() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SellerScene.ORDERS_FILE_PATH))) {
        String line;
        br.readLine(); // Skip the header
        
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
                Order order = new Order(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), data[3], data[4], data[5], data[6]);
                orders.add(order);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return orders;
    }

    public void viewRevenueReport(List<Order> orders) {
        RevenueReport revenueReport = new RevenueReport();
        double totalRevenue = revenueReport.calculateTotalRevenue(orders);
        System.out.println("Total Revenue: " + totalRevenue);
    }

    public void viewRevenuePerSeller(List<Order> orders) {
        RevenueReport revenueReport = new RevenueReport();
        revenueReport.revenuePerSeller(orders);
    }

    private class RevenueReport {
        public double calculateTotalRevenue(List<Order> orders) {
            double total = 0;
            for (Order order : orders) {
                total += order.getPayment();
            }
            return total;
        }

        public void revenuePerSeller(List<Order> orders) {
            Map<String, Double> sellerRevenue = new HashMap<>();
            for (Order order : orders) {
                String seller = order.getSeller();
                double payment = order.getPayment();
                sellerRevenue.put(seller, sellerRevenue.getOrDefault(seller, 0.0) + payment);
            }

            System.out.println("Revenue Per Seller:");
            for (Map.Entry<String, Double> entry : sellerRevenue.entrySet()) {
                System.out.println("Seller: " + entry.getKey() + " | Revenue: " + entry.getValue());
            }
        }
    }
    public ArrayList<User> getAllUsers() {
        return users;
    }
}