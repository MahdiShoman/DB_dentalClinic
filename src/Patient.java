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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Patient {
    private int PNN;
    private String name;
    private String birthday;
    private String contactNumber;
    private String address;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    Patient() {

    }

    public Patient(int PNN, String name, String birthday, String contactNumber, String address) {
        this.PNN = PNN;
        this.name = name;
        this.birthday = birthday;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public int getPNN() {
        return PNN;
    }

    public void setPNN(int PNN) {
        this.PNN = PNN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    ConnectDB connectDB = new ConnectDB();
    private ArrayList<Patient> data = new ArrayList<>();
    private ObservableList<Patient> dataList;

    public VBox tableView() throws SQLException, ClassNotFoundException {

        TableView<Patient> myDataTable = new TableView<Patient>();
        getData();
        dataList = FXCollections.observableArrayList(data);
        data = new ArrayList<>();
        Scene scene = new Scene(new Group());

        Label label = new Label("Patient Table");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.MEDIUMAQUAMARINE);

        myDataTable.setEditable(true);
        myDataTable.setMaxHeight(600);
        myDataTable.setMaxWidth(600);

        // name of column for display
        TableColumn<Patient, Integer> PNNCol = new TableColumn<Patient, Integer>("PNN");
        PNNCol.setMinWidth(50);
        // to get the data from specific column
        PNNCol.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("PNN"));

        TableColumn<Patient, String> nameCol = new TableColumn<Patient, String>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Name"));

        nameCol.setCellFactory(TextFieldTableCell.<Patient>forTableColumn());
        nameCol.setOnEditCommit((CellEditEvent<Patient, String> t) -> {
            ((Patient) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue()); // display
            // only
            updateName(t.getRowValue().getPNN(), t.getNewValue());
        });

        TableColumn<Patient, String> birthdayCol = new TableColumn<Patient, String>("Birthday");
        birthdayCol.setMinWidth(50);
        birthdayCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Birthday"));

        birthdayCol.setCellFactory(TextFieldTableCell.<Patient>forTableColumn());

        birthdayCol.setOnEditCommit((CellEditEvent<Patient, String> t) -> {
            ((Patient) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBirthday(t.getNewValue());
            updateBirthday(t.getRowValue().getPNN(), t.getNewValue());
        });

        TableColumn<Patient, String> contactNumberCol = new TableColumn<Patient, String>("ContactNumber");
        contactNumberCol.setMinWidth(100);
        contactNumberCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("ContactNumber"));

        contactNumberCol.setCellFactory(TextFieldTableCell.<Patient>forTableColumn());
        contactNumberCol.setOnEditCommit((CellEditEvent<Patient, String> t) -> {
            ((Patient) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setContactNumber(t.getNewValue());
            updateContactNumber(t.getRowValue().getPNN(), t.getNewValue());
        });

        TableColumn<Patient, String> addressCol = new TableColumn<Patient, String>("Address");
        addressCol.setMinWidth(100);
        addressCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("Address"));

        addressCol.setCellFactory(TextFieldTableCell.<Patient>forTableColumn());
        addressCol.setOnEditCommit((CellEditEvent<Patient, String> t) -> {
            ((Patient) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAddress(t.getNewValue());
            updateAddress(t.getRowValue().getPNN(), t.getNewValue());
        });

        myDataTable.setItems(dataList);
        myDataTable.getColumns().addAll(PNNCol, nameCol, birthdayCol, contactNumberCol, addressCol);

        final TextField addDNN = new TextField();
        addDNN.setPromptText("PNN");
        addDNN.setMaxWidth(PNNCol.getPrefWidth());

        final TextField addName = new TextField();
        addName.setMaxWidth(nameCol.getPrefWidth());
        addName.setPromptText("Name");

        final TextField addBirthday = new TextField();
        addBirthday.setMaxWidth(birthdayCol.getPrefWidth());
        addBirthday.setPromptText("Birthday");

        final TextField addContactNumber = new TextField();
        addContactNumber.setMaxWidth(contactNumberCol.getPrefWidth());
        addContactNumber.setPromptText("Contact Number");

        final TextField addAddress = new TextField();
        addAddress.setMaxWidth(addressCol.getPrefWidth());
        addAddress.setPromptText("Address");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                Patient rc = new Patient(Integer.parseInt(addDNN.getText()), addName.getText(), addBirthday.getText(),
                        addContactNumber.getText(), addAddress.getText());
                dataList.add(rc);
                insertData(rc);
                addDNN.clear();
                addName.clear();
                addBirthday.clear();
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
            ObservableList<Patient> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Patient> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                deleteRow(row);
                myDataTable.refresh();
            });
        });

        hb.getChildren().addAll(addDNN, addName, addBirthday, addContactNumber, addAddress, addButton, deleteButton);
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

    private void insertData(Patient rc) {

        try {
            notification = ("Insert into Patients (PNN, Name, Birthday, ContactNumber, Address) values(" + rc.getPNN()
                    + ",'" + rc.getName() + "','" + rc.getBirthday() + "', '" + rc.getContactNumber() + "', '"
                    + rc.getAddress() + "')" + ";");

            connectDB.startDBConnection();
            connectDB.ExecuteStatement("Insert into Patients (PNN, Name, Birthday, ContactNumber, Address) values("
                    + rc.getPNN() + ",'" + rc.getName() + "','" + rc.getBirthday() + "', '" + rc.getContactNumber()
                    + "', '" + rc.getAddress() + "')" + ";");
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

    // Connection con ;
    private void getData() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;

        connectDB.startDBConnection();
        System.out.println("Connection established");

        SQL = "select PNN,Name,Birthday,ContactNumber , Address from Patients order by PNN";
        Statement stmt = ConnectDB.connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next())
            data.add(new Patient(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5)));

        rs.close();
        stmt.close();

        connectDB.closeDBConnection();
        System.out.println("Connection closed" + data.size());

    }

    String notification;// to alerts

    public void updateName(int PNN, String name) {

        try {
            notification = ("update  Patients set name = '" + name + "' where PNN = " + PNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Doctors set name = '" + name + "' where PNN = " + PNN + ";");
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

    public void updateBirthday(int PNN, String birthday) {

        try {
            notification = ("update  Patients set birthday = " + birthday + " where PNN = " + PNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Patients set birthday = " + birthday + " where PNN = " + PNN + ";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateContactNumber(int PNN, String contactNumber) {

        try {
            notification = ("update  Patients set contactNumber = '" + contactNumber + "' where PNN = " + PNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "update  Patients set contactNumber = '" + contactNumber + "' where PNN = " + PNN + ";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateAddress(int PNN, String address) {

        try {
            notification = ("update  Patients set address = '" + address + "' where PNN = " + PNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Patients set address = '" + address + "' where PNN = " + PNN + ";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteRow(Patient row) {
        // TODO Auto-generated method stub
        try {
            notification = ("delete from  Patients where PNN=" + row.getPNN() + ";");
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("delete from  Patients where PNN=" + row.getPNN() + ";");
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


    public void CashVsDeb() {


        String SQL;
        try {
            SQL = "select  Way , COUNT(*) as paymentCount from Payments GROUP BY Way";
            connectDB.startDBConnection();
            Statement stmt = ConnectDB.connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            rs.close();
            stmt.close();

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

    public String Revenue() {
        String SQL;
        try {
            SQL = "SELECT SUM(Amount) AS TotalRevenue FROM Payments;";
            connectDB.startDBConnection();
            Statement stmt = ConnectDB.connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next())
                return rs.getString(1);
            rs.close();
            stmt.close();

            connectDB.closeDBConnection();

            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;

    }


}
