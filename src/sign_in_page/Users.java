package sign_in_page;

public class Users {
    private String username;
    private String password;
    private String role;
    private String name;
    private String birthday;
    private String contactNumber;
    private String address;
    private String specialty;

    // Constructors, getters, and setters

    public Users(String username, String password, String role, String name,
                String birthday, String contactNumber, String address, String specialty) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.birthday = birthday;
        this.contactNumber = contactNumber;
        this.address = address;
        this.specialty = specialty;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }



}
