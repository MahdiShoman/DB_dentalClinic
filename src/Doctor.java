import DB.ConnectDB;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.management.Notification;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Doctor {
    private int DNN;
    private String name;
    private String specialty;
    private String birthday;
    private String contactNumber;
    private String address;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    Doctor(){


    }
    public Doctor(int DNN, String name, String specialty, String birthday, String contactNumber, String address) {
        this.DNN = DNN;
        this.name = name;
        this.specialty = specialty;
        this.birthday = birthday;
        this.contactNumber = contactNumber;
        this.address = address;

    }

    public int getDNN() {
        return DNN;
    }

    public void setDNN(int DNN) {
        this.DNN = DNN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = String.valueOf(birthday);
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
    TabPane pane;
    private Image img;
    Patient patient = new Patient();
    Appointment appointment = new Appointment();
    Payment payment = new Payment();
    Diagnosis diagnosis = new Diagnosis();
    Prescription prescription = new Prescription();
    Treatment treatment = new Treatment();

    void accountSystem() throws SQLException, ClassNotFoundException {
        // Creating Background Image
        img = new Image("backgournd.jpg");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        TabPane tabPane = new TabPane();
        tabPane.setBackground(bGround);
        Tab patientTab = new Tab("patient");
        Tab appointmenTab = new Tab("appointment");
        Tab paymnetTab = new Tab("paymeny");
        Tab diganosisTab = new Tab("Diagnosis");
        Tab prescriptionTab = new Tab("prescription");
        Tab TreatmentTab = new Tab("Treatment");
        patientTab.setContent(patient.tableView());
        appointmenTab.setContent(appointment.tableView());
        paymnetTab.setContent(payment.tableView());
        diganosisTab.setContent(diagnosis.tableView());
        prescriptionTab.setContent(prescription.tableView());
        TreatmentTab.setContent(treatment.tableView());
        tabPane.getTabs().addAll(patientTab, appointmenTab, paymnetTab, diganosisTab, prescriptionTab, TreatmentTab);

        // Create the scene
        Scene scene = new Scene(tabPane, 600   , 600);
        // Set the scene on the stage
        stage.setScene(scene);

        stage.show();

    }

    ConnectDB connectDB = new ConnectDB();
    private ArrayList<Doctor> data = new ArrayList<>();
    private ObservableList<Doctor> dataList;

    public VBox tableView() throws SQLException, ClassNotFoundException {

        TableView<Doctor> myDataTable = new TableView<Doctor>();
        getData();
        dataList = FXCollections.observableArrayList(data);
        data = new ArrayList<>();
        Scene scene = new Scene(new Group());


        Label label = new Label("Doctors Table");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.MEDIUMAQUAMARINE);

        myDataTable.setEditable(true);
        myDataTable.setMaxHeight(600);
        myDataTable.setMaxWidth(600);




        // name of column for display
        TableColumn<Doctor, Integer> DNNCol =
                new TableColumn<Doctor, Integer>("DNN");
        DNNCol.setMinWidth(50);
        // to get the data from specific column
        DNNCol.setCellValueFactory(
                new PropertyValueFactory<Doctor, Integer>("DNN"));



        TableColumn<Doctor, String> nameCol = new TableColumn<Doctor, String>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("Name"));


        nameCol.setCellFactory(TextFieldTableCell.<Doctor>forTableColumn());
        nameCol.setOnEditCommit(
                (CellEditEvent<Doctor, String> t) -> {
                    ((Doctor) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue()); //display only
                    updateName( t.getRowValue().getDNN(),t.getNewValue());
                });

        TableColumn<Doctor, String> specialtyCol = new TableColumn<Doctor, String>("Specialty");
        specialtyCol.setMinWidth(100);
        specialtyCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("Specialty"));


        specialtyCol.setCellFactory(TextFieldTableCell.<Doctor>forTableColumn());
        specialtyCol.setOnEditCommit(
                (CellEditEvent<Doctor, String> t) -> {
                    ((Doctor) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setSpecialty(t.getNewValue());
                    updateSpecialty( t.getRowValue().getDNN(),t.getNewValue());
                });



        TableColumn<Doctor, String> birthdayCol = new TableColumn<Doctor, String>("Birthday");
        birthdayCol.setMinWidth(50);
        birthdayCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("Birthday"));


        birthdayCol.setCellFactory(TextFieldTableCell.<Doctor>
                forTableColumn());

        birthdayCol.setOnEditCommit(
                (CellEditEvent<Doctor, String> t) -> {
                    ((Doctor) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setBirthday(t.getNewValue());
                    updateBirthday( t.getRowValue().getDNN(),t.getNewValue());
                });

        TableColumn<Doctor, String> contactNumberCol = new TableColumn<Doctor, String>("ContactNumber");
        contactNumberCol.setMinWidth(100);
        contactNumberCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("ContactNumber"));


        contactNumberCol.setCellFactory(TextFieldTableCell.<Doctor>forTableColumn());
        contactNumberCol.setOnEditCommit(
                (CellEditEvent<Doctor, String> t) -> {
                    ((Doctor) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setContactNumber(t.getNewValue());
                    updateContactNumber( t.getRowValue().getDNN(),t.getNewValue());
                });



        TableColumn<Doctor, String> addressCol = new TableColumn<Doctor, String>("Address");
        addressCol.setMinWidth(100);
        addressCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("Address"));


        addressCol.setCellFactory(TextFieldTableCell.<Doctor>forTableColumn());
        addressCol.setOnEditCommit(
                (CellEditEvent<Doctor, String> t) -> {
                    ((Doctor) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAddress(t.getNewValue());
                    updateAddress( t.getRowValue().getDNN(),t.getNewValue());
                });



        myDataTable.setItems(dataList);

        myDataTable.getColumns().addAll(DNNCol, nameCol, birthdayCol, specialtyCol, contactNumberCol,addressCol);



        final TextField addDNN = new TextField();
        addDNN.setPromptText("DNN");
        addDNN.setMaxWidth(DNNCol.getPrefWidth());

        final TextField addName = new TextField();
        addName.setMaxWidth(nameCol.getPrefWidth());
        addName.setPromptText("Name");

        final TextField addBirthday = new TextField();
        addBirthday.setMaxWidth(birthdayCol.getPrefWidth());
        addBirthday.setPromptText("Birthday");

        final TextField addSpecialty = new TextField();
        addSpecialty.setMaxWidth(specialtyCol.getPrefWidth());
        addSpecialty.setPromptText("Specialty");

        final TextField addContactNumber = new TextField();
        addContactNumber.setMaxWidth(contactNumberCol.getPrefWidth());
        addContactNumber.setPromptText("Contact Number");

        final TextField addAddress = new TextField();
        addAddress.setMaxWidth(addressCol.getPrefWidth());
        addAddress.setPromptText("Address");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
         try {
                Doctor rc = new Doctor(
                        Integer.parseInt(addDNN.getText()),
                        addName.getText(),
                        addSpecialty.getText(),
                        addBirthday.getText(),
                        addContactNumber.getText(),
                        addAddress.getText());
                dataList.add(rc);
                insertData(rc);
                addDNN.clear();
                addName.clear();
                addBirthday.clear();
                addSpecialty.clear();
                addContactNumber.clear();
                addAddress.clear();
            } catch (NumberFormatException ex) {
             alert.setContentText("fill all information on fields");
             alert.show();
         }
        });

        final HBox hb = new HBox();


        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {
            ObservableList<Doctor> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Doctor> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                deleteRow(row);
                myDataTable.refresh();
            });
        });


        hb.getChildren().addAll(addDNN, addName,  addSpecialty,addBirthday, addContactNumber,addAddress, addButton, deleteButton);
        hb.setSpacing(3);


        final Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction((ActionEvent e) -> {
            myDataTable.refresh();
        });

