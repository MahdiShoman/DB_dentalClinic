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

public class Diagnosis {
    private int DNN;
    private int ANN;
    private String Diagnosis_Condition;
    private String analysis;
    private String cause;
    private float price;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public Diagnosis() {

    }

    public Diagnosis(int dNN, int aNN, String diagnosis_Condition, String analysis, String cause, float price) {
        super();
        DNN = dNN;
        ANN = aNN;
        Diagnosis_Condition = diagnosis_Condition;
        this.analysis = analysis;
        this.cause = cause;
        this.price = price;
    }

    public int getDNN() {
        return DNN;
    }

    public void setDNN(int dNN) {
        DNN = dNN;
    }

    public int getANN() {
        return ANN;
    }

    public void setANN(int aNN) {
        ANN = aNN;
    }

    public String getDiagnosis_Condition() {
        return Diagnosis_Condition;
    }

    public void setDiagnosis_Condition(String diagnosis_Condition) {
        Diagnosis_Condition = diagnosis_Condition;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    ConnectDB connectDB = new ConnectDB();
    private ArrayList<Diagnosis> data = new ArrayList<>();
    private ObservableList<Diagnosis> dataList;

    public VBox tableView() throws SQLException, ClassNotFoundException {

        TableView<Diagnosis> myDataTable = new TableView<Diagnosis>();
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
        TableColumn<Diagnosis, Integer> DNNCol = new TableColumn<Diagnosis, Integer>("DNN");
        DNNCol.setMinWidth(50);
        // to get the data from specific column
        DNNCol.setCellValueFactory(new PropertyValueFactory<Diagnosis, Integer>("DNN"));

        // name of column for display
        TableColumn<Diagnosis, Integer> ANNCol = new TableColumn<Diagnosis, Integer>("ANN");
        ANNCol.setMinWidth(50);
        // to get the data from specific column
        ANNCol.setCellValueFactory(new PropertyValueFactory<Diagnosis, Integer>("ANN"));

        TableColumn<Diagnosis, String> condCol = new TableColumn<Diagnosis, String>("Diagnosis_Condition");
        condCol.setMinWidth(100);
        condCol.setCellValueFactory(new PropertyValueFactory<Diagnosis, String>("Diagnosis_Condition"));

        condCol.setCellFactory(TextFieldTableCell.<Diagnosis>forTableColumn());
        condCol.setOnEditCommit((CellEditEvent<Diagnosis, String> t) -> {
            ((Diagnosis) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDiagnosis_Condition(t.getNewValue()); // display
            // only
            updateDiagnosis(t.getRowValue().getDNN(), t.getNewValue());
        });

        TableColumn<Diagnosis, String> analysisCol = new TableColumn<Diagnosis, String>("Analysis");
        analysisCol.setMinWidth(50);
        analysisCol.setCellValueFactory(new PropertyValueFactory<Diagnosis, String>("Analysis"));

        analysisCol.setCellFactory(TextFieldTableCell.<Diagnosis>forTableColumn());

        analysisCol.setOnEditCommit((CellEditEvent<Diagnosis, String> t) -> {
            ((Diagnosis) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAnalysis(t.getNewValue());
            updateAnalysis(t.getRowValue().getDNN(), t.getNewValue());
        });

        TableColumn<Diagnosis, String> causeCol = new TableColumn<Diagnosis, String>("cause");
        causeCol.setMinWidth(50);
        causeCol.setCellValueFactory(new PropertyValueFactory<Diagnosis, String>("Cause"));

        causeCol.setCellFactory(TextFieldTableCell.<Diagnosis>forTableColumn());

        causeCol.setOnEditCommit((CellEditEvent<Diagnosis, String> t) -> {
            ((Diagnosis) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAnalysis(t.getNewValue());
            updateCause(t.getRowValue().getDNN(), t.getNewValue());
        });

        // name of column for display
        TableColumn<Diagnosis, Float> priceCol = new TableColumn<Diagnosis, Float>("price");
        priceCol.setMinWidth(50);
        // to get the data from specific column
        priceCol.setCellValueFactory(new PropertyValueFactory<Diagnosis, Float>("Price"));

        myDataTable.setItems(dataList);
        myDataTable.getColumns().addAll(DNNCol, ANNCol, condCol, analysisCol, causeCol, priceCol);

        final TextField addDNN = new TextField();
        addDNN.setPromptText("DNN");
        addDNN.setMaxWidth(DNNCol.getPrefWidth());

        final TextField addANN = new TextField();
        addANN.setMaxWidth(ANNCol.getPrefWidth());
        addANN.setPromptText("Name");

        final TextField addDiagnosis = new TextField();
        addDiagnosis.setMaxWidth(condCol.getPrefWidth());
        addDiagnosis.setPromptText("Birthday");

        final TextField addAnalysis = new TextField();
        addAnalysis.setMaxWidth(analysisCol.getPrefWidth());
        addAnalysis.setPromptText("analysis");

        final TextField addCause = new TextField();
        addCause.setMaxWidth(causeCol.getPrefWidth());
        addCause.setPromptText("cause");

        final TextField addPrice = new TextField();
        addPrice.setMaxWidth(priceCol.getPrefWidth());
        addPrice.setPromptText("price");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                Diagnosis rc = new Diagnosis(Integer.parseInt(addDNN.getText()), Integer.parseInt(addANN.getText()),
                        addDiagnosis.getText(), addAnalysis.getText(), addCause.getText(),
                        Float.parseFloat(addPrice.getText()));
                dataList.add(rc);
                insertData(rc);
                addDNN.clear();
                addANN.clear();
                addDiagnosis.clear();
                addAnalysis.clear();
                addCause.clear();
                addPrice.clear();
            } catch (NumberFormatException ex) {
                alert.setContentText("fill all information on fields");
                alert.show();
            }
        });

        final HBox hb = new HBox();

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {
            ObservableList<Diagnosis> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Diagnosis> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                deleteRow(row);
                myDataTable.refresh();
            });
        });

        hb.getChildren().addAll(addDNN, addANN, addDiagnosis, addAnalysis, addCause, addPrice, addButton, deleteButton);
        hb.setSpacing(3);

        final Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction((ActionEvent e) -> {
            myDataTable.refresh();
        });

        final HBox hb2 = new HBox();
        hb2.getChildren().addAll(refreshButton);
        hb2.setAlignment(Pos.CENTER);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, myDataTable, hb, hb2);
        // vbox.getChildren().addAll(label, myDataTable);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        return vbox;

    }

    private void insertData(Diagnosis rc) {

        try {
            notification = ("Insert into Diagnosis (DNN, ANN, Diagnosis_Condition, Analysis, Cause, AgreedPrice) values("
                    + rc.getDNN() + ",'" + rc.getANN() + "','" + rc.getDiagnosis_Condition() + "', '" + rc.getAnalysis()
                    + "', '" + rc.getCause() + "', '" + rc.getPrice() + "')" + ";");

            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "Insert into Diagnosis (DNN, ANN, Diagnosis_Condition, Analysis, Cause, AgreedPrice) values("
                            + rc.getDNN() + ",'" + rc.getANN() + "','" + rc.getDiagnosis_Condition() + "', '"
                            + rc.getAnalysis() + "', '" + rc.getCause() + "', '" + rc.getPrice() + "')" + ";");
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

        SQL = "select DNN, ANN, Diagnosis_Condition, Analysis, Cause, AgreedPrice from Diagnosis order by DNN";
        Statement stmt = ConnectDB.connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next())
            data.add(new Diagnosis(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
                    rs.getString(3), rs.getString(4), rs.getString(5), Float.parseFloat(rs.getString(6))));

        rs.close();
        stmt.close();

        connectDB.closeDBConnection();
        System.out.println("Connection closed" + data.size());

    }

    String notification;// to alerts

    public void updateDiagnosis(int DNN, String Diagnosis_Condition) {

        try {
            notification = ("update  Diagnosis set Diagnosis_Condition = '" + Diagnosis_Condition + "' where DNN = "
                    + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Diagnosis set Diagnosis_Condition = '" + Diagnosis_Condition
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

    public void updateAnalysis(int DNN, String analysis) {

        try {
            notification = ("update  Diagnosis set Analysis = " + analysis + " where DNN = " + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Diagnosis set Analysis = " + analysis + " where DNN = " + DNN + ";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateCause(int DNN, String cause) {

        try {
            notification = ("update  Diagnosis set Cause = " + cause + " where DNN = " + DNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Diagnosis set Cause = " + cause + " where DNN = " + DNN + ";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteRow(Diagnosis row) {
        // TODO Auto-generated method stub
        try {
            notification = ("delete from  Diagnosis where DNN=" + row.getDNN() + ";");
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("delete from  Diagnosis where DNN=" + row.getDNN() + ";");
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
