package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements LoginScreen.LoginListener {
    private Stage stage;
    private String currentUser;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        showLoginScreen();
        stage.show();   // ✅ must show the stage after setting scene
    }

    public void showLoginScreen() {
        LoginScreen login = new LoginScreen(this);   // ✅ now works
        Scene scene = new Scene(login, 500, 400);
        stage.setScene(scene);
    }

    public void showDashboardScreen() {
        DashboardScreen dashboard = new DashboardScreen(stage, this);
        dashboard.show();
    }

    public void showMoodTrackerScreen() {
        MoodTrackerScreen moodTracker = new MoodTrackerScreen(stage, this);
        moodTracker.show();
    }

    public void showPetInteractionScreen() {
        PetInteractionScreen petScreen = new PetInteractionScreen(stage, this);
        petScreen.show();
    }

    @Override
    public void onLoginSuccess() {
        showDashboardScreen();  // ✅ switch to dashboard
    }

    @Override
    public void onSwitchToRegister() {
        // Later implement a RegisterScreen
        System.out.println("👉 Register screen will go here!");
    }

    public void setCurrentUser(String user) {
        this.currentUser = user;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
