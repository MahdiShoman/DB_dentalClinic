
public class EquipmentOrder {
    private int orderID;
    private String description;
    private String orderDate;
    private double price;
    private int doctorID;

    public EquipmentOrder() {
    }

    public EquipmentOrder(int orderID, String description, String orderDate, double price, int doctorID) {
        this.orderID = orderID;
        this.description = description;
        this.orderDate = orderDate;
        this.price = price;
        this.doctorID = doctorID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

