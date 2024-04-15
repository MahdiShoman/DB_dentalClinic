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

public class Prescription {
    private int DNN;
    private String time;
    private String DrugName;
    private String dosAge;
    private String HowtoUse;
    private int durationOfTreat;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    Prescription() {

    }

    public Prescription(int DNN, String time, String drugName, String dosAge, String howtoUse, int durationOfTreat) {
        super();
        this.DNN = DNN;
        this.time = time;
        DrugName = drugName;
        this.dosAge = dosAge;
        HowtoUse = howtoUse;
        this.durationOfTreat = durationOfTreat;
    }

    public int getDNN() {
        return DNN;
    }

    public void setDNN(int dNN) {
        DNN = dNN;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDrugName() {
        return DrugName;
    }

    public void setDrugName(String drugName) {
        DrugName = drugName;
    }

    public String getDosAge() {
        return dosAge;
    }

    public void setDosAge(String dosAge) {
        this.dosAge = dosAge;
    }

    public String getHowtoUse() {
        return HowtoUse;
    }

    public void setHowtoUse(String howtoUse) {
        HowtoUse = howtoUse;
    }

    public int getDurationOfTreat() {
        return durationOfTreat;
    }

    public void setDurationOfTreat(int durationOfTreat) {
        this.durationOfTreat = durationOfTreat;
    }

    ConnectDB connectDB = new ConnectDB();
    private ArrayList<Prescription> data = new ArrayList<>();
    private ObservableList<Prescription> dataList;

    public VBox tableView() throws SQLException, ClassNotFoundException {

        TableView<Prescription> myDataTable = new TableView<Prescription>();
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
        TableColumn<Prescription, Integer> DNNCol = new TableColumn<Prescription, Integer>("DNN");
        DNNCol.setMinWidth(50);
        // to get the data from specific column
        DNNCol.setCellValueFactory(new PropertyValueFactory<Prescription, Integer>("DNN"));

        TableColumn<Prescription, String> TimeCol = new TableColumn<Prescription, String>("time");
        TimeCol.setMinWidth(100);
        TimeCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("Time"));

        TimeCol.setCellFactory(TextFieldTableCell.<Prescription>forTableColumn());
        TimeCol.setOnEditCommit((CellEditEvent<Prescription, String> t) -> {
            ((Prescription) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTime(t.getNewValue()); // display
            // only
            updateTime(t.getRowValue().getDNN(), t.getNewValue());
        });

        TableColumn<Prescription, String> DrugNameCol = new TableColumn<Prescription, String>("Drug Name");
        DrugNameCol.setMinWidth(100);
        DrugNameCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("DrugName"));

        DrugNameCol.setCellFactory(TextFieldTableCell.<Prescription>forTableColumn());
        DrugNameCol.setOnEditCommit((CellEditEvent<Prescription, String> t) -> {
            ((Prescription) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDrugName(t.getNewValue()); // display
            // only
            updateDrugName(t.getRowValue().getDNN(), t.getNewValue());
        });

        TableColumn<Prescription, String> DosageCol = new TableColumn<Prescription, String>("Dosage");
        DosageCol.setMinWidth(100);
        DosageCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("DosAge"));

        DosageCol.setCellFactory(TextFieldTableCell.<Prescription>forTableColumn());
        DosageCol.setOnEditCommit((CellEditEvent<Prescription, String> t) -> {
            ((Prescription) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDosAge(t.getNewValue()); // display
            // only
            updateDosAge(t.getRowValue().getDNN(), t.getNewValue());
        });

        TableColumn<Prescription, String> useCol = new TableColumn<Prescription, String>("how to use");
        useCol.setMinWidth(100);
        useCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("HowtoUse"));

        useCol.setCellFactory(TextFieldTableCell.<Prescription>forTableColumn());
        useCol.setOnEditCommit((CellEditEvent<Prescription, String> t) -> {
            ((Prescription) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setHowtoUse(t.getNewValue()); // display
            // only
            updateUsage(t.getRowValue().getDNN(), t.getNewValue());
        });

        TableColumn<Prescription, Integer> durationCol = new TableColumn<Prescription, Integer>("duration");
        durationCol.setMinWidth(50);
        // to get the data from specific column
        durationCol.setCellValueFactory(new PropertyValueFactory<Prescription, Integer>("DurationOfTreat"));

        myDataTable.setItems(dataList);
        myDataTable.getColumns().addAll(DNNCol, TimeCol, DrugNameCol, DosageCol, useCol, durationCol);

        final TextField addDNN = new TextField();
        addDNN.setPromptText("DNN");
        addDNN.setMaxWidth(DNNCol.getPrefWidth());

        final TextField addTime = new TextField();
        addTime.setMaxWidth(TimeCol.getPrefWidth());
        addTime.setPromptText("Name");

        final TextField addDrug = new TextField();
        addDrug.setMaxWidth(DrugNameCol.getPrefWidth());
        addDrug.setPromptText("drug name");

        final TextField addDosAge = new TextField();
        addDosAge.setMaxWidth(DosageCol.getPrefWidth());
        addDosAge.setPromptText("Dosage");

        final TextField addUseage = new TextField();
        addUseage.setMaxWidth(useCol.getPrefWidth());
        addUseage.setPromptText("usege");

        final TextField addDuration = new TextField();
        addDuration.setMaxWidth(useCol.getPrefWidth());
        addDuration.setPromptText("duration");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                Prescription rc = new Prescription(Integer.parseInt(addDNN.getText()), addTime.getText(),
                        addDrug.getText(), addDosAge.getText(), addUseage.getText(),
                        Integer.parseInt(addDuration.getText()));
                dataList.add(rc);
                insertData(rc);
                addDNN.clear();
                addTime.clear();
                addDrug.clear();
                addDosAge.clear();
                addUseage.clear();
                addDuration.clear();
            } catch (NumberFormatException ex) {
                alert.setContentText("fill all information on fields");
                alert.show();
            }
        });

        final HBox hb = new HBox();

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {
            ObservableList<Prescription> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Prescription> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                deleteRow(row);
                myDataTable.refresh();
            });
        });

        hb.getChildren().addAll(addDNN, addTime, addDrug, addDosAge, addUseage, addDuration, addButton, deleteButton);
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

    // Connection con ;
    private void getData() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;

        connectDB.startDBConnection();
        System.out.println("Connection established");

        SQL = "select DNN,TimeOfPrescription,DrugName,Dosage,WayToUse,DurationOfTreatment from Prescriptions order by DNN";
        Statement stmt = ConnectDB.connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next())
            data.add(new Prescription(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6))));

        rs.close();
        stmt.close();

        connectDB.closeDBConnection();
        System.out.println("Connection closed" + data.size());

    }

    private void insertData(Prescription rc) {

        try {
            notification = ("INSERT INTO Prescriptions (DNN, TimeOfPrescription, DrugName, Dosage, WayToUse, DurationOfTreatment) VALUES ("
                    + rc.getDNN() + ",'" + rc.getTime() + "','" + rc.getDrugName() + "', '" + rc.getDosAge() + "', '"
                    + rc.getHowtoUse() + "', '" + rc.getDurationOfTreat() + "')" + ";");

            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "INSERT INTO Prescriptions (DNN, TimeOfPrescription, DrugName, Dosage, WayToUse, DurationOfTreatment) VALUES ("
                            + rc.getDNN() + ",'" + rc.getTime() + "','" + rc.getDrugName() + "', '" + rc.getDosAge()
                            + "', '" + rc.getHowtoUse() + "', '" + rc.getDurationOfTreat() + "')" + ";");

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

    String notification;// to alerts

    public void updateTime(int DNN, String TimeOfPrescription) {

        try {
            notification = ("update  Prescriptions set TimeOfPrescription = '" + TimeOfPrescription + "' where DNN = "
                    + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Prescriptions set TimeOfPrescription = '" + TimeOfPrescription
                    + "' where DNN = " + DNN + ";");
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

    // updateDrugName

    public void updateDrugName(int DNN, String DrugName) {

        try {
            notification = ("update  Prescriptions set DrugName = '" + DrugName + "' where DNN = " + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "update  Prescriptions set DrugName = '" + DrugName + "' where DNN = " + DNN + ";");
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

    public void updateDosAge(int DNN, String DosAge) {

        try {
            notification = ("update  Prescriptions set Dosage = '" + DosAge + "' where DNN = " + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Prescriptions set Dosge = '" + DosAge + "' where DNN = " + DNN + ";");
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

    public void updateUsage(int DNN, String use) {

        try {
            notification = ("update  Prescriptions set WayToUse = '" + use + "' where DNN = " + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Prescriptions set WayToUse = '" + use + "' where DNN = " + DNN + ";");
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

    private void deleteRow(Prescription row) {
        // TODO Auto-generated method stub
        try {
            notification = ("delete from  Prescriptions where DNN=" + row.getDNN() + ";");
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("delete from  Prescriptions where DNN=" + row.getDNN() + ";");
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
