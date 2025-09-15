package app;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class DashboardScreen {

    private Stage stage;
    private Main main;

    public DashboardScreen(Stage stage, Main main) {
        this.stage = stage;
        this.main = main;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fceef5;"); // soft pink background

        // --- TOP: Welcome message ---
        Text welcome = new Text("Welcome back to Healora!");
        welcome.setFont(Font.font("Comic Sans MS", 22));
        HBox topBox = new HBox(welcome);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(15));
        root.setTop(topBox);

        // --- CENTER: Pet placeholder ---
        // Declare and initialize petView here (guaranteed initialization)
        ImageView petView = new ImageView();

        try {
            URL imgUrl = getClass().getResource("/images/pet_placeholder.png");
            if (imgUrl != null) {
                Image petImage = new Image(imgUrl.toExternalForm());
                petView.setImage(petImage);
            } else {
                System.out.println("⚠ pet_placeholder.png not found on classpath (/images/pet_placeholder.png)");
            }
        } catch (Exception e) {
            System.out.println("⚠ Error loading pet image: " + e.getMessage());
            // leave petView empty as fallback
        }

        petView.setFitWidth(180);
        petView.setFitHeight(180);
        petView.setPreserveRatio(true);

        // Scale animation on hover
        petView.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), petView);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });
        petView.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), petView);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        HBox centerBox = new HBox(petView);
        centerBox.setAlignment(Pos.CENTER);
        root.setCenter(centerBox);

        // --- BOTTOM: Buttons ---
        Button logMoodBtn = new Button("Open Mood Tracker");
        Button logoutBtn = new Button("Logout");

        logMoodBtn.setOnAction(e -> main.showMoodTrackerScreen());
        logoutBtn.setOnAction(e -> {
            // reset current user if you added a setter
            try {
                main.getClass().getMethod("setCurrentUser", String.class).invoke(main, new Object[]{null});
            } catch (Exception ignore) { /* optional: ignore if setter not present */ }
            main.showLoginScreen();
        });

        HBox bottomBox = new HBox(20, logMoodBtn, logoutBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));
        root.setBottom(bottomBox);

        // --- Scene ---
        Scene scene = new Scene(root, 800, 600);
        try {
            URL cssUrl = getClass().getResource("/css/style.css");
            if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());
        } catch (Exception e) {
            System.out.println("⚠ Stylesheet not found or failed to load.");
        }

        // Fade in animation
        FadeTransition ft = new FadeTransition(Duration.seconds(1), root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        stage.setScene(scene);
        stage.show();
    }
}
