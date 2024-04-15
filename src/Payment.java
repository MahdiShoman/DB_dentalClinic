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

public class Payment {

    private int PNM;
    private int PNN;
    private String way;
    private String dateAndTime;
    private float amount;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public Payment() {

    }

    public Payment(int PNM, int PNN, String way, String dateAndTime, float amount) {
        this.PNM = PNM;
        this.PNN = PNN;
        this.way = way;
        this.dateAndTime = dateAndTime;
        this.amount = amount;
    }

    public int getPNM() {
        return PNM;
    }

    public void setPNM(int pNM) {
        PNM = pNM;
    }

    public int getPNN() {
        return PNN;
    }

    public void setPNN(int pNN) {
        PNN = pNN;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    ConnectDB connectDB = new ConnectDB();
    private ArrayList<Payment> data = new ArrayList<>();
    private ObservableList<Payment> dataList;

    public VBox tableView() throws SQLException, ClassNotFoundException {

        TableView<Payment> myDataTable = new TableView<Payment>();
        getData();
        dataList = FXCollections.observableArrayList(data);
        data = new ArrayList<>();
        Scene scene = new Scene(new Group());

        Label label = new Label("payment Table");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.MEDIUMAQUAMARINE);

        myDataTable.setEditable(true);
        myDataTable.setMaxHeight(600);
        myDataTable.setMaxWidth(600);

        // name of column for display

        TableColumn<Payment, Integer> PNMCol = new TableColumn<Payment, Integer>("PNM");
        PNMCol.setMinWidth(50);
        // to get the data from specific column
        PNMCol.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("PNM"));

        TableColumn<Payment, Integer> PNNCol = new TableColumn<Payment, Integer>("PNN");
        PNNCol.setMinWidth(50);
        // to get the data from specific column
        PNNCol.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("PNN"));

        TableColumn<Payment, String> wayCol = new TableColumn<Payment, String>("way");
        wayCol.setMinWidth(100);
        wayCol.setCellValueFactory(new PropertyValueFactory<Payment, String>("Way"));

        wayCol.setCellFactory(TextFieldTableCell.<Payment>forTableColumn());
        wayCol.setOnEditCommit((CellEditEvent<Payment, String> t) -> {
            ((Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWay(t.getNewValue()); // display
            // only
            updateName(t.getRowValue().getPNN(), t.getNewValue());
        });

        TableColumn<Payment, String> DateCol = new TableColumn<Payment, String>("DateAndTime");
        DateCol.setMinWidth(100);
        DateCol.setCellValueFactory(new PropertyValueFactory<Payment, String>("DateAndTime"));

        DateCol.setCellFactory(TextFieldTableCell.<Payment>forTableColumn());
        DateCol.setOnEditCommit((CellEditEvent<Payment, String> t) -> {
            ((Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateAndTime(t.getNewValue());
            updateDate(t.getRowValue().getPNN(), t.getNewValue());
        });

        TableColumn<Payment, Float> amountCol = new TableColumn<Payment, Float>("Amount");
        amountCol.setMinWidth(50);
        // to get the data from specific column
        amountCol.setCellValueFactory(new PropertyValueFactory<Payment, Float>("Amount"));

        myDataTable.setItems(dataList);
        myDataTable.getColumns().addAll(PNMCol, PNNCol, wayCol, DateCol, amountCol);

        final TextField addPNN = new TextField();
        addPNN.setPromptText("PNN");
        addPNN.setMaxWidth(PNNCol.getPrefWidth());

        final TextField addPNM = new TextField();
        addPNM.setPromptText("PNM");
        addPNM.setMaxWidth(PNNCol.getPrefWidth());

        final TextField addWay = new TextField();
        addWay.setMaxWidth(addWay.getPrefWidth());
        addWay.setPromptText("way of pay");

        final TextField addDate = new TextField();
        addDate.setMaxWidth(addDate.getPrefWidth());
        addDate.setPromptText("date");

        final TextField addAmount = new TextField();
        addAmount.setMaxWidth(addAmount.getPrefWidth());
        addAmount.setPromptText("amount");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                Payment rc = new Payment(Integer.parseInt(addPNM.getText()), Integer.parseInt(addPNN.getText()),
                        addWay.getText(), addDate.getText(), Float.parseFloat(addAmount.getText()));
                dataList.add(rc);
                insertData(rc);
                addPNM.clear();
                addPNN.clear();
                addWay.clear();
                addDate.clear();
                addAmount.clear();
            } catch (NumberFormatException ex) {
                alert.setContentText("fill all information on fields");
                alert.show();
            }
        });

        final HBox hb = new HBox();

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {
            ObservableList<Payment> selectedRows = myDataTable.getSelectionModel().getSelectedItems();
            ArrayList<Payment> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> {
                myDataTable.getItems().remove(row);
                deleteRow(row);
                myDataTable.refresh();
            });
        });

        hb.getChildren().addAll(addPNM, addPNN, addWay, addDate, addAmount, addButton, deleteButton);
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
        Revenue();
        CashVsDeb();

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

        SQL = "select PNM,PNN,Way,DateAndTime,Amount from Payments order by PNN";
        Statement stmt = ConnectDB.connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next())
            data.add(new Payment(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)), rs.getString(3),
                    rs.getString(4), Float.parseFloat(rs.getString(5))));

        rs.close();
        stmt.close();

        connectDB.closeDBConnection();
        System.out.println("Connection closed" + data.size());

    }

    private void insertData(Payment rc) {

        try {
            notification = ("Insert into Payments (PNM, PNN, Way, DateAndTime, Amount) values(" + rc.getPNM() + ",'"
                    + rc.getPNN() + "','" + rc.getWay() + "', '" + rc.getDateAndTime() + "', '" + rc.getAmount() + "')"
                    + ";");

            connectDB.startDBConnection();
            connectDB.ExecuteStatement("INSERT INTO Payments (PNM, PNN, Way, DateAndTime, Amount) values(" + rc.getPNM()
                    + "," + rc.getPNN() + ",'" + rc.getWay() + "', '" + rc.getDateAndTime() + "', " + rc.getAmount()
                    + ")" + ";");

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

    public void updateName(int PNN, String way) {

        try {
            notification = ("update  Payments set way = '" + way + "' where PNM = " + PNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("update  Payments set name = '" + way + "' where PNM = " + PNN + ";");
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

    public void updateDate(int PNN, String DateAndTime) {

        try {
            notification = ("update  Payments set DateAndTime = '" + DateAndTime + "' where PNM = " + PNN);
            connectDB.startDBConnection();
            connectDB.ExecuteStatement(
                    "update  Payments set DateAndTime = '" + DateAndTime + "' where PNM = " + PNN + ";");
            connectDB.closeDBConnection();
            alert.setContentText(notification);
            alert.show();
            System.out.println("Connection closed");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteRow(Payment row) {
        // TODO Auto-generated method stub
        try {
            notification = ("delete from  Payments where PNM=" + row.getPNM() + ";");
            connectDB.startDBConnection();
            connectDB.ExecuteStatement("delete from  Payments where PNM=" + row.getPNM() + ";");
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

//		SELECT SUM(Amount) AS TotalRevenue
//		FROM Payments;

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
