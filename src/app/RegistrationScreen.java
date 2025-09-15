package app;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class RegistrationScreen extends StackPane {

    public interface RegistrationListener {
        void onRegistrationSuccess();   // ✅ No args
        void onSwitchToLogin();
    }

    public RegistrationScreen(RegistrationListener listener) {
        this.setStyle("-fx-background-color: #f2f6fc;");
        this.setPadding(new Insets(20));

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setPrefWidth(320);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        Label title = new Label("Register");
        title.setFont(Font.font("Arial", 22));
        title.setTextFill(Color.web("#333"));

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Confirm Password");

        Button submitBtn = new Button("Register");
        submitBtn.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 8 16;");
        submitBtn.setFont(Font.font(14));

        Hyperlink switchToLogin = new Hyperlink("Already have an account? Login");
        switchToLogin.setFont(Font.font(12));
        switchToLogin.setOnAction(e -> listener.onSwitchToLogin());

        card.getChildren().addAll(title, nameField, emailField, passwordField, confirmField, submitBtn, switchToLogin);
        this.getChildren().add(card);

        submitBtn.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String pass = passwordField.getText();
            String confirm = confirmField.getText();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                showAlert("Error", "Please fill in all fields!");
                return;
            }
            if (!pass.equals(confirm)) {
                showAlert("Error", "Passwords do not match!");
                return;
            }

            User newUser = new User(name, email, pass);
            UserDatabase.addUser(newUser);

            showAlert("Success", "Registration successful!");
            listener.onRegistrationSuccess();   // ✅ Calls Main.java
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(title);
        alert.showAndWait();
    }
}
