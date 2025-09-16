package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PetInteractionScreen {
    private Stage stage;
    private Main main;
    private Scene scene;

    public PetInteractionScreen(Stage stage, Main main) {
        this.stage = stage;
        this.main = main;
    }

    public void show() {
        ImageView petView = new ImageView();
        // TODO: load your pet image here

        Button feedBtn = new Button("Feed");
        Button playBtn = new Button("Play");
        Button healBtn = new Button("Heal");
        Button restBtn = new Button("Rest");
        Button backBtn = new Button("← Back to Dashboard");

        HBox actions = new HBox(15, feedBtn, playBtn, healBtn, restBtn);
        actions.setAlignment(Pos.CENTER);

        // Button actions (example only)
        feedBtn.setOnAction(e -> updateStats(0.15, 0.05, 0));
        playBtn.setOnAction(e -> updateStats(0.1, -0.05, 0.05));
        healBtn.setOnAction(e -> updateStats(0.05, 0, 0));
        restBtn.setOnAction(e -> updateStats(0, 0.2, 0));

        backBtn.setOnAction(e -> {
            DashboardScreen dashboard = new DashboardScreen(stage, main);
            dashboard.show();   // ✅ show dashboard directly
        });

        VBox layout = new VBox(20, petView, actions, backBtn);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    private void updateStats(double happinessDelta, double energyDelta, double levelDelta) {
        // TODO: Implement stats update logic
    }
}
