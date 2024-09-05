package Booking;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class Booking {
    private int id;
    private User user;
    private Driver driver;
    private String place;
    private String dates;
    private String days;
    private String vehicleType;

    public Booking(User user, Driver driver, String place, String dates, String days, String vehicleType) {
        this.user = user;
        this.driver = driver;
        this.place = place;
        this.dates = dates;
        this.days = days;
        this.vehicleType = vehicleType;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/driverhire", "root", "Ashif@2003");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO bookings (user_id, driver_id, place, dates, days, vehicle_type) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, this.user.getId());
            statement.setInt(2, this.driver.getId());
            statement.setString(3, this.place);
            statement.setString(4, this.dates);
            statement.setString(5, this.days);
            statement.setString(6, this.vehicleType);
            statement.executeUpdate();

            // Get the auto-generated booking ID and set it back to the Booking object
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


