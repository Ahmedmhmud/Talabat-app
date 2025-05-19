package talabatapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SellerScene  {
    public static final String PRODUCTS_FILE_PATH = "D:\\products.csv";
    public static final String PRODUCTS_HEADER = "id,name,seller,price,numOfSoldPieces,revenue,quantity";
    
    public static final String ORDERS_FILE_PATH = "D:\\orders.csv";
    public static final String ORDERS_HEADER = "id,seller,payment,status,date,customer,address";
    

    // Main container for changing content
    private StackPane contentArea = new StackPane();
    private final String talabatOrange = "#FF6F00";
    private final String talabatBlue = "#367FB1";
    private final String talabatDark = "#2C3E50";
    private final String talabatLight = "#ECF0F1";

    public Scene start(Stage primaryStage, Seller e1) {
        // Sidebar buttons
        Button productsButton = createMenuButton("My Products", "home.png");
        Button pendingOrdersButton = createMenuButton("Pending Orders", "orders.png");
        Button reportsButton = createMenuButton("Reports", "reports.png");
        Button logoutButton = createMenuButton("Logout", "logout.png");

        // === Sidebar Layout ===
        VBox sidebar = new VBox(8);
        sidebar.setStyle("-fx-background-color: white; -fx-padding: 16px;");
        sidebar.setPrefWidth(250);
        
        // Add separator between sidebar and content
        BorderPane root = new BorderPane();
        BorderPane.setMargin(sidebar, new Insets(0, 1, 0, 0)); // Right margin for separator line
        root.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;"); // Right border only

        // === Logo & Title ===
        ImageView logo = new ImageView(new Image("file:///C:/Users/Hatem%20Ayman/Desktop/talabat-seeklogo.jpeg"));
        logo.setFitWidth(140);
        logo.setFitHeight(30.8);        
        
        VBox header = new VBox(logo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 0, 30, 0));
        sidebar.getChildren().add(header);

        // Add buttons to sidebar
        sidebar.getChildren().addAll(productsButton, pendingOrdersButton, reportsButton, new Separator(), logoutButton);

        // === Main Content Area ===
        contentArea.setStyle("-fx-background-color: #f8f9fa;");
        contentArea.getChildren().add(createWelcomeView());

        // === Layout Manager ===
        root.setLeft(sidebar);
        root.setCenter(contentArea);

        // === Button Actions ===
        productsButton.setOnAction(e -> switchView(createHomeView(e1), talabatBlue));
        pendingOrdersButton.setOnAction(e -> switchView(createOrdersView(e1), talabatOrange));
        reportsButton.setOnAction(e -> switchView(createReportsView(e1), "#27AE60"));
        logoutButton.setOnAction(e -> {
        TalabatApp loginApp = new TalabatApp();
        try {
                contentArea.getChildren().clear();
                primaryStage.setMaximized(true);
                loginApp.start(primaryStage);  
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // === Scene setup ===
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        primaryStage.setTitle("Talabat Seller Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
   
        return scene;
    }

    private Button createMenuButton(String text, String iconName) {
        Button btn = new Button(text);
        btn.setPrefWidth(220);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setContentDisplay(ContentDisplay.LEFT);
        
        // Try to load icon
        try {
            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconName)));
            icon.setFitWidth(20);
            icon.setFitHeight(20);
            btn.setGraphic(icon);
            btn.setGraphicTextGap(15);
        } catch (Exception e) {
            System.out.println("Icon not found: " + iconName);
        }
        
        // Base style
        String buttonStyle = "-fx-background-color: transparent; " +
                           "-fx-text-fill: " + talabatDark + "; " +
                           "-fx-font-size: 14px; " +
                           "-fx-font-weight: bold; " +
                           "-fx-padding: 12px 16px; " +
                           "-fx-border-radius: 8px; " +
                           "-fx-background-radius: 8px;";
        
        // Hover style (Soft UI inspired)
        String buttonHoverStyle = "-fx-background-color: rgba(255, 111, 0, 0.1); " +
                                "-fx-text-fill: " + talabatOrange + "; " +
                                "-fx-effect: dropshadow(gaussian, rgba(255, 111, 0, 0.4), 10, 0.2, 0, 2);";
        
        // Active style
        String buttonActiveStyle = "-fx-background-color: rgba(255, 111, 0, 0.2); " +
                                 "-fx-text-fill: " + talabatOrange + ";";
        
        btn.setStyle(buttonStyle);
        
        // Hover effects
        btn.setOnMouseEntered(e -> btn.setStyle(buttonStyle + buttonHoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(buttonStyle));
        btn.setOnMousePressed(e -> btn.setStyle(buttonStyle + buttonActiveStyle));
        btn.setOnMouseReleased(e -> btn.setStyle(buttonStyle + buttonHoverStyle));
        
        return btn;
    }

    private void switchView(Region content, String accentColor) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

private VBox createWelcomeView() {
        Label welcome = new Label("Welcome to Talabat!");
        welcome.setStyle("-fx-text-fill: " + talabatDark + "; " +
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold;");
        
        
        VBox box = new VBox(welcome);
        box.setAlignment(Pos.CENTER);
        return box;
    }

public VBox createHomeView(Seller e1) {
    // === Title ===
    Text title = new Text("My Products");
    title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    // === TableView Setup ===
    TableView<Product> table = new TableView<>();
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    // === Define Columns ===
    TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Product, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
    priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Product, Integer> numOfSoldCol = new TableColumn<>("Sold Pieces");
    numOfSoldCol.setCellValueFactory(new PropertyValueFactory<>("numOfSoldPieces"));

    TableColumn<Product, Double> revenueCol = new TableColumn<>("Revenue");
    revenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));

    TableColumn<Product, Integer> quantityCol = new TableColumn<>("Quantity");
    quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    table.getColumns().addAll(idCol, nameCol, priceCol, numOfSoldCol, revenueCol, quantityCol);

    // === Load Seller's Products ===
    ObservableList<Product> products = FXCollections.observableArrayList(e1.getSellerProducts());
    table.setItems(products);

    // === Add Product Form ===
    TextField addNameField = new TextField();
    addNameField.setPromptText("Product Name");
    
    TextField addPriceField = new TextField();
    addPriceField.setPromptText("Price");
    
    TextField addQuantityField = new TextField();
    addQuantityField.setPromptText("Quantity");

    Button addButton = new Button("Add Product");
    addButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white;");

    HBox addFormLayout = new HBox(10, addNameField, addPriceField, addQuantityField, addButton);
    addFormLayout.setAlignment(Pos.CENTER);
    addFormLayout.setPadding(new Insets(10));

    // === Edit & Remove Buttons (Initially Hidden) ===
    Button editButton = new Button("Edit Product");
    editButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white;");
    editButton.setVisible(false);

    Button removeButton = new Button("Remove Product");
    removeButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white;");
    removeButton.setVisible(false);

    // === Form Fields for Edit (Initially Hidden) ===
    TextField editNameField = new TextField();
    editNameField.setPromptText("Product Name");
    editNameField.setVisible(false);

    TextField editPriceField = new TextField();
    editPriceField.setPromptText("Price");
    editPriceField.setVisible(false);

    TextField editQuantityField = new TextField();
    editQuantityField.setPromptText("Quantity");
    editQuantityField.setVisible(false);

    HBox editFormLayout = new HBox(10, editNameField, editPriceField, editQuantityField, editButton, removeButton);
    editFormLayout.setAlignment(Pos.CENTER);
    editFormLayout.setPadding(new Insets(10));

    // === Add Button Action ===
    addButton.setOnAction(event -> {
        try {
            String name = addNameField.getText();
            double price = Double.parseDouble(addPriceField.getText());
            int quantity = Integer.parseInt(addQuantityField.getText());

            Product newProduct = new Product(name, e1.getEmail(), price, quantity);
            e1.add(newProduct);
            table.getItems().add(newProduct);

            addNameField.clear();
            addPriceField.clear();
            addQuantityField.clear();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input Error");
            alert.setContentText("Please enter valid Price and Quantity");
            alert.showAndWait();
        }
    });
    
    table.setRowFactory(tv -> {
    TableRow<Product> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (row.isEmpty()) {
            // Hide the controls you want when empty space is clicked
            editButton.setVisible(false);
            removeButton.setVisible(false);
            editNameField.setVisible(false);
            editPriceField.setVisible(false);
            editQuantityField.setVisible(false);
            
            // Clear the table selection
            table.getSelectionModel().clearSelection();
        }
        
    });
    return row;
    });

    // === Table Row Selection Logic ===
    table.setOnMouseClicked(event -> {
        Product selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            editNameField.setText(selectedProduct.getName());
            editPriceField.setText(String.valueOf(selectedProduct.getPrice()));
            editQuantityField.setText(String.valueOf(selectedProduct.getQuantity()));
            editButton.setVisible(true);
            removeButton.setVisible(true);
            editNameField.setVisible(true);
            editPriceField.setVisible(true);
            editQuantityField.setVisible(true);
        } 
    });
    

    // === Edit Button Logic ===
    editButton.setOnAction(event -> {
        handleEdit(table, editNameField, editPriceField, editQuantityField, e1);
        table.getSelectionModel().clearSelection();
        editButton.setVisible(false);
            removeButton.setVisible(false);
            editNameField.setVisible(false);
            editPriceField.setVisible(false);
            editQuantityField.setVisible(false);
            });

    // === Remove Button Logic ===
    removeButton.setOnAction(event -> {
        handleRemove(table, e1);
        table.getSelectionModel().clearSelection();
        editButton.setVisible(false);
            removeButton.setVisible(false);
            editNameField.setVisible(false);
            editPriceField.setVisible(false);
            editQuantityField.setVisible(false);

    });
    

    // === Layout ===
    VBox layout = new VBox(15, title, table, addFormLayout, editFormLayout);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.TOP_CENTER);
    layout.setStyle("-fx-background-color: #ffffff;");

    return layout;
}

