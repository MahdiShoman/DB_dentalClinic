import DB.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Nurse {
    private int NNM;
    private String name;
    private Date birthday;
    private String contactNumber;
    private String address;
    private int doctorID;

    public Nurse() {
    }

    public Nurse(int NNM, String name, Date birthday, String contactNumber, String address, int doctorID) {
        this.NNM = NNM;
        this.name = name;
        this.birthday = birthday;
        this.contactNumber = contactNumber;
        this.address = address;
        this.doctorID = doctorID;
    }

    public int getNNM() {
        return NNM;
    }

    public void setNNM(int NNM) {
        this.NNM = NNM;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    Stage stage = new Stage();

    void accountSystem() throws SQLException, ClassNotFoundException {
        TabPane tabPane = new TabPane();
        Tab appointment_ = new Tab("appointment_ Table",tableView());
        tabPane.getTabs().addAll(appointment_);
        // Create the scene
        Scene scene = new Scene(tabPane, 1100, 800);
        // Set the scene on the stage
        stage.setScene(scene);
        stage.show();

    }

    ConnectDB connectDB = new ConnectDB();
    private ArrayList<Appointment> data = new ArrayList<>();
    private ObservableList<Appointment> dataList;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public VBox tableView() throws SQLException, ClassNotFoundException {

        TableView<Appointment> myDataTable = new TableView<Appointment>();
        getData();
        dataList = FXCollections.observableArrayList(data);
        data = new ArrayList<>();
        Scene scene = new Scene(new Group());

        Label label = new Label("appointment Table");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.MEDIUMAQUAMARINE);

        myDataTable.setEditable(true);
        myDataTable.setMaxHeight(800);
        myDataTable.setMaxWidth(800);
        myDataTable.setFixedCellSize(35);


// name of column for display
        TableColumn<Appointment, Integer> appointmentCol = new TableColumn<Appointment, Integer>("appointmentID");
        appointmentCol.setMinWidth(50);
// to get the data from specific column
        appointmentCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));


        TableColumn<Appointment, String> dateAndTimeCol = new TableColumn<Appointment, String>("Date and Time");
        dateAndTimeCol.setMinWidth(50);
        dateAndTimeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("dateAndTime"));

        dateAndTimeCol.setCellFactory(TextFieldTableCell.<Appointment>forTableColumn());

        dateAndTimeCol.setOnEditCommit((TableColumn.CellEditEvent<Appointment, String> t) -> {
            ((Appointment) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDateAndTime(t.getNewValue());
            updateDate(t.getRowValue().getAppointmentID(), t.getNewValue());
        });

        // Set the font size for the cells in the dateAndTimeCol column
        dateAndTimeCol.setCellFactory(column -> {
            return new TextFieldTableCell<Appointment, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!isEmpty()) {
                        setStyle("-fx-font-size: 16px;"); // Change the font size as needed
                    }
                }
            };
        });

// name of column for display
        TableColumn<Appointment, Integer> patientIDCol = new TableColumn<Appointment, Integer>("patientID");
        patientIDCol.setMinWidth(50);
// to get the data from specific column
        patientIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("patientID"));

// name of column for display
        TableColumn<Appointment, Integer> doctorIDCol = new TableColumn<Appointment, Integer>("doctorID");
        doctorIDCol.setMinWidth(50);
// to get the data from specific column
        doctorIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("doctorID"));

        // name of column for display
        TableColumn<Appointment, Integer> nurseIDCol = new TableColumn<Appointment, Integer>("nurseID");
        nurseIDCol.setMinWidth(50);
// to get the data from specific column
        nurseIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("nurseID"));

        TableColumn<Appointment, String> appointmentConCol = new TableColumn<Appointment, String>("condition");
        appointmentConCol.setMinWidth(140);
        appointmentConCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Appointment_condition"));

        appointmentConCol.setCellFactory(TextFieldTableCell.<Appointment>forTableColumn());
        appointmentConCol.setOnEditCommit((TableColumn.CellEditEvent<Appointment, String> t) -> {
            ((Appointment) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setAppointment_condition((t.getNewValue()));
            updateCondition(t.getRowValue().getPatientID(), t.getNewValue());
        });



        myDataTable.setItems(dataList);
        myDataTable.getColumns().addAll(appointmentCol, dateAndTimeCol, doctorIDCol,nurseIDCol, patientIDCol,appointmentConCol);

        setColumnFontResize(appointmentCol);
        setColumnFontResize(dateAndTimeCol);
        setColumnFontResize(patientIDCol);
        setColumnFontResize(nurseIDCol);
        setColumnFontResize(doctorIDCol);
        setColumnFontResize(appointmentConCol);


        final Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction((ActionEvent e) -> {
            myDataTable.refresh();
        });

        final HBox hb2 = new HBox();
        hb2.getChildren().addAll(refreshButton);
        hb2.setAlignment(Pos.CENTER);
        hb2.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, myDataTable, hb2);
// vbox.getChildren().addAll(label, myDataTable);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        return vbox;

    }
    private <T> void setColumnFontResize(TableColumn<Appointment, T> column) {
        column.setCellFactory(cell -> new TableCell<Appointment, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (!isEmpty()) {
                    setStyle("-fx-font-size: 16px;"); // Change the font size as needed
                    setText(item.toString()); // Set the text to display
                }
            }
        });
    }


    // Connection con ;
    private void getData() throws SQLException, ClassNotFoundException {
// TODO Auto-generated method stub

        String SQL;

        connectDB.startDBConnection();
        System.out.println("Connection established");

        SQL = "select ANN,PNN,Doctor_DNN ,Nurse_NNM , DateAndTime,Appointment_Condition from Appointments order by ANN";
        Statement stmt = ConnectDB.connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next())
            data.add(new Appointment(Integer.parseInt(rs.getString(1)), rs.getString(5),
                    Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),Integer.parseInt(rs.getString(4)), rs.getString(6)));

        rs.close();
        stmt.close();

        connectDB.closeDBConnection();
        System.out.println("Connection closed" + data.size());

    }

    String notification;// to alerts


    public void updateCondition(int PNN, String Appointment_Condition) {

        try {
            notification = ("update  Appointments set Appointment_Condition = '" + Appointment_Condition
                    + "' where PNN = " + PNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Appointments set Appointment_Condition = '" + Appointment_Condition
                    + "' where PNN = " + PNN + ";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateDate(int ANN, String date) {

        try {

            connectDB.startDBConnection();
            connectDB
                    .ExecuteStatement("update  Appointments set DateAndTime = '" + date + "' where ANN = " + ANN + ";");
            connectDB.closeDBConnection();
            notification = ("update  Appointments set DateAndTime = '" + date + "' where ANN = " + ANN);
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
