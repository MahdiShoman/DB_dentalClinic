package sign_in_page;

import DB.ConnectDB;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class SignUp { // the handler button

    Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
    ConnectDB connectDB = new ConnectDB();
    VBox signUpLayout;

    public SignUp() {
        signUpLayout = new VBox(10);
        initializeSignUp();
    }

    public VBox getSignUpLayout() {
        return signUpLayout;
    }

    public void setSignUpLayout(VBox signUpLayout) {
        this.signUpLayout = signUpLayout;
    }

    void initializeSignUp() {
        // Create a signup page
        Label signUpLabel = new Label("Sign Up");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Doctor", "Nurse", "Secretary");
        roleComboBox.setPromptText("Select a Role");
        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(event -> {
            String selectedRole = roleComboBox.getValue();
            openSignUpPage(selectedRole);
        });
        signUpLayout.getChildren().addAll(signUpLabel, new Label("Sign up as:"), roleComboBox, signUpButton);

    }

    public void openSignUpPage(String role) {
        Stage signUpStage = new Stage();
        VBox signUpLayout = new VBox(10);

        // Create common fields
        TextField nameField = new TextField();
        TextField userNameField = new TextField();
        DatePicker birthdayPicker = new DatePicker();
        TextField contactNumberField = new TextField();
        TextField addressField = new TextField();
        TextField passwordField = new TextField();
        TextField specialtyField = new TextField();
        // Create a Save button to store the information
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            // Retrieve and save the information based on the selected role
            // You can add the logic to save the data to a database or perform other actions here.
            if (nameField.getText().trim().isEmpty() || birthdayPicker.getValue() == null || contactNumberField.getText().trim().isEmpty()
                    || addressField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
                alertWarning.setContentText("you should fill all fields");
                alertWarning.show();
            } else {

                System.out.println("Role: " + role);
                System.out.println("Name: " + nameField.getText());
                System.out.println("userName: " + userNameField.getText());
                System.out.println("Birthday: " + birthdayPicker.getValue());
                System.out.println("Contact Number: " + contactNumberField.getText());
                System.out.println("Address: " + addressField.getText());
                System.out.println("Password: " + passwordField.getText());
                try {
                    connectDB.startDBConnection();
                    connectDB.ExecuteStatement("INSERT INTO users (username, password, role, name, birthday, contact_number, address, specialty) values( '" +
                            userNameField.getText() + "','"
                            + passwordField.getText() + "','"
                            + role + "','"
                            + nameField.getText() + "', '"
                            + birthdayPicker.getValue() + "', '"
                            + contactNumberField.getText() + "', '"
                            + addressField.getText() + "', '"
                            + specialtyField.getText() + "')" + ";");
                    connectDB.closeDBConnection();
                } catch (SQLIntegrityConstraintViolationException e) {
                    alertWarning.setContentText(e.getMessage());
                    alertWarning.show();

                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
            if (role.equals("Doctor")) {
                //  System.out.println("Specialty: " + specialtyField.getText());
            } else if (role.equals("Nurse")) {
                //   System.out.println("Doctor ID: " + doctorIdField.getText());
            }
        });
        // Create role-specific fields
        if (role.equals("Doctor")) {

            signUpLayout.getChildren().addAll(
                    new Label("Name:"), nameField,
                    new Label("userName:"), userNameField,
                    new Label("Specialty:"), specialtyField,
                    new Label("Birthday:"), birthdayPicker,
                    new Label("Contact Number:"), contactNumberField,
                    new Label("Address:"), addressField,
                    new Label("Password:"), passwordField

            );
        } else if (role.equals("Nurse")) {
            TextField doctorIdField = new TextField();
            signUpLayout.getChildren().addAll(
                    new Label("Name:"), nameField,
                    new Label("userName:"), userNameField,
                    new Label("Specialty:"), specialtyField,
                    new Label("Birthday:"), birthdayPicker,
                    new Label("Contact Number:"), contactNumberField,
                    new Label("Address:"), addressField,
                    new Label("Nurse ID:"), doctorIdField,
                    new Label("Password:"), passwordField
            );
        } else if (role.equals("Secretary")) {
            signUpLayout.getChildren().addAll(
                    new Label("Name:"), nameField,
                    new Label("userName:"), userNameField,
                    new Label("Specialty:"), specialtyField,
                    new Label("Birthday:"), birthdayPicker,
                    new Label("Contact Number:"), contactNumberField,
                    new Label("Address:"), addressField,
                    new Label("Password:"), passwordField
            );
        }


        signUpLayout.getChildren().add(saveButton);

        Scene signUpScene = new Scene(signUpLayout, 400, 500);
        signUpStage.setScene(signUpScene);
        signUpStage.setTitle("Sign Up as " + role);
        signUpStage.show();
    }

}
