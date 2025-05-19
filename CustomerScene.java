package talabatapp;

import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import static talabatapp.AdminScene.contentArea;

public class CustomerScene {
    public String email;

    private static class Restaurant {
        private final String name;
        private final String email;
        private final List<MenuItem> menuItems = new ArrayList<>();

        public Restaurant(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public void addMenuItem(MenuItem item) {
            menuItems.add(item);
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public List<MenuItem> getMenuItems() { return menuItems; }
    }

    private static class MenuItem {
        private final int id;
        private final String name;
        private final double price;
        private int quantity;

        public MenuItem(int id, String name, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    private static class CartItem {
        private final int itemId;
        private final int quantity;

        public CartItem(int itemId, int quantity) {
            this.itemId = itemId;
            this.quantity = quantity;
        }

        public int getItemId() { return itemId; }
        public int getQuantity() { return quantity; }
    }

    private final List<Restaurant> restaurants = new ArrayList<>();
    private final List<Integer> cart = new ArrayList<>();
    private Button cartButton;
    private static final String CSV_PATH = "D:\\products.csv";

    public CustomerScene() {
        loadRestaurantsFromCsv();
    }
    
    public CustomerScene(String email) {
        this.email = email;
        loadRestaurantsFromCsv();
    }
    

    private void loadRestaurantsFromCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            br.readLine(); // Skip header
            String line;
            Map<String, Restaurant> restaurantMap = new HashMap<>();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 7) continue;

                int id = Integer.parseInt(values[0].trim());
                String itemName = values[1].trim();
                String email = values[2].trim();
                double price = Double.parseDouble(values[3].trim());
                int quantity = Integer.parseInt(values[6].trim());

                String restaurantName = email.split("@")[0].replace(".", " ");
                restaurantName = restaurantName.substring(0, 1).toUpperCase() + restaurantName.substring(1);

                Restaurant restaurant = restaurantMap.get(email);
                if (restaurant == null) {
                 restaurant = new Restaurant(restaurantName, email);
                   restaurantMap.put(email, restaurant);
                 }
                restaurant.addMenuItem(new MenuItem(id, itemName, price, quantity));
            }
            restaurants.addAll(restaurantMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scene getMainScene(Stage stage) {
        // Header
        HBox header = new HBox(20);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0;");
         
        ImageView palestineFlag = new ImageView(new Image("file:/D:/pal.png"));
        palestineFlag.setFitHeight(20);
        palestineFlag.setPreserveRatio(true);
        
        ImageView logo = new ImageView(new Image("file:/D:/talabat.png"));
        logo.setFitHeight(30);
        logo.setPreserveRatio(true);
        
        Button btlogout = new Button ("LogOut");
        btlogout.setOnAction(e -> {
        TalabatApp loginApp = new TalabatApp();
        try {
            contentArea.getChildren().clear();
            stage.setMaximized(true);
            loginApp.start(stage);  
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        cartButton = new Button("🛒 Cart");
        cartButton.setOnAction(e -> showCartScene(stage));

        header.getChildren().addAll(logo, spacer, palestineFlag  , cartButton, btlogout);

        // Title and Search
        Label title = new Label("All Restaurants");
        title.setFont(Font.font("Arial", 24));
        title.setPadding(new Insets(10, 0, 0, 10));
        
        TextField searchField = new TextField();
        searchField.setPromptText("Search restaurants...");
        Button searchButton = new Button("🔍");
        HBox searchBar = new HBox(10, searchField, searchButton);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(10));

        // Restaurant Grid
        TilePane restaurantGrid = new TilePane();
        restaurantGrid.setPadding(new Insets(10));
        restaurantGrid.setHgap(20);
        restaurantGrid.setVgap(20);
        restaurantGrid.setPrefColumns(4);

        for (Restaurant r : restaurants) {
            restaurantGrid.getChildren().add(createRestaurantCard(r, stage));
        }

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            restaurantGrid.getChildren().clear();
            restaurants.stream()
                .filter(r -> r.getName().toLowerCase().contains(query))
                .forEach(r -> restaurantGrid.getChildren().add(createRestaurantCard(r, stage)));
        });

        VBox root = new VBox(10, header, title, searchBar, restaurantGrid);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    }

    private VBox createRestaurantCard(Restaurant restaurant, Stage stage) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 10;");

        Circle imagePlaceholder = new Circle(30, Color.LIGHTGRAY);
        Text initial = new Text(restaurant.getName().substring(0, 1));
        StackPane logo = new StackPane(imagePlaceholder, initial);

        Label nameLabel = new Label(restaurant.getName());
        nameLabel.setFont(Font.font(14));

        Label itemsLabel = new Label(restaurant.getMenuItems().size() + " items");
        itemsLabel.setStyle("-fx-text-fill: gray;");

        card.getChildren().addAll(logo, nameLabel, itemsLabel);

        // Hover effects
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #999;");
            ScaleTransition st = new ScaleTransition(Duration.millis(100), card);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color: white; -fx-border-color: #ccc;");
            ScaleTransition st = new ScaleTransition(Duration.millis(100), card);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        card.setOnMouseClicked(e -> showMenuScene(stage, restaurant));

        return card;
    }

    private void showMenuScene(Stage stage, Restaurant restaurant) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));

