import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import DB.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Appointment {
    private int appointmentID;
    private String dateAndTime;
    private int patientID;
    private int doctorID;
    private int nurseID;
    private String appointment_condition;


    Appointment() {

    }

    public Appointment(int appointmentID, String dateAndTime, int patientID, int doctorID, int nurseID,
                       String appointment_condition) {
        this.appointmentID = appointmentID;
        this.dateAndTime = dateAndTime;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.nurseID=nurseID;
        this.appointment_condition = appointment_condition;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getNurseID() {
        return nurseID;
    }

    public void setNurseID(int nurseID) {
        this.nurseID = nurseID;
    }

    public String getAppointment_condition() {
        return appointment_condition;
    }

    public void setAppointment_condition(String appointment_condition) {
        this.appointment_condition = appointment_condition;
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

        dateAndTimeCol.setOnEditCommit((CellEditEvent<Appointment, String> t) -> {
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
        appointmentConCol.setOnEditCommit((CellEditEvent<Appointment, String> t) -> {
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
    }

}