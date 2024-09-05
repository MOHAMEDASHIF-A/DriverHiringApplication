package location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
 // Database operations for Location
    public void saveToDatabase(int driverId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/driverhire", "root", "Ashif@2003");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO locations (driver_id, latitude, longitude) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE latitude = ?, longitude = ?")) {
            statement.setInt(1, driverId);
            statement.setDouble(2, this.latitude);
            statement.setDouble(3, this.longitude);
            statement.setDouble(4, this.latitude);
            statement.setDouble(5, this.longitude);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}