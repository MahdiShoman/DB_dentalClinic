import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sign_in_page.SignUp;

import java.sql.SQLException;

public class Main  extends Application {
    Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
    SignUp signUp = new SignUp();
    Login login = new Login();
    static Doctor doctor = new Doctor();
    static Secretary secretary = new Secretary();
    static Nurse nurse = new Nurse();
    Button signUpBT = new Button("add new user");
    private Image img;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
        primaryStage.setTitle("welcome page");
        Label welcomeLabel = new Label("Dental Clinic organizer");
        welcomeLabel.setFont(Font.font(40));
        welcomeLabel.setTextFill(Color.BLACK);
        Label massageLabel = new Label("login to see your patient and your oppointments");
        massageLabel.setFont(Font.font(15));
        massageLabel.setTextFill(Color.BLACK);
        // Creating Background Image
        img = new Image("backgournd.jpg");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);

        Doctor doctor = new Doctor();
        signUpBT.setFont(Font.font(14));
        signUpBT.setTextFill(Color.BLUE);
        signUpBT.setStyle("-fx-background-radius: 18, 7;-fx-background-color:white;");

        GridPane pane = new GridPane();
        pane.add(welcomeLabel, 0, 0);
        pane.add(massageLabel, 0, 1);
        signUpBT.setOnAction(e->{
            Scene scene = new Scene(signUp.getSignUpLayout(),600,600);
            primaryStage.setScene(scene);

            primaryStage.show();
        });
        VBox vBox = new VBox(10,login.getLoginLayout(),signUpBT);
        pane.add(vBox, 0, 2);
        pane.setBackground(bGround);
        Scene scene = new Scene(pane, 1000, 1000);
        // Set the scene on the stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void enterAccount(String role) throws SQLException, ClassNotFoundException {
        if (role.equals("Doctor")){
            doctor.accountSystem();
        }else if (role.equals("Secretary")){

            secretary.accountSystem();
        }else {
            nurse.accountSystem();
        }
    }



}

