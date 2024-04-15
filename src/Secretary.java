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

public class Secretary {
    private int SSN; // Secretary Social Security Number
    private String name;
    private Date birthday;
    private String contactNumber;
    private String address;

    public Secretary() {
    }

    public Secretary(int SSN, String name, Date birthday, String contactNumber, String address) {
        this.SSN = SSN;
        this.name = name;
        this.birthday = birthday;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
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

    Stage stage = new Stage();
    Appointment appointment = new Appointment();
    Patient patient = new Patient();
    void accountSystem() throws SQLException, ClassNotFoundException {
        TabPane tabPane = new TabPane();
        Tab appointment_ = new Tab("appointment_ Table",appointment.tableView());
        Tab patient_ = new Tab("patient_ Table",patient.tableView());
        tabPane.getTabs().addAll(appointment_,patient_);
        // Create the scene
        Scene scene = new Scene(tabPane, 1100, 800);
        // Set the scene on the stage
        stage.setScene(scene);
        stage.show();
        Treatment treatment = new Treatment();
        treatment.sameTreat();
        Payment payment = new Payment();
        System.out.println(payment.Revenue());

    }
    /*ConnectDB connectDB = new ConnectDB();
    private ArrayList<EquipmentOrder> data = new ArrayList<>();
    private ObservableList<EquipmentOrder> dataList;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public VBox tableView() throws SQLException, ClassNotFoundException {

        TableView<EquipmentOrder> myDataTable = new TableView<EquipmentOrder>();
        getData();
        dataList = FXCollections.observableArrayList(data);
        data = new ArrayList<>();
        Scene scene = new Scene(new Group());

        Label label = new Label("Equipment Order Table");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.MEDIUMAQUAMARINE);

        myDataTable.setEditable(true);
        myDataTable.setMaxHeight(800);
        myDataTable.setMaxWidth(800);
        myDataTable.setFixedCellSize(35);


// name of column for display
        TableColumn<EquipmentOrder, Integer> orderIDCol = new TableColumn<EquipmentOrder, Integer>("orderID");
        orderIDCol.setMinWidth(50);
// to get the data from specific column
        orderIDCol.setCellValueFactory(new PropertyValueFactory<EquipmentOrder, Integer>("orderID"));


        TableColumn<EquipmentOrder, String> orderDateCol = new TableColumn<EquipmentOrder, String>("orderDate");
        orderDateCol.setMinWidth(50);
        orderDateCol.setCellValueFactory(new PropertyValueFactory<EquipmentOrder, String>("orderDate"));

        orderDateCol.setCellFactory(TextFieldTableCell.<EquipmentOrder>forTableColumn());

        orderDateCol.setOnEditCommit((TableColumn.CellEditEvent<EquipmentOrder, String> t) -> {
            ((EquipmentOrder) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setOrderDate(t.getNewValue());
            updateDate(t.getRowValue().getOrderID(), t.getNewValue());
        });

        // Set the font size for the cells in the dateAndTimeCol column
        *//*orderDateCol.setCellFactory(column -> {
            return new TextFieldTableCell<EquipmentOrder, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!isEmpty()) {
                        setStyle("-fx-font-size: 16px;"); // Change the font size as needed
                    }
                }
            };
        });*//*

// name of column for display
        TableColumn<EquipmentOrder, Integer> patientIDCol = new TableColumn<EquipmentOrder, Integer>("patientID");
        patientIDCol.setMinWidth(50);
// to get the data from specific column
        patientIDCol.setCellValueFactory(new PropertyValueFactory<EquipmentOrder, Integer>("patientID"));

// name of column for display
        TableColumn<EquipmentOrder, Integer> doctorIDCol = new TableColumn<EquipmentOrder, Integer>("doctorID");
        doctorIDCol.setMinWidth(50);
// to get the data from specific column
        doctorIDCol.setCellValueFactory(new PropertyValueFactory<EquipmentOrder, Integer>("doctorID"));

        // name of column for display
        TableColumn<EquipmentOrder, Integer> nurseIDCol = new TableColumn<EquipmentOrder, Integer>("nurseID");
        nurseIDCol.setMinWidth(50);
// to get the data from specific column
        nurseIDCol.setCellValueFactory(new PropertyValueFactory<EquipmentOrder, Integer>("nurseID"));

        TableColumn<EquipmentOrder, String> descriptionCol = new TableColumn<EquipmentOrder, String>("description");
        descriptionCol.setMinWidth(140);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<EquipmentOrder, String>("description"));

        descriptionCol.setCellFactory(TextFieldTableCell.<EquipmentOrder>forTableColumn());
        descriptionCol.setOnEditCommit((TableColumn.CellEditEvent<EquipmentOrder, String> t) -> {
            ((EquipmentOrder) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDescription((t.getNewValue()));
            updateCondition(t.getRowValue().getOrderID(), t.getNewValue());
        });



        myDataTable.setItems(dataList);
        myDataTable.getColumns().addAll(appointmentCol, dateAndTimeCol, doctorIDCol,nurseIDCol, patientIDCol,appointmentConCol);

        setColumnFontResize(appointmentCol);
        setColumnFontResize(dateAndTimeCol);
        setColumnFontResize(patientIDCol);
        setColumnFontResize(nurseIDCol);
        setColumnFontResize(doctorIDCol);
        setColumnFontResize(appointmentConCol);

        final TextField addAppitment = new TextField();
        addAppitment.setPromptText("Appointment number");
        addAppitment.setMinWidth(appointmentCol.getPrefWidth());

        final TextField addDate = new TextField();
        addDate.setMinWidth(dateAndTimeCol.getPrefWidth());
        addDate.setPromptText("Date and Time");

        final TextField addPatientID = new TextField();
        addPatientID.setMinWidth(patientIDCol.getPrefWidth());
        addPatientID.setPromptText("patient ID");

        final TextField addDoctorID = new TextField();
        addDoctorID.setMinWidth(doctorIDCol.getPrefWidth());
        addDoctorID.setPromptText("doctor ID");

        final TextField addNurseID = new TextField();
        addNurseID.setMinWidth(doctorIDCol.getPrefWidth());
        addNurseID.setPromptText("nurse ID");

        final TextField addCond = new TextField();
        addCond.setMinWidth(dateAndTimeCol.getPrefWidth());
        addCond.setPromptText("Condition");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                Appointment rc = new Appointment(Integer.parseInt(addAppitment.getText()), addDate.getText(),
                        Integer.parseInt(addPatientID.getText()), Integer.parseInt(addDoctorID.getText()),Integer.parseInt(addNurseID.getText()),
                        addCond.getText());
                dataList.add(rc);
                insertData(rc);
                addAppitment.clear();
                addDate.clear();
                addPatientID.clear();
                addDoctorID.clear();
                addNurseID.clear();
            } catch (NumberFormatException ex) {
                alert.setContentText("fill all information on fields");
                alert.show();
            }
        });



        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {
            ObservableList<Appointment> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Appointment> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                deleteRow(row);
                myDataTable.refresh();
            });
        });
        final HBox hb = new HBox();
        hb.getChildren().addAll( addAppitment, addDate, addPatientID, addDoctorID,addNurseID,addCond, addButton, deleteButton);
        hb.setSpacing(3);

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
        vbox.getChildren().addAll(label, myDataTable, hb, hb2);
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

    private void insertData(Appointment rc) {

        try {
            notification = ("Insert into Appointments (ANN, PNN, Doctor_DNN,Nurse_NNM, DateAndTime, Appointment_Condition) values("
                    + rc.getAppointmentID() + ",'" + rc.getPatientID() + "','" + rc.getDoctorID() + "', '"+ rc.getNurseID() + "', '"
                    + rc.getDateAndTime() + "', '" + rc.getAppointment_condition() + "')" + ";");

            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "Insert into Appointments (ANN, PNN, Doctor_DNN,Nurse_NNM, DateAndTime, Appointment_Condition) values("
                            + rc.getAppointmentID() + ",'" + rc.getPatientID() + "','" + rc.getDoctorID() + "', '"+ rc.getNurseID() + "', '"
                            + rc.getDateAndTime() + "', '" + rc.getAppointment_condition() + "')" + ";");

            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed" + data.size());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCondition(int PNN, String Appointment_Condition) {

        try {
            notification = ("update  Equipment set description = '" + Appointment_Condition
                    + "' where PNN = " + PNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Equipment set description = '" + Appointment_Condition
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

    private void deleteRow(Appointment row) {
// TODO Auto-generated method stub
        try {
            notification = ("delete from  Appointments where ANN=" + row.getAppointmentID() + ";");
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("delete from  Appointments where ANN=" + row.getAppointmentID() + ";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/
}
