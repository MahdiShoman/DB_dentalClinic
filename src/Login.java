import DB.ConnectDB;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.*;

public class Login {
    ConnectDB connectDB = new ConnectDB();
    VBox loginLayout ;
    public Login() {
        loginLayout = new VBox(10);
        initializeLogin();
    }

    public VBox getLoginLayout() {
        return loginLayout;
    }

    public void setLoginLayout(VBox loginLayout) {
        this.loginLayout = loginLayout;
    }

    void initializeLogin (){

        // Create a login page
        Label loginLabel = new Label("Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            // Implement your login logic here
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                checkLogin ( username, password);
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            // Check credentials and perform login
        });

        loginLayout.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton);


    }

    void checkLogin (String username,String password) throws SQLException, ClassNotFoundException {
       //"Select u.username , u.password from users u where u.username = ? and u.password = ?;"
        String role=" ";
        connectDB.startDBConnection();
        Connection con = ConnectDB.connection;
        PreparedStatement ps =
                con.prepareStatement
                        ("Select u.role  from users u where u.username = ? and u.password = ? ;");
        ps.setString (1, username);
        ps.setString (2, password);
       // ps.setString (3, role);

//        System.out.println(username+password+role);
        ResultSet rs = ps.executeQuery();


        if (rs.next()) {
            System.out.println("its exist");
            role = rs.getString("role");
            System.out.println(role);
//            Main.enterAccount( role);
            Main.enterAccount(role);
        } else {
            System.out.println("nothing");
        }
        connectDB.closeDBConnection();
    }
}