//		Button ownedNoneButton = new Button("Owned None");
//		ownedNoneButton.setOnAction(c -> );

       // final Button clearButton = new Button("Clear All");
      /*  clearButton.setOnAction((ActionEvent e) -> {
            showDialog(stage, NONE, myDataTable);


        });*/



        final HBox hb2 = new HBox();
        hb2.getChildren().addAll( refreshButton);
        hb2.setAlignment(Pos.CENTER);
        hb2.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, myDataTable, hb,hb2);
        //	vbox.getChildren().addAll(label, myDataTable);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        return vbox;
    }
    String notification;// to alerts
    private void insertData(Doctor rc) {

        try {
             notification=("Insert into Doctors (DNN, Name, Specialty, Birthday, ContactNumber, Address) values("+
                    rc.getDNN()+",'"
                    +rc.getName()+"','"
                    + rc.getSpecialty() +"','"
                    + rc.getBirthday()+"', '"
                    + rc.getContactNumber()+"', '"
                    +rc.getAddress()+"')"+";");

            connectDB.startDBConnection();
            connectDB.ExecuteStatement("Insert into Doctors (DNN, Name, Specialty, Birthday, ContactNumber, Address) values("+
                    rc.getDNN()+",'"
                    +rc.getName()+"','"
                    + rc.getSpecialty() +"','"
                    + rc.getBirthday()+"', '"
                    + rc.getContactNumber()+"', '"
                    +rc.getAddress()+"')"+";");
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

    //Connection con ;
    private void getData() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;

        connectDB.startDBConnection();
        System.out.println("Connection established");

        SQL = "select DNN,Name,Specialty,Birthday,ContactNumber , Address from Doctors order by DNN";
        Statement stmt = ConnectDB.connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);


        while ( rs.next() )
            data.add(new Doctor(
                    Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)));

        rs.close();
        stmt.close();

        connectDB.closeDBConnection();
        System.out.println("Connection closed" + data.size());


    }
    public void updateName(int DNN, String name) {

        try {
            notification=("update  Doctors set name = '"+name + "' where DNN = "+DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Doctors set name = '"+name + "' where DNN = "+DNN+";");
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

    public void updateBirthday(int DNN, String birthday) {

        try {
            notification =("update  Doctors set birthday = "+birthday + " where DNN = "+DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Doctors set birthday = "+birthday + " where DNN = "+DNN+";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void updateSpecialty(int DNN, String specialty) {

        try {
            notification=("update  Doctors set specialty = '"+specialty + "' where DNN = "+DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Doctors set specialty = '"+specialty + "' where DNN = "+DNN+";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateContactNumber(int DNN, String contactNumber) {

        try {
            notification=("update  Doctors set contactNumber = '"+contactNumber + "' where DNN = "+DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Doctors set contactNumber = '"+contactNumber + "' where DNN = "+DNN+";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void updateAddress(int DNN, String address) {

        try {
            notification=("update  Doctors set address = '"+address + "' where DNN = "+DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Doctors set address = '"+address + "' where DNN = "+DNN+";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void deleteRow(Doctor row) {
        // TODO Auto-generated method stub
        try {
            notification=("delete from  Doctors where DNN="+row.getDNN() + ";");
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("delete from  Doctors where DNN="+row.getDNN() + ";");
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
