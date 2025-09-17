package app;

import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;

public class PetInteractionScreen {
    private Stage stage;
    private Main main;
    private Scene scene;

    // model
    private final Pet pet;

    // UI
    private ProgressBar happinessBar;
    private ProgressBar energyBar;
    private ProgressBar levelBar;
    private ImageView petView;
    private Label happinessValueLabel;
    private Label energyValueLabel;
    private Label levelValueLabel;

    // configuration
    private final int LEVEL_MAX = 10; // used for level-progress bar (adjust if different)

    public PetInteractionScreen(Stage stage, Main main) {
        this.stage = stage;
        this.main = main;
        this.pet = new Pet("Healora"); // create pet when screen object created
    }

    public void show() {
        // image placeholder
        petView = new ImageView();
        petView.setFitWidth(220);
        petView.setPreserveRatio(true);
        loadPetImage("pet_placeholder.png"); // load placeholder (must exist in resources/images)

        // labels & progress bars
        Label title = new Label(pet.getName());
        title.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        Label happinessLabel = new Label("Happiness:");
        happinessBar = new ProgressBar();
        happinessBar.setPrefWidth(300);
        happinessValueLabel = new Label();
        // bind numeric label and progress bar
        happinessValueLabel.textProperty().bind(pet.happinessProperty().asString().concat("%"));
        happinessBar.progressProperty().bind(Bindings.divide(pet.happinessProperty(), 100.0));

        Label energyLabel = new Label("Energy:");
        energyBar = new ProgressBar();
        energyBar.setPrefWidth(300);
        energyValueLabel = new Label();
        energyValueLabel.textProperty().bind(pet.energyProperty().asString().concat("%"));
        energyBar.progressProperty().bind(Bindings.divide(pet.energyProperty(), 100.0));

        Label levelLabel = new Label("Level:");
        levelBar = new ProgressBar();
        levelBar.setPrefWidth(300);
        levelValueLabel = new Label();
        // normalize level to LEVEL_MAX for progress visualization
        levelValueLabel.textProperty().bind(pet.levelProperty().asString());
        levelBar.progressProperty().bind(Bindings.divide(pet.levelProperty(), (double)LEVEL_MAX));

        // actions
        Button feedBtn = new Button("🍎 Feed");
        Button playBtn = new Button("🎮 Play");
        Button healBtn = new Button("💊 Heal");
        Button restBtn = new Button("😴 Rest");
        Button backBtn = new Button("← Back to Dashboard");

        HBox valuesRow1 = new HBox(8, happinessLabel, happinessBar, happinessValueLabel);
        valuesRow1.setAlignment(Pos.CENTER_LEFT);
        HBox valuesRow2 = new HBox(8, energyLabel, energyBar, energyValueLabel);
        valuesRow2.setAlignment(Pos.CENTER_LEFT);
        HBox valuesRow3 = new HBox(8, levelLabel, levelBar, levelValueLabel);
        valuesRow3.setAlignment(Pos.CENTER_LEFT);

        HBox actions = new HBox(12, feedBtn, playBtn, healBtn, restBtn);
        actions.setAlignment(Pos.CENTER);

        // button handlers (call backend methods)
        feedBtn.setOnAction(e -> {
            pet.feed();
            animatePetBounce();
            maybeUpdatePetImage();
        });

        playBtn.setOnAction(e -> {
            pet.play();
            animatePetBounce();
            maybeUpdatePetImage();
        });

        healBtn.setOnAction(e -> {
            pet.heal();
            animatePetBounce();
            maybeUpdatePetImage();
        });

        restBtn.setOnAction(e -> {
            pet.rest();
            animatePetBounce();
            maybeUpdatePetImage();
        });

        backBtn.setOnAction(e -> main.showDashboardScreen());

        VBox layout = new VBox(14,
                title,
                petView,
                valuesRow1,
                valuesRow2,
                valuesRow3,
                actions,
                backBtn
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        scene = new Scene(layout, 760, 640);
        // load stylesheet (if you have it)
        try {
            if (getClass().getResource("/css/style.css") != null)
                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception ignored) {}

        stage.setScene(scene);
        stage.show();
    }

    // small bounce animation on action
    private void animatePetBounce() {
        ScaleTransition st = new ScaleTransition(Duration.millis(220), petView);
        st.setFromX(0.95); st.setFromY(0.95);
        st.setToX(1.08); st.setToY(1.08);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    // update image based on pet state (simple rules)
    private void maybeUpdatePetImage() {
        try {
            int h = pet.getHappiness();
            int e = pet.getEnergy();

            if (h >= 70) {
                loadPetImage("pet_happy.png");
            } else if (e < 25) {
                loadPetImage("pet_sad.png"); // tired sad
            } else if (h < 35) {
                loadPetImage("pet_sad.png");
            } else {
                loadPetImage("pet_neutral.png");
            }
        } catch (Exception ignored) {}
    }

    // safe image loader from /resources/images/
    private void loadPetImage(String fileName) {
        try (InputStream is = getClass().getResourceAsStream("/images/" + fileName)) {
            if (is != null) {
                Image img = new Image(is);
                petView.setImage(img);
            } else {
                System.err.println("⚠ Could not find image: " + fileName + " on classpath /images/");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
