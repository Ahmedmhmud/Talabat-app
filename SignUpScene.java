package talabatapp;
    
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static talabatapp.AdminScene.contentArea;
import static talabatapp.AdminScene.getScene;

class SignUpScene {

    public static Scene getScene(Stage primaryStage) {
        TextField inNAme = new TextField();
        inNAme.setPromptText("Enter your name");
        inNAme.setMaxWidth(300);

        TextField inEmail = new TextField();
        inEmail.setPromptText("Enter your email");
        inEmail.setMaxWidth(300);

        PasswordField inPassword = new PasswordField();
        inPassword.setPromptText("Enter your password");
        inPassword.setMaxWidth(300);

        TextField inAddress = new TextField();
        inAddress.setPromptText("Enter your address");
        inAddress.setMaxWidth(300);

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
        lblError.setVisible(false);

        Button btnRegister = new Button("Register");
        btnRegister.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
        btnRegister.setMaxWidth(100);

        btnRegister.setOnAction(e -> {
            String name = inNAme.getText();
            String email = inEmail.getText();
            String password = inPassword.getText();
            String address = inAddress.getText();
            
            // Input validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()) {
                lblError.setText("Please fill in all fields.");
                lblError.setVisible(true);
            } else if (!isValidEmail(email)) {
                lblError.setText("Please enter a valid email.");
                lblError.setVisible(true);
            } else if (password.length() < 6) {
                lblError.setText("Password must be at least 6 characters long.");
                lblError.setVisible(true);
            } else {
                // Register the customer if validation passes
                Customer customer = new Customer(name, email, password, UserRole.CUSTOMER, address);
                customer.register(name, email, password, UserRole.CUSTOMER, address);
                lblError.setText("Registration successful!");
                lblError.setStyle("-fx-text-fill: green;");
                lblError.setVisible(true);

                // Clear the fields
                inNAme.clear();
                inEmail.clear();
                inPassword.clear();
                inAddress.clear();

                // Navigate to AppScene
                CustomerScene customerScene = new CustomerScene();
                primaryStage.setScene(customerScene.getMainScene(primaryStage));
                //primaryStage.setScene(CustomerScene.getScene(primaryStage));
                primaryStage.setTitle("Talabat");
                primaryStage.setMaximized(true);
            }
        });
          
            Button backButton = new Button("Back");
            backButton.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white;");
            backButton.setMaxWidth(100);
            backButton.setOnAction(e -> {
            TalabatApp loginApp = new TalabatApp();
            try {
                contentArea.getChildren().clear();
                loginApp.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });     
        
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll( inNAme, inEmail, inPassword, inAddress, btnRegister, backButton, lblError);
        root.setAlignment(Pos.CENTER);

        // Background image setup
        Image bgImage = new Image("file:/D:/unnamed.png");
        BackgroundImage backgroundImg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));

        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);");

        StackPane root1 = new StackPane();
        root1.setBackground(new Background(backgroundImg));
        root1.getChildren().addAll(overlay, root);
        root1.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        StackPane container = new StackPane(root1);
        container.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Adjust the scene size based on the screen size
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        Scene scene = new Scene(container, screenWidth, screenHeight);
        return scene;
    }

    // Method to validate email format
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}