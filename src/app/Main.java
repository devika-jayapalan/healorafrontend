package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application 
        implements LoginScreen.LoginListener, RegistrationScreen.RegistrationListener {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showLoginScreen();
        stage.setTitle("Mood Tracker App");
        stage.show();
    }

    // -------------------
    // Screen switching
    // -------------------

    public void showLoginScreen() {
        LoginScreen login = new LoginScreen(this);
        Scene scene = new Scene(login, 600, 400);
        primaryStage.setScene(scene);
    }

    public void showRegistrationScreen() {
        RegistrationScreen reg = new RegistrationScreen(this);
        Scene scene = new Scene(reg, 600, 450);
        primaryStage.setScene(scene);
    }

    public void showDashboardScreen() {
        DashboardScreen dashboard = new DashboardScreen(primaryStage, this);
        dashboard.show();
    }
    public void showMoodTrackerScreen() {
    MoodTrackerScreen moodScreen = new MoodTrackerScreen(primaryStage, this);
    moodScreen.show();
}


    // -------------------
    // Listener callbacks
    // -------------------

    @Override
    public void onLoginSuccess() {
        showDashboardScreen();
    }

    @Override
    public void onRegistrationSuccess() {
        // After registration → go back to login
        showLoginScreen();
    }

    @Override
    public void onSwitchToLogin() {
        showLoginScreen();
    }

    @Override
    public void onSwitchToRegister() {
        showRegistrationScreen();
    }
}
