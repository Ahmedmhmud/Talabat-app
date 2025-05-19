package talabatapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminScene {
    public static StackPane contentArea = new StackPane();
    private final String talabatOrange = "#FF6F00";
    private static final String talabatDark = "#2C3E50";
    public static Scene getScene(Stage stage , Admin admin) {
        Button btviewuser = new Button("View Users");
        Button btadd = new Button("Add User");
        Button btedit = new Button ("Edit User");
        Button btremove = new Button ("Remove User");
        Button btsearch = new Button ("Search User");
        Button btvieworder = new Button ("View Order");
        Button btviewrevenue = new Button ("View Revenue");
        Button btlogout = new Button ("LogOut");
        btviewuser.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btadd.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btedit.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btremove.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btvieworder.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btviewrevenue.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btlogout.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btsearch.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btlogout.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btadd.setMaxWidth(200);
        btedit.setMaxWidth(200);
        btremove.setMaxWidth(200);
        btsearch.setMaxWidth(200);
        btviewuser.setMaxWidth(200);
        btvieworder.setMaxWidth(200);
        btviewrevenue.setMaxWidth(200);
        btlogout.setMaxWidth(200);
        
        VBox vertical1 = new VBox(10);
        vertical1.setPadding(new Insets(20));
        vertical1.setAlignment(Pos.CENTER);
        vertical1.setStyle("-fx-background-color: white; -fx-padding: 16px;");
        vertical1.setPrefWidth(250);

        BorderPane panes = new BorderPane();
        BorderPane.setMargin(vertical1, new Insets(0, 1, 0, 0)); // Right margin for separator line
        panes.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;"); // Right border only
        // === Logo & Title ===
        ImageView logo = new ImageView(new Image("file:///C:/Users/DELL/Desktop/talabatlogo.jpg"));
        logo.setFitWidth(140);
        logo.setFitHeight(30.8);
        VBox header = new VBox(logo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 0, 30, 0));
        vertical1.getChildren().add(header);
        vertical1.getChildren().addAll(btadd,btedit,btremove,btsearch,btviewuser,btvieworder,btviewrevenue,btlogout);
        contentArea.setStyle("-fx-background-color: #f8f9fa;");
        contentArea.getChildren().add(createWelcomeView());

        // === Layout Manager ===
        panes.setLeft(vertical1);
        panes.setCenter(contentArea);
        
        btadd.setOnAction(e ->{
            ComboBox<String> UserType = new ComboBox<>();
            UserType.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
            UserType.getItems().addAll("Admin","Seller","Customer");
            UserType.setPromptText("Select User Type");
            TextField txtname = new TextField();
            txtname.setPromptText("Enter Name");
            txtname.setMaxWidth(200);
            TextField txtemail = new TextField();
            txtemail.setPromptText("Enter Email");
            txtemail.setMaxWidth(200);
            PasswordField txtpass = new PasswordField();
            txtpass.setPromptText("Enter Password");
            txtpass.setMaxWidth(200);
            Button btconfirmadd = new Button("Add");
            Button backBtn = new Button("Back");
            Label statusLabel = new Label();
            Label resultLabel = new Label();
            VBox adduserform = new VBox(20);
            adduserform.setPadding(new Insets(20));
            adduserform.setAlignment(Pos.CENTER);
            adduserform.getChildren().addAll(UserType,txtname,txtemail,txtpass,btconfirmadd,backBtn,statusLabel);
            btconfirmadd.setMaxWidth(100);
            backBtn.setMaxWidth(100);
            backBtn.setOnAction(es -> {contentArea.getChildren().clear();
                contentArea.getChildren().add(createWelcomeView());});
            btconfirmadd.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
            backBtn.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
            Image bgImage = new Image("file:/D:\\project\\talabat.jpg");
            BackgroundImage backgroundImg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
            Pane overlay = new Pane();
            overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6);");
            overlay.setPrefSize(800, 600);

            StackPane root = new StackPane();
            root.setBackground(new Background(backgroundImg));
            root.getChildren().addAll(overlay, adduserform);
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);        
            btconfirmadd.setOnAction(ev ->{
                String type =UserType.getValue();
                String name =txtname.getText().trim();
                String email =txtemail.getText().trim();
                String password =txtpass.getText().trim();
                if(type ==null || name.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR ,"Please fill all fields and select user type.");
                    alert.show();
                    return ;
                }
                email = txtemail.getText().trim();
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(email);
        
            User foundUser = null;
            for (User u : admin.getAllUsers()) {
                if (u.getEmail().equals(email)) {
                    foundUser = u;
                    break;
                }
            }
            if (!matcher.matches()) {
               statusLabel.setText("Invalid email format. Please enter a valid email.");
            }
            else if (foundUser != null) {
              statusLabel.setText("User is already exist");  
            }
            else if (password.length() < 6) {
                statusLabel.setText("Password must be at least 6 characters.");
            }
            else if (foundUser == null) {
               switch(type){
                    case "Admin" :
                        admin.add(new Admin(name, email, password, UserRole.ADMIN));
                        break;
                    case "Seller" :
                        admin.add(new Seller(name, email, password, UserRole.SELLER));
                        break;
                    case "Customer" :
                        admin.add(new Customer(name, email, password, UserRole.CUSTOMER));
                        break;
                } 
            
               statusLabel.setText("User added successfully.");
            }
            
            
                
             
             
             
            });
        
        });
     btedit.setOnAction(e -> {
        
        TextField txtemail = new TextField();
        txtemail.setPromptText("Enter Email to Edit");
        txtemail.setMaxWidth(200);

        Button btnCheck = new Button("Check");
        Label resultLabel = new Label();

        VBox checkForm = new VBox(20);
        checkForm.setPadding(new Insets(20));
        checkForm.setAlignment(Pos.CENTER);
        Button backBtn = new Button("Back");
        checkForm.getChildren().addAll(txtemail, btnCheck,backBtn, resultLabel);
        btnCheck.setMaxWidth(100);
        backBtn.setMaxWidth(100);
        btnCheck.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        backBtn.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        Image bgImage = new Image("file:/D:\\project\\talabat.jpg");
        BackgroundImage backgroundImg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6);");
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImg));
        root.getChildren().addAll(overlay, checkForm);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(root);

        backBtn.setOnAction(es ->{
            contentArea.getChildren().clear();
            contentArea.getChildren().add(createWelcomeView());
        });
        btnCheck.setOnAction(ev -> {
            String email = txtemail.getText().trim();
            if (email.isEmpty()) {
                resultLabel.setText("Please enter an email.");
                return;
            }
                
        
            User foundUser = null;
            for (User u : admin.getAllUsers()) {
                if (u.getEmail().equals(email)) {
                    foundUser = u;
                    break;
                }
            }

            if (foundUser == null) {
                resultLabel.setText("User not found.");
            } else {
                final User finalFoundUser = foundUser; // إعلان متغير final

            
                TextField txtname = new TextField(finalFoundUser.getName());
                PasswordField txtpass = new PasswordField();
                txtpass.setMaxWidth(200);
                txtname.setMaxWidth(200);
                txtpass.setPromptText("Enter New Password");

                ComboBox<String> UserType = new ComboBox<>();
                UserType.getItems().addAll("Admin", "Seller", "Customer");
                UserType.setValue(finalFoundUser.getRole().toString());

                Button btnSave = new Button("Save");
                Button btback = new Button("Back");
                Label resultLabel2 = new Label();

                VBox editForm = new VBox(20);
                editForm.setPadding(new Insets(20));
                editForm.setAlignment(Pos.CENTER);
                editForm.getChildren().addAll(txtname, txtpass, UserType, btnSave,btback, resultLabel2);
                btnSave.setMaxWidth(100);
                btback.setMaxWidth(100);
                UserType.setMaxWidth(130);
                UserType.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
                btnSave.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
                btback.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
                Image bgImage1 = new Image("file:/D:\\project\\talabat.jpg");
                BackgroundImage backgroundImg1 = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
                Pane overlay1 = new Pane();
                overlay1.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6);");
                StackPane root1 = new StackPane();
                root1.setBackground(new Background(backgroundImg));
                root1.getChildren().addAll(overlay, editForm);
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root1);

                btback.setOnAction(es ->{
                    contentArea.getChildren().clear();
                    contentArea.getChildren().add(createWelcomeView());
                });

            
                btnSave.setOnAction(ev2 -> {
                    String name = txtname.getText().trim();
                    String pass = txtpass.getText().trim();
                    String role = UserType.getValue();

                    if (name.isEmpty() || pass.isEmpty() || role == null) {
                        resultLabel2.setText("Please fill all fields.");
                        return;
                    }

                
                    switch (role) {
                        case "Admin":
                            admin.edit(new Admin(name, finalFoundUser.getEmail(), pass, UserRole.ADMIN));
                            break;
                        case "Seller":
                            admin.edit(new Seller(name, finalFoundUser.getEmail(), pass, UserRole.SELLER));
                            break;
                        case "Customer":
                            admin.edit(new Customer(name, finalFoundUser.getEmail(), pass, UserRole.CUSTOMER));
                            break;
                    }

                    resultLabel2.setText("User edited successfully.");
                });
            }
        });
});

    btremove.setOnAction(e -> {
         
        Label lblTitle = new Label("Enter Email to Remove User");
        TextField txtemail = new TextField();
        txtemail.setMaxWidth(200);
        txtemail.setPromptText("Enter Email");

        Button btConfirmRemove = new Button("Remove");
        Button backBtn = new Button("Back");
        Label resultLabel = new Label();
        btConfirmRemove.setMaxWidth(100);
        backBtn.setMaxWidth(100);
        btConfirmRemove.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        backBtn.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");

        
        VBox removeForm = new VBox(20);
        removeForm.setPadding(new Insets(20));
        removeForm.setAlignment(Pos.CENTER);
        removeForm.getChildren().addAll(lblTitle, txtemail, btConfirmRemove,backBtn, resultLabel);
        backBtn.setOnAction(es -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(createWelcomeView());
        });
        Image bgImage = new Image("file:/D:\\project\\talabat.jpg");
        BackgroundImage backgroundImg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6);");
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImg));
        root.getChildren().addAll(overlay, removeForm);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(root);

        
        btConfirmRemove.setOnAction(ev -> {
            
            String email = txtemail.getText().trim();

            if (email.isEmpty()) {
                resultLabel.setText("Please enter an email.");
                return;
            }

            
            User foundUser = null;
            for (User u : admin.getAllUsers()) {
                if (u.getEmail().equals(email)) {
                    foundUser = u;
                    break;
                }
            }

            if (foundUser == null) {
                resultLabel.setText("User not found.");
            } else {
                admin.remove(foundUser); 
                resultLabel.setText("User removed successfully.");
            }
        });
    });

    btsearch.setOnAction(e -> {
    
        Label lblTitle = new Label("Enter Email to Search User");
        TextField txtemail = new TextField();
        txtemail.setMaxWidth(200);
        txtemail.setPromptText("Enter Email");

        Button btConfirmSearch = new Button("Search");
        Button backBtn = new Button("Back");
        btConfirmSearch.setMaxWidth(100);
        backBtn.setMaxWidth(100);
        btConfirmSearch.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        backBtn.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        backBtn.setOnAction(es -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(createWelcomeView());
        });
         
        Label resultLabel = new Label();

        VBox searchForm = new VBox(20);
        searchForm.setPadding(new Insets(20));
        searchForm.setAlignment(Pos.CENTER);
        searchForm.getChildren().addAll(lblTitle, txtemail, btConfirmSearch,backBtn, resultLabel);
        Image bgImage = new Image("file:/D:\\project\\talabat.jpg");
        BackgroundImage backgroundImg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6);");
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImg));
        root.getChildren().addAll(overlay, searchForm);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(root);
   
        btConfirmSearch.setOnAction(ev -> {
            String email = txtemail.getText().trim();

            if (email.isEmpty()) {
                resultLabel.setText("Please enter an email.");
                return;
            }

        
            User foundUser = null;
            for (User u : admin.getAllUsers()) {
                if (u.getEmail().equalsIgnoreCase(email)) {
                    foundUser = u;
                    break;
                }
            }
            Button backBtn1 = new Button("Back");
            backBtn1.setOnAction(es -> stage.setScene(getScene(stage, admin)));
            backBtn1.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
            backBtn1.setMaxWidth(100);
        
            if (foundUser == null) {
                 resultLabel.setText("User not found.");
            } else {
                resultLabel.setText("Name: " + foundUser.getName() +
                            "\nEmail: " + foundUser.getEmail() +
                            "\nRole: " + foundUser.getRole());
            }
        });
    });
    btviewuser.setOnAction(e -> {
        Label title = new Label("All Users");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        List<User> allUsers = admin.getAllUsers();

    
        VBox userListVBox = new VBox(10);
        userListVBox.setPadding(new Insets(20));
        userListVBox.setAlignment(Pos.TOP_LEFT);

    
        if (allUsers.isEmpty()) {
            userListVBox.getChildren().add(new Label("No users available."));
        } else {
            for (User u : allUsers) {
                Label userLabel = new Label("Name: " + u.getName() + 
                                    " | Email: " + u.getEmail() +
                                    " | Role: " + u.getRole());
                userLabel.setStyle("-fx-background-color: #F0F0F0; -fx-padding: 5;");
                userListVBox.getChildren().add(userLabel);
            }
        }

    
        ScrollPane scrollPane = new ScrollPane(userListVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

    
        Button backBtn = new Button("Back");
        backBtn.setMaxWidth(100);
        backBtn.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        backBtn.setOnAction(ev -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(createWelcomeView());
        });

    
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, scrollPane, backBtn);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(layout);
    
    });
    btvieworder.setOnAction(e -> {
    Label title = new Label("All Orders");
    title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    List<Order> orders = admin.viewOrderReports(); 

    TableView<Order> tableView = new TableView<>();
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<Order, Integer> idCol = new TableColumn<>("Order ID");
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Order, String> customerCol = new TableColumn<>("Customer");
    customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));

    TableColumn<Order, String> sellerCol = new TableColumn<>("Seller");
    sellerCol.setCellValueFactory(new PropertyValueFactory<>("seller"));

    TableColumn<Order, String> statusCol = new TableColumn<>("Status");
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    TableColumn<Order, String> paymentCol = new TableColumn<>("Payment");
    paymentCol.setCellValueFactory(new PropertyValueFactory<>("payment"));

    tableView.getColumns().addAll(idCol, customerCol, sellerCol, statusCol, paymentCol);

    if (orders != null && !orders.isEmpty()) {
        ObservableList<Order> orderData = FXCollections.observableArrayList(orders);
        tableView.setItems(orderData);
    } else {
        tableView.setPlaceholder(new Label("No orders available."));
    }

    tableView.setPrefHeight(400);

    Button backBtn = new Button("Back");
    backBtn.setMaxWidth(100);
    backBtn.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
    backBtn.setOnAction(ev -> {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(createWelcomeView());
    });

    VBox layout = new VBox(20, title, tableView, backBtn);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.CENTER);
    contentArea.getChildren().clear();
    contentArea.getChildren().add(layout);
});


    btviewrevenue.setOnAction(e -> {
        Label title = new Label("Revenue Report");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        List<Order> orders = admin.viewOrderReports(); // تأكد أن orders موجودة داخل admin

        VBox reportBox = new VBox(15);
        reportBox.setPadding(new Insets(20));
        reportBox.setAlignment(Pos.TOP_LEFT);

        if (orders == null || orders.isEmpty()) {
            reportBox.getChildren().add(new Label("No orders available to calculate revenue."));
        } else {
     // حساب إجمالي الإيرادات
            double totalRevenue = 0;
            Map<String, Double> revenuePerSeller = new HashMap<>();

            for (Order order : orders) {
                double payment = order.getPayment();
                totalRevenue += payment;

                String seller = order.getSeller();
                revenuePerSeller.put(seller, revenuePerSeller.getOrDefault(seller, 0.0) + payment);
            }

        // عرض إجمالي الإيرادات
            Label totalLabel = new Label("Total Revenue: " + totalRevenue);
            totalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: green;");
            reportBox.getChildren().add(totalLabel);

        // عرض الإيرادات حسب البائع
            Label perSellerLabel = new Label("Revenue per Seller:");
            perSellerLabel.setStyle("-fx-font-weight: bold;");
            reportBox.getChildren().add(perSellerLabel);

            for (Map.Entry<String, Double> entry : revenuePerSeller.entrySet()) {
                Label sellerLabel = new Label("Seller: " + entry.getKey() + " | Revenue: " + entry.getValue());
                reportBox.getChildren().add(sellerLabel);
            }
        }

        ScrollPane scrollPane = new ScrollPane(reportBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        Button backBtn = new Button("Back");
        backBtn.setMaxWidth(100);
        backBtn.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        backBtn.setOnAction(ev -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(createWelcomeView());
        });

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, scrollPane, backBtn);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(layout);
    });
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





    Image bgImage = new Image("file:/D:\\project\\talabat.jpg");
    BackgroundImage backgroundImg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
    Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6);");

    StackPane root = new StackPane();
    root.setBackground(new Background(backgroundImg));
    root.getChildren().addAll(overlay, panes);
    //stage.setResizable(true);
    stage.setTitle("Admin");
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    stage.setScene(scene);
    stage.show();
   
    return scene;
    
      
    }
    private static VBox createWelcomeView() {
        Label welcome = new Label("Welcome to Talabat!");
        welcome.setStyle("-fx-text-fill: " + talabatDark + "; " +
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold;");
        
        
        VBox box = new VBox(welcome);
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
    
