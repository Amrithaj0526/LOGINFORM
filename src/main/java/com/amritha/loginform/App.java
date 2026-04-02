package com.amritha.loginform;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        Button loginBtn = new Button("Login");
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        loginBtn.setOnAction(e -> {
            String user = userField.getText().trim();
            String pass = passField.getText().trim();

            String userErr = validateUsername(user);
            String passErr = validatePassword(pass);

            if (userErr != null || passErr != null) {
                StringBuilder msg = new StringBuilder();
                if (userErr != null) msg.append("Username: ").append(userErr).append("\n");
                if (passErr != null) msg.append("Password: ").append(passErr);
                messageLabel.setText(msg.toString().trim());
                Alert a = new Alert(Alert.AlertType.ERROR, msg.toString().trim());
                a.setHeaderText("Validation failed");
                a.showAndWait();
                return;
            }

            // Check credentials against configured credentials (if any)
            if (!authenticate(user, pass)) {
                String err = "Invalid username or password";
                messageLabel.setText(err);
                Alert a = new Alert(Alert.AlertType.ERROR, err);
                a.setHeaderText("Authentication failed");
                a.showAndWait();
                return;
            }

            // Authentication passed
            messageLabel.setText("");
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Login successful!");
            a.showAndWait();
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(loginBtn, 1, 2);
        GridPane.setHalignment(loginBtn, HPos.RIGHT);
        grid.add(messageLabel, 1, 3);

        Scene scene = new Scene(grid, 320, 180);
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    String validateUsername(String user) {
        if (user == null || user.isEmpty()) return "must not be empty";
        if (user.length() < 3) return "must be at least 3 characters";
        if (user.length() > 20) return "must not be longer than 20 characters";
        if (!user.matches("[A-Za-z0-9._-]+")) return "only letters, digits, dot, underscore and hyphen allowed";
        return null;
    }

    String validatePassword(String pass) {
        if (pass == null || pass.isEmpty()) return "must not be empty";
        if (pass.length() < 8) return "must be at least 8 characters";
        boolean hasUpper = pass.matches(".*[A-Z].*");
        boolean hasLower = pass.matches(".*[a-z].*");
        boolean hasDigit = pass.matches(".*\\d.*");
        boolean hasSpecial = pass.matches(".*[^A-Za-z0-9].*");
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) return "must contain upper, lower, digit and special character";
        return null;
    }

    /**
     * Authenticate credentials.
     * Priority: System properties `login.username/login.password` -> env `LOGIN_USERNAME/LOGIN_PASSWORD`.
     * If neither configured, any credentials that passed validation are accepted (demo mode).
     */
    boolean authenticate(String user, String pass) {
        String cfgUser = System.getProperty("login.username");
        String cfgPass = System.getProperty("login.password");
        if (cfgUser == null) cfgUser = System.getenv("LOGIN_USERNAME");
        if (cfgPass == null) cfgPass = System.getenv("LOGIN_PASSWORD");
        if (cfgUser != null && cfgPass != null) {
            return cfgUser.equals(user) && cfgPass.equals(pass);
        }
        // No configured credentials: accept validated inputs (demo mode)
        return true;
    }
}
