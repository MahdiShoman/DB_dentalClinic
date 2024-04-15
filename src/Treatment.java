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

public class Treatment {
    private int DNN;
    private String description;
    private String time;
    private String nameOfTreat;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    Treatment() {

    }

    public Treatment(int dNN, String time, String nameOfTreat, String description) {
        super();
        DNN = dNN;
        this.description = description;
        this.time = time;
        this.nameOfTreat = nameOfTreat;
    }

    public int getDNN() {
        return DNN;
    }

    public void setDNN(int dNN) {
        DNN = dNN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNameOfTreat() {
        return nameOfTreat;
    }

    public void setNameOfTreat(String nameOfTreat) {
        this.nameOfTreat = nameOfTreat;
    }

    ConnectDB connectDB = new ConnectDB();
    private ArrayList<Treatment> data = new ArrayList<>();
    private ObservableList<Treatment> dataList;

    public VBox tableView() throws SQLException, ClassNotFoundException {

        TableView<Treatment> myDataTable = new TableView<Treatment>();
        // myDataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        getData();
        dataList = FXCollections.observableArrayList(data);
        data = new ArrayList<>();
        Scene scene = new Scene(new Group());

        Label label = new Label("diagnosis Table");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.MEDIUMAQUAMARINE);

        myDataTable.setEditable(true);
        myDataTable.setMaxHeight(600);
        myDataTable.setMaxWidth(600);

        // name of column for display
        TableColumn<Treatment, Integer> DNNCol = new TableColumn<Treatment, Integer>("DNN");
        DNNCol.setMinWidth(50);
        // to get the data from specific column
        DNNCol.setCellValueFactory(new PropertyValueFactory<Treatment, Integer>("DNN"));

        TableColumn<Treatment, String> descrCol = new TableColumn<Treatment, String>("description");
        descrCol.setMinWidth(100);
        descrCol.setCellValueFactory(new PropertyValueFactory<Treatment, String>("description"));

        descrCol.setCellFactory(TextFieldTableCell.<Treatment>forTableColumn());
        descrCol.setOnEditCommit((CellEditEvent<Treatment, String> t) -> {
            ((Treatment) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDescription(t.getNewValue()); // display
            // only
            updateDescription(t.getRowValue().getDNN(), t.getNewValue());
        });

        TableColumn<Treatment, String> TimeCol = new TableColumn<Treatment, String>("time");
        TimeCol.setMinWidth(100);
        TimeCol.setCellValueFactory(new PropertyValueFactory<Treatment, String>("Time"));

        TimeCol.setCellFactory(TextFieldTableCell.<Treatment>forTableColumn());
        TimeCol.setOnEditCommit((CellEditEvent<Treatment, String> t) -> {
            ((Treatment) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTime(t.getNewValue()); // display
            // only
            updateTime(t.getRowValue().getDNN(), t.getNewValue());
        });

        TableColumn<Treatment, String> NameCol = new TableColumn<Treatment, String>("Name Of Treat");
        NameCol.setMinWidth(100);
        NameCol.setCellValueFactory(new PropertyValueFactory<Treatment, String>("NameOfTreat"));

        NameCol.setCellFactory(TextFieldTableCell.<Treatment>forTableColumn());
        NameCol.setOnEditCommit((CellEditEvent<Treatment, String> t) -> {
            ((Treatment) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setNameOfTreat(t.getNewValue()); // display
            // only
            updateName(t.getRowValue().getDNN(), t.getNewValue());
        });

        myDataTable.setItems(dataList);
        myDataTable.getColumns().addAll(DNNCol, TimeCol, NameCol, descrCol);

        final TextField addDNN = new TextField();
        addDNN.setPromptText("PNN");
        addDNN.setMaxWidth(DNNCol.getPrefWidth());

        final TextField addTime = new TextField();
        addTime.setMaxWidth(TimeCol.getPrefWidth());
        addTime.setPromptText("Name");

        final TextField addName = new TextField();
        addName.setMaxWidth(NameCol.getPrefWidth());
        addName.setPromptText("name");

        final TextField addDescr = new TextField();
        addDescr.setMaxWidth(addDescr.getPrefWidth());
        addDescr.setPromptText("description");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                Treatment rc = new Treatment(Integer.parseInt(addDNN.getText()), addTime.getText(), addName.getText(),
                        addDescr.getText());
                dataList.add(rc);
                insertData(rc);
                addDNN.clear();
                addName.clear();
                addTime.clear();
                addDescr.clear();
            } catch (NumberFormatException ex) {
                alert.setContentText("fill all information on fields");
                alert.show();
            }
        });

        final HBox hb = new HBox();

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {
            ObservableList<Treatment> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Treatment> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                deleteRow(row);
                myDataTable.refresh();
            });
        });

        hb.getChildren().addAll(addDNN, addTime, addName, addDescr, addButton, deleteButton);
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

    private void insertData(Treatment rc) {

        try {
            notification = ("INSERT INTO Treatments (DNN, TimeOfTreatment, Name, Description) VALUES(" + rc.getDNN()
                    + ",'" + rc.getTime() + "','" + rc.getNameOfTreat() + "', '" + rc.getDescription() + "')" + ";");

            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "INSERT INTO Treatments (DNN, TimeOfTreatment, Name, Description) VALUES(" + rc.getDNN() + ",'"
                            + rc.getTime() + "','" + rc.getNameOfTreat() + "', '" + rc.getDescription() + "')" + ";");

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

        SQL = "select DNN , TimeOfTreatment , Name , Description from Treatments order by DNN";
        Statement stmt = ConnectDB.connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            data.add(new Treatment(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
                    rs.getString(4)));
        }

        rs.close();
        stmt.close();

        connectDB.closeDBConnection();
        System.out.println("Connection closed" + data.size());

    }

    String notification;// to alerts

    public void updateDescription(int DNN, String Description) {

        try {
            notification = ("update  Treatments set Description = '" + Description + "' where DNN = " + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "update  Treatments set Description = '" + Description + "' where DNN = " + DNN + ";");
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

    public void updateTime(int DNN, String TIme) {

        try {
            notification = ("update  Treatments set TimeOfTreatment = '" + TIme + "' where DNN = " + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "update  Treatments set TimeOfTreatment = '" + TIme + "' where DNN = " + DNN + ";");
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

    public void updateName(int DNN, String name) {

        try {
            notification = ("update  Treatments set Name = '" + name + "' where DNN = " + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Treatments set Name = '" + name + "' where DNN = " + DNN + ";");
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

    private void deleteRow(Treatment row) {
        // TODO Auto-generated method stub
        try {
            notification = ("delete from  Treatments where DNN=" + row.getDNN() + ";");
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("delete from  Treatments where DNN=" + row.getDNN() + ";");
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


    public void sameTreat() {
        String SQL;
        try {
            SQL = "SELECT Name, Description, COUNT(DISTINCT PNN) AS NumberOfPatients\r\n" + "FROM Treatments\r\n"
                    + "JOIN Diagnosis ON Treatments.DNN = Diagnosis.DNN\r\n"
                    + "JOIN Appointments ON Diagnosis.ANN = Appointments.ANN\r\n" + "GROUP BY Name, Description;";
            connectDB.startDBConnection();
            Statement stmt = ConnectDB.connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
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

}
