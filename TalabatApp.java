package talabatapp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TalabatApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField inEmail = new TextField();
        inEmail.setPromptText("Enter your email");
        inEmail.setMaxWidth(300);

        PasswordField inPassword = new PasswordField();
        inPassword.setPromptText("Enter your password");
        inPassword.setMaxWidth(300);

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
        lblError.setVisible(false);

        Button btnLogIn = new Button("Log in");
        btnLogIn.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogIn.setMaxWidth(300);
        hoverEffect(btnLogIn);

        btnLogIn.setOnAction(e -> {
            String email = inEmail.getText().trim();
            String password = inPassword.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                lblError.setText("Please enter both email and password.");
                lblError.setVisible(true);
                return;
            }

            UserRole role = User.logIn(email, password);

            if (null == role) {
                lblError.setText("Invalid email or password. Please try again.");
                lblError.setVisible(true);
            } else switch (role) {
                case ADMIN:
                    Admin a = new Admin(email, password, UserRole.ADMIN);
                    lblError.setVisible(false);
                    primaryStage.setScene(AdminScene.getScene(primaryStage, a));
                    break;
                case SELLER:
                    Seller s = new Seller(email);
                    SellerScene sellerscene = new SellerScene();
                    primaryStage.setScene(sellerscene.start(primaryStage, s));
                    break;
                case CUSTOMER:
                    lblError.setVisible(false);
                    CustomerScene customerScene = new CustomerScene(email);
                    primaryStage.setScene(customerScene.getMainScene(primaryStage));
                    primaryStage.setTitle("Talabat ");
                    primaryStage.setMaximized(true);
                    break;
                default:
                    lblError.setText("Invalid email or password. Please try again.");
                    lblError.setVisible(true);
                    break;
            }
        });

        Button btnSignUp = new Button("Sign up");
        btnSignUp.setStyle("-fx-background-color: #FF6F00; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSignUp.setMaxWidth(300);
        hoverEffect(btnSignUp);

        btnSignUp.setOnAction(e -> {
            primaryStage.setScene(SignUpScene.getScene(primaryStage));
            primaryStage.setTitle("Talabat");
            primaryStage.setMaximized(true);
        });

        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(20));
        formBox.getChildren().addAll(inEmail, inPassword, btnLogIn, btnSignUp, lblError);

        Image bgImage = new Image("file:/D:/unnamed.png");
        BackgroundImage backgroundImg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );

        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);");

        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImg));
        root.getChildren().addAll(overlay, formBox);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Talabat Login");
        primaryStage.show();
    }

    private void hoverEffect(Button button) {
    DropShadow shadow = new DropShadow();
    shadow.setColor(Color.web("#FFA000"));
    shadow.setRadius(15);

    Scale scaleUp = new Scale(1.02, 1.02);
    button.getTransforms().add(scaleUp);
    scaleUp.setPivotX(button.getWidth() / 2);
    scaleUp.setPivotY(button.getHeight() / 2);
    scaleUp.setX(1);
    scaleUp.setY(1); // No initial scaling

    // Ensure the pivot point is set after layout
    button.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
        scaleUp.setPivotX(newVal.getWidth() / 2);
        scaleUp.setPivotY(newVal.getHeight() / 2);
    });

    button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
        button.setEffect(shadow);
        scaleUp.setX(1.02);
        scaleUp.setY(1.02);
    });

    button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
        button.setEffect(null);
        scaleUp.setX(1);
        scaleUp.setY(1);
    });
}


    public static void main(String[] args) {
        launch(args);
    }
}