private void handleEdit(TableView<Product> table, TextField editNameField, TextField editPriceField, TextField editQuantityField, Seller e1) {
    try {
        Product selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            String newName = editNameField.getText();
            double newPrice = Double.parseDouble(editPriceField.getText());
            int newQuantity = Integer.parseInt(editQuantityField.getText());

            Product updatedProduct = new Product(selectedProduct.getId(), newName, e1.getEmail(), newPrice, selectedProduct.getNumOfSoldPieces(), selectedProduct.getRevenue(), newQuantity);
            e1.edit(updatedProduct);

            table.getItems().set(table.getSelectionModel().getSelectedIndex(), updatedProduct);
        }
    } catch (NumberFormatException ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Input Error");
        alert.setContentText("Please enter valid values.");
        alert.showAndWait();
    }
}

private void handleRemove(TableView<Product> table, Seller e1) {
    Product selectedProduct = table.getSelectionModel().getSelectedItem();
    if (selectedProduct != null) {
        e1.remove(selectedProduct.getId());
        table.getItems().remove(selectedProduct);
    }
}
public VBox createOrdersView(Seller e1) {
    // === Title ===
    Text title = new Text("Pending Orders");
    title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    
    // === TableView Setup ===
    TableView<Order> table = new TableView<>();
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<Order, Integer> idCol = new TableColumn<>("ID");
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Order, String> customerCol = new TableColumn<>("Customer");
    customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));

    TableColumn<Order, String> addressCol = new TableColumn<>("Address");
    addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

    TableColumn<Order, String> statusCol = new TableColumn<>("Status");
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    TableColumn<Order, Double> paymentCol = new TableColumn<>("Payment");
    paymentCol.setCellValueFactory(new PropertyValueFactory<>("payment"));

    TableColumn<Order, String> dateCol = new TableColumn<>("Date");
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    table.getColumns().addAll(idCol, customerCol, addressCol, statusCol, paymentCol, dateCol);
    ObservableList<Order> orders = FXCollections.observableArrayList(e1.getSellerPendingOrders());
    table.setItems(orders);

    // === Change Status Button ===
    Button changeStatusButton = new Button("Mark as Shipped");
    changeStatusButton.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
    changeStatusButton.setVisible(false);

    table.setOnMouseClicked(event -> {
        Order selectedOrder = table.getSelectionModel().getSelectedItem();
        changeStatusButton.setVisible(selectedOrder != null);
    });

    changeStatusButton.setOnAction(event -> {
    Order selectedOrder = table.getSelectionModel().getSelectedItem();
    if (selectedOrder != null) {
        // Call the method to change the status in the file
        e1.changeOrderStatus(selectedOrder.getId());
        
        // Update the status in the local list as well
        selectedOrder.setStatus("shipped");
        
        // Refresh the table
        table.refresh();
        
        // Remove the order from the list as it's no longer "pending"
        orders.remove(selectedOrder);
    }
});
    table.setRowFactory(tv -> {
    TableRow<Order> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (row.isEmpty()) {
            // Hide the controls you want when empty space is clicked
            
            changeStatusButton.setVisible(false);

            // Clear the table selection
            table.getSelectionModel().clearSelection();
        }
        
    });
    return row;
    });


    VBox layout = new VBox(15, title, table, changeStatusButton);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.TOP_CENTER);

    return layout;
}
public VBox createReportsView(Seller e1) {
    // === Title ===
    Text title = new Text("Seller Reports");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    title.setFill(Color.web("#34495e"));
    title.setStyle("-fx-padding: 10px;");

    // === Product Statistics Panel ===
    List<Product> products = e1.viewProductsReport();  // Using your method to get the products
    Product maxSoldProduct = products.get(0);  // max sold product
    Product maxRevenueProduct = products.get(1);  // max revenue product

    VBox productPanel = createStatisticsPanel(
            "Max Sold Product",
            "Product: " + maxSoldProduct.getName(),
            "Sold: " + maxSoldProduct.getNumOfSoldPieces());

    VBox revenuePanel = createStatisticsPanel(
            "Max Revenue Product",
            "Product: " + maxRevenueProduct.getName(),
            "Revenue: $" + maxRevenueProduct.getRevenue());

    // === Revenue Panel ===
    double totalRevenue = e1.viewOrdersReports();  // Using your method to get total revenue
    VBox totalRevenuePanel = createStatisticsPanel(
            "Total Revenue",
            "Total Revenue: $" + totalRevenue,
            "");

    // === Layout ===
    VBox layout = new VBox(20, title, productPanel, revenuePanel, totalRevenuePanel);
    layout.setAlignment(Pos.TOP_CENTER);
    layout.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 30px;");

    return layout;
}

// Helper method to create a fancy panel
private VBox createStatisticsPanel(String title, String stat1, String stat2) {
    VBox panel = new VBox(10);
    panel.setAlignment(Pos.CENTER);
    panel.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-radius: 15px; -fx-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);");

    // Title
    Text titleText = new Text(title);
    titleText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    titleText.setFill(Color.web("#2980b9"));

    // First statistic
    Text stat1Text = new Text(stat1);
    stat1Text.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
    stat1Text.setFill(Color.web("#2c3e50"));

    // Second statistic (optional)
    Text stat2Text = new Text(stat2);
    stat2Text.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
    stat2Text.setFill(Color.web("#2c3e50"));

    // Add to panel
    panel.getChildren().addAll(titleText, stat1Text, stat2Text);
    return panel;
}

}