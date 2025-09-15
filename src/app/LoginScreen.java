package app;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LoginScreen extends StackPane {

    // Listener interface (Main.java will implement this)
    public interface LoginListener {
        void onLoginSuccess();
        void onSwitchToRegister(); // NEW → handle "Register" link
    }

    public LoginScreen(LoginListener listener) {
        // Background
        this.setStyle("-fx-background-color: #f2f6fc;"); // pastel light background
        this.setPadding(new Insets(20));

        // Card container
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setPrefWidth(300);
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);"
        );

        // Title
        Label title = new Label("Login");
        title.setFont(Font.font("Arial", 22));
        title.setTextFill(Color.web("#333"));

        // Email
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        // Password
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Login button
        Button loginBtn = new Button("Login");
        loginBtn.setStyle(
            "-fx-background-color: #4a90e2; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8; " +
            "-fx-padding: 8 16;"
        );
        loginBtn.setFont(Font.font(14));

        // Forgot password (just placeholder)
        Label forgot = new Label("Forgot password?");
        forgot.setTextFill(Color.GRAY);
        forgot.setFont(Font.font(12));

        // Register link
        Hyperlink registerLink = new Hyperlink("Don’t have an account? Register");
        registerLink.setFont(Font.font(12));
        registerLink.setOnAction(e -> listener.onSwitchToRegister());

        // Add all to card
        card.getChildren().addAll(title, emailField, passwordField, loginBtn, forgot, registerLink);

        // Add card to root
        this.getChildren().add(card);
        StackPane.setAlignment(card, Pos.CENTER);

        // Fade animation
        FadeTransition ft = new FadeTransition(Duration.seconds(1), card);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        // Button action
        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String pass = passwordField.getText();

            // Dummy validation
            if (!email.isEmpty() && !pass.isEmpty()) {
                listener.onLoginSuccess();  // Notify Main.java
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter email and password!", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}
