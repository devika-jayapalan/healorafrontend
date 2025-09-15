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

    public interface LoginListener {
        void onLoginSuccess();         // ✅ No args
        void onSwitchToRegister();
    }

    public LoginScreen(LoginListener listener) {
        this.setStyle("-fx-background-color: #f2f6fc;");
        this.setPadding(new Insets(20));

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setPrefWidth(300);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        Label title = new Label("Login");
        title.setFont(Font.font("Arial", 22));
        title.setTextFill(Color.web("#333"));

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 8 16;");
        loginBtn.setFont(Font.font(14));

        Hyperlink registerLink = new Hyperlink("Don’t have an account? Register");
        registerLink.setFont(Font.font(12));
        registerLink.setOnAction(e -> listener.onSwitchToRegister());

        card.getChildren().addAll(title, emailField, passwordField, loginBtn, registerLink);
        this.getChildren().add(card);

        // Button action
        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String pass = passwordField.getText();

            if (!email.isEmpty() && !pass.isEmpty()) {
                listener.onLoginSuccess();   // ✅ Calls Main.java
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter email and password!", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}
