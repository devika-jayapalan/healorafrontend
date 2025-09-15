package app;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.time.LocalDate;

public class MoodTrackerScreen extends BorderPane {

    private Stage primaryStage;
    private Main main;
    private ImageView petImage;

    public MoodTrackerScreen(Stage stage, Main main) {
        this.primaryStage = stage;
        this.main = main;

        this.setStyle("-fx-background-color: #f2f6fc;");
        this.setPadding(new Insets(20));

        // ---- Top ----
        Label title = new Label("Track Your Mood");
        title.setFont(Font.font("Arial", 22));
        title.setTextFill(Color.web("#333"));
        BorderPane.setAlignment(title, Pos.CENTER);
        this.setTop(title);

        // ---- Pet Image ----
        petImage = new ImageView(loadPetImage("pet_placeholder.png"));
        petImage.setFitWidth(150);
        petImage.setPreserveRatio(true);

        // Hover animation
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.3), petImage);
        petImage.setOnMouseEntered(e -> {
            st.setToX(1.1);
            st.setToY(1.1);
            st.playFromStart();
        });
        petImage.setOnMouseExited(e -> {
            st.setToX(1.0);
            st.setToY(1.0);
            st.playFromStart();
        });

        VBox petBox = new VBox(10, petImage);
        petBox.setAlignment(Pos.CENTER);

        // ---- Mood selection ----
        VBox moodBox = new VBox(15);
        moodBox.setAlignment(Pos.CENTER);

        Label chooseLabel = new Label("How are you feeling today?");
        chooseLabel.setFont(Font.font("Arial", 16));

        ComboBox<String> moodSelect = new ComboBox<>();
        moodSelect.getItems().addAll("Happy", "Sad", "Angry", "Neutral", "Excited", "Stressed");
        moodSelect.setPromptText("Select Mood");

        // Slider for intensity
        Label intensityLabel = new Label("Mood Intensity (1-5):");
        Slider intensitySlider = new Slider(1, 5, 3);
        intensitySlider.setShowTickLabels(true);
        intensitySlider.setShowTickMarks(true);
        intensitySlider.setMajorTickUnit(1);
        intensitySlider.setSnapToTicks(true);

        Button submitBtn = new Button("Save Mood");
        submitBtn.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 8 16;");

        // Suggestion area
        TextArea suggestionArea = new TextArea();
        suggestionArea.setEditable(false);
        suggestionArea.setWrapText(true);
        suggestionArea.setPrefHeight(120);
        suggestionArea.setPromptText("Suggestions will appear here...");

        moodBox.getChildren().addAll(chooseLabel, moodSelect, intensityLabel, intensitySlider, submitBtn, suggestionArea);

        VBox centerContent = new VBox(20, petBox, moodBox);
        centerContent.setAlignment(Pos.CENTER);

        this.setCenter(centerContent);

        // ---- Bottom: Back Button ----
        Button backBtn = new Button("← Back to Dashboard");
        backBtn.setOnAction(e -> main.showDashboardScreen());
        BorderPane.setAlignment(backBtn, Pos.CENTER);
        this.setBottom(backBtn);

        // ---- Fade-in Animation ----
        FadeTransition ft = new FadeTransition(Duration.seconds(1), centerContent);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        // ---- Submit Button Action ----
        submitBtn.setOnAction(e -> {
            String moodType = moodSelect.getValue();
            int intensity = (int) intensitySlider.getValue();

            if (moodType == null || moodType.isEmpty()) {
                showAlert("Error", "Please select a mood!");
                return;
            }

            try {
                // 1. Create a mood object
                
               mood m = new mood(moodType, 3, LocalDate.now()); //this line showing error

                // 2. Save to database
                DatabaseManager db = new DatabaseManager();
                db.saveMood(m);

                // 3. Analyze moods
                MoodAnalyzer analyzer = new MoodAnalyzer(db.getAllMoods());
                String analysis = "Most Frequent: " + analyzer.mostFrequentMood() +
                                  "\nAvg Intensity: " + analyzer.calculateAverageIntensity();

                // 4. Suggestion
                String suggestion = SuggestionEngine.generateSuggestion(m);

                // 5. Show in text area
                suggestionArea.setText("Mood: " + moodType + " (Intensity: " + intensity + ")\n\n" +
                        "Analysis:\n" + analysis + "\n\n" +
                        "Suggestion:\n" + suggestion);

                // 6. Update pet reaction
                updatePetReaction(moodType);

            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Database Error", "Could not save mood or generate suggestion!");
            }
        });
    }

    private void updatePetReaction(String mood) {
        String fileName;
        switch (mood.toLowerCase()) {
            case "happy": fileName = "pet_happy.png"; break;
            case "sad": fileName = "pet_sad.png"; break;
            case "angry": fileName = "pet_angry.png"; break;
            case "excited": fileName = "pet_excited.png"; break;
            case "stressed": fileName = "pet_stressed.png"; break;
            default: fileName = "pet_neutral.png"; break;
        }
        petImage.setImage(loadPetImage(fileName));

        // Small bounce animation
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.3), petImage);
        st.setFromX(0.9);
        st.setFromY(0.9);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    private Image loadPetImage(String fileName) {
        try {
            InputStream is = getClass().getResourceAsStream("/images/" + fileName);
            if (is != null) return new Image(is);
            System.err.println("⚠ Could not load image: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Image("https://via.placeholder.com/150"); // fallback
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(title);
        alert.showAndWait();
    }

    public void show() {
        Scene scene = new Scene(this, 600, 500);
        primaryStage.setScene(scene);
    }
}
