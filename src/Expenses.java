import java.util.Date;

public class Expenses {
    private int expenseID;
    private String description;
    private double amount;
    private int doctorID;
    private Date month;

    public Expenses(int expenseID, String description, double amount, int doctorID, Date month) {
        this.expenseID = expenseID;
        this.description = description;
        this.amount = amount;
        this.doctorID = doctorID;
        this.month = month;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