        Label title = new Label(restaurant.getName() + " Menu");
        title.setFont(Font.font(24));

        TableView<MenuItem> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        
        TableColumn<MenuItem, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%.2f", c.getValue().getPrice())));

        TableColumn<MenuItem, String> qtyCol = new TableColumn<>("In Stock");
        qtyCol.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getQuantity())));

        TableColumn<MenuItem, Void> actionCol = new TableColumn<>("Add");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button addBtn = new Button("+");
            {
                addBtn.setOnAction(e -> {
                  MenuItem item = getTableView().getItems().get(getIndex());
                  if (item.getQuantity() > 0) {
                  item.setQuantity(item.getQuantity() - 1);  
                  cart.add(item.getId());
                  updateCartButton();
                  getTableView().refresh();  
                 }
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                setGraphic(empty ? null : addBtn);
            }
        });

        table.getColumns().addAll(nameCol, priceCol, qtyCol, actionCol);
        table.getItems().addAll(restaurant.getMenuItems());

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> stage.setScene(getMainScene(stage)));

        layout.getChildren().addAll(title, table, backBtn);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setScene(new Scene(layout, screenBounds.getWidth(), screenBounds.getHeight()));
    }

    private void showCartScene(Stage stage) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));

        Label title = new Label("Your Cart");
        title.setFont(Font.font(24));

        // Count item quantities
        Map<Integer, Integer> itemCounts = new HashMap<>();
        for (int itemId : cart) {
            itemCounts.put(itemId, itemCounts.getOrDefault(itemId, 0) + 1);
        }

        // Create cart items list
        List<CartItem> cartItems = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : itemCounts.entrySet()) {
            cartItems.add(new CartItem(entry.getKey(), entry.getValue()));
        }

        // Create table
        TableView<CartItem> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<CartItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(c -> {
            MenuItem item = findItemById(c.getValue().getItemId());
            return new SimpleStringProperty(item != null ? item.getName() : "Unknown");
        });

        TableColumn<CartItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()).asObject());

        TableColumn<CartItem, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(c -> {
            MenuItem item = findItemById(c.getValue().getItemId());
            return new SimpleStringProperty(item != null ? String.format("$%.2f", item.getPrice()) : "$0.00");
        });

        TableColumn<CartItem, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(c -> {
            MenuItem item = findItemById(c.getValue().getItemId());
            double total = item != null ? item.getPrice() * c.getValue().getQuantity() : 0;
            return new SimpleStringProperty(String.format("$%.2f", total));
        });

        TableColumn<CartItem, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final HBox box = new HBox(5);
            private final Button plus = new Button("+");
            private final Button minus = new Button("-");
            {
                plus.setOnAction(e -> {
                 CartItem cartItem = getTableView().getItems().get(getIndex());
                 MenuItem menuItem = findItemById(cartItem.getItemId());
                 if (menuItem != null && menuItem.getQuantity() > 0) {
                cart.add(cartItem.getItemId());
                menuItem.setQuantity(menuItem.getQuantity() - 1);
                showCartScene(stage);
                }
               });
                minus.setOnAction(e -> {
                 CartItem cartItem = getTableView().getItems().get(getIndex());
                  MenuItem menuItem = findItemById(cartItem.getItemId());
                 if (menuItem != null) {
                cart.remove(Integer.valueOf(cartItem.getItemId()));
                menuItem.setQuantity(menuItem.getQuantity() + 1);
                showCartScene(stage);
                }
                });

                box.getChildren().addAll(plus, minus);
            }
            @Override protected void updateItem(Void item, boolean empty) {
                setGraphic(empty ? null : box);
            }
        });

        table.getColumns().addAll(nameCol, qtyCol, priceCol, totalCol, actionCol);
        table.getItems().addAll(cartItems);

        // Calculate total
        double total = cartItems.stream()
            .mapToDouble(ci -> {
                MenuItem item = findItemById(ci.getItemId());
                return item != null ? item.getPrice() * ci.getQuantity() : 0;
            })
            .sum();

        Label totalLabel = new Label("Total: $" + String.format("%.2f", total));
        totalLabel.setFont(Font.font(18));

        Button checkoutBtn = new Button("Checkout");
checkoutBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
checkoutBtn.setOnAction(e -> {
    try {
        // Create order details
        int orderId = this.hashCode() % 10000;
        String date = new Date().toString();
        String customerAddress = "zagzig";
        
        // Properly calculate total order price with quantities
        Map<Integer, Integer> itemCountss = new HashMap<>();
        for (int itemId : cart) {
            itemCountss.put(itemId, itemCountss.getOrDefault(itemId, 0) + 1);
        }

        double totalPrice = 0.0;
        for (Map.Entry<Integer, Integer> entry : itemCountss.entrySet()) {
            MenuItem item = findItemById(entry.getKey());
            if (item != null) {
                totalPrice += item.getPrice() * entry.getValue();
            }
        }

        // Get unique restaurant emails
        Set<String> restaurantEmails = new HashSet<>();
        for (int itemId : cart) {
            MenuItem item = findItemById(itemId);
            if (item != null) {
                for (Restaurant r : restaurants) {
                    if (r.getMenuItems().contains(item)) {
                        restaurantEmails.add(r.getEmail());
                        break;
                    }
                }
            }
        }

        // Write to CSV with UTF-8 encoding
        File ordersFile = new File("D:\\orders.csv");
        boolean fileExists = ordersFile.exists();

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(ordersFile, true),
                        StandardCharsets.UTF_8))) {

            // Write UTF-8 BOM and headers if file is new
            if (!fileExists) {
                writer.write("\uFEFF");
                writer.write("id,seller,payment,status,date,customer,address");
                writer.newLine();
            }

            // Write order data
            for (String restaurantEmail : restaurantEmails) {
                String line = String.join(",",
                        String.valueOf(orderId),
                        restaurantEmail,
                        String.format(Locale.US, "%.2f", totalPrice),  // << Corrected format here
                        "pending",
                        date,
                        email,
                        customerAddress
                );
                writer.write(line);
                writer.newLine();
            }

            // Success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Placed");
            alert.setHeaderText(null);
            alert.setContentText("Your order has been placed successfully!");
            alert.showAndWait();

            // Clear cart and return to main scene
            cart.clear();
            updateCartButton();
            stage.setScene(getMainScene(stage));

        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to place order");
            alert.setContentText("Error: " + ex.getMessage());
            alert.showAndWait();
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }
});


        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> stage.setScene(getMainScene(stage)));

        HBox buttons = new HBox(10, checkoutBtn, backBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        layout.getChildren().addAll(title, table, totalLabel, buttons);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setScene(new Scene(layout, screenBounds.getWidth(), screenBounds.getHeight()));
    }

    private MenuItem findItemById(int id) {
        return restaurants.stream()
            .flatMap(r -> r.getMenuItems().stream())
            .filter(item -> item.getId() == id)
            .findFirst()
            .orElse(null);
    }

    private void updateCartButton() {
        cartButton.setText("🛒 Cart");
    }
}