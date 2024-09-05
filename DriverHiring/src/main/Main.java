package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
class User {
    private int id;
    private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User( String name) {
		super();
		this.name = name;
	}
    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/driverhire", "root", "Ashif@2003");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, this.name);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
class Driver {
    private int id;
    private String name;
    private int experience;
    private double rating;
    private String vehicle;
    private Location location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Driver(int id, String name, int experience, double rating,String vehicle) {
		super();
		this.id = id;
		this.name = name;
		this.experience = experience;
		this.rating = rating;
		this.vehicle=vehicle;
	}
	public Driver(String name, int experience, double rating,String vehicle) {
		super();
		this.name = name;
		this.experience = experience;
		this.rating = rating;
		this.vehicle=vehicle;
	}
}
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
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
class Location {
	private int id; // Unique ID for the location entry in the database
    private double latitude;
    private double longitude;
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public void saveToDatabase(int driverId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/driverhire", "root", "Ashif@2003")) {
            if (this.id == 0) {              
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO locations (driver_id, latitude, longitude) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                statement.setInt(1, driverId);
                statement.setDouble(2, this.latitude);
                statement.setDouble(3, this.longitude);
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                }
            } else {              
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE locations SET latitude = ?, longitude = ? WHERE id = ?"
                );
                statement.setDouble(1, this.latitude);
                statement.setDouble(2, this.longitude);
                statement.setInt(3, this.id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/driverhire";
    private static final String USER = "root";
    private static final String PASSWORD = "Ashif@2003";
    private Connection connection;
    public DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void addDriver(Driver driver) {
        try {
        	PreparedStatement statement = connection.prepareStatement("INSERT INTO drivers (name, experience, rating, vehicle) VALUES (?, ?, ?, ?)");
            statement.setString(1, driver.getName());
            statement.setInt(2, driver.getExperience());
            statement.setDouble(3, driver.getRating());
            statement.setString(4, driver.getVehicle());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Driver> getAvailableDrivers() {
        List<Driver> drivers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM drivers ORDER BY experience DESC, rating DESC");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int experience = resultSet.getInt("experience");
                double rating = resultSet.getDouble("rating");
                String vehicle = resultSet.getString("vehicle");
                drivers.add(new Driver(id, name, experience, rating,vehicle));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }
}
public class Main {
    private static final DatabaseManager databaseManager = new DatabaseManager();

    public static void main(String[] args) {
    	
    	int option=1;
		Scanner sc=new Scanner(System.in);
		System.out.println("Welcome to Driver Hiring Application...>>");
		while(true)
		{
			System.out.println("Enter 1--->add User Details\nEnter 2---> add Drivers\nEnter 3--->Booking the Driver");
			option=sc.nextInt()	;
			if(option==1)
			{
				System.out.println("---------Enter your Details--------");
				System.out.println("Enter your Name");
				sc.nextLine();
		        String name=sc.nextLine();
		        User u1=new User( name);
		        u1.saveToDatabase();
				System.out.println("Your details is successfully added.");
				System.out.println("-------------------------------------");
				System.out.println();
			}
			else if(option==2)
			{
				System.out.println("--------About Drivers Details--------");
				System.out.println("Enter Driver Name");
				sc.nextLine();
		        String name=sc.nextLine();
		        System.out.println("Enter Driver Experiance");
		        int exp=sc.nextInt();
		        System.out.println("Enter Driver Rating");
		        double rat=sc.nextFloat();
		        sc.nextLine();
		        System.out.println("Enter Driver Vehicle");
		        String vehicle=sc.nextLine();
		        addExampleDrivers(name,exp,rat,vehicle);
		        List<Driver> availableDrivers = databaseManager.getAvailableDrivers();
		        for (Driver driver : availableDrivers) {
		            System.out.println("Driver: " + driver.getName() + ", Experience: " + driver.getExperience() + ", Rating: " + driver.getRating()+ ", Vehicle: " + driver.getVehicle());
		        }
				System.out.println("-------------------------------------");
				System.out.println();
			}
			else if (option == 3) {
			    List<Driver> availableDrivers = databaseManager.getAvailableDrivers();
			    System.out.println("Available Drivers:");
			    for (int i = 0; i < availableDrivers.size(); i++) {
			        Driver driver = availableDrivers.get(i);
			        System.out.println((i + 1) + ". Driver: " + driver.getName() + ", Experience: " + driver.getExperience() + ", Rating: " + driver.getRating()+ ", Vehicle: " + driver.getVehicle());
			    }
			    System.out.println("Enter the number of the driver you want to book:");
			    int driverChoice = sc.nextInt();
			    if (driverChoice >= 1 && driverChoice <= availableDrivers.size()) {
			        Driver selectedDriver = availableDrivers.get(driverChoice - 1);
			        System.out.println("Enter your name:");
			        sc.nextLine(); // Consume the newline character left by the previous nextInt()
			        String userName = sc.nextLine();
			        User user = new User(userName);
			        user.saveToDatabase();
			        System.out.println("Enter the place:");
			        String place = sc.nextLine();
			        System.out.println("Enter the dates:");
			        String dates = sc.nextLine();
			        System.out.println("Enter the days:");
			        String days = sc.nextLine();
			        System.out.println("Enter the vehicle type:");
			        String vehicleType = sc.nextLine();
			        Booking booking = new Booking(user, selectedDriver, place, dates, days, vehicleType);
			        booking.saveToDatabase();
			        System.out.println("Booking successful!");
//			        bookDriver(user, selectedDriver, place, dates, days, vehicleType);
			    } else {
			        System.out.println("Invalid driver choice.");
			    }
			    System.out.println("-------------------------------------");
			    System.out.println();
			}
			else 
			{
				System.out.println("Invalid input");
			}			
		}
	}
    private static void addExampleDrivers(String name,int exp,double rat,String vehicle) {
        Driver driver1 = new Driver(name, exp, rat,vehicle);
        databaseManager.addDriver(driver1);
    }
//    private static void bookDriver(User user, Driver driver, String place, String dates, String days, String vehicleType) {
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            System.out.println("Enter driver's current latitude (or enter -1 to stop updating location):");
//            double latitude = sc.nextDouble();
//            if (latitude == -1) {
//                break;
//            }
//            System.out.println("Enter driver's current longitude:");
//            double longitude = sc.nextDouble();
//            Location newLocation = new Location(latitude, longitude);
//            driver.setLocation(newLocation);
//            driver.getLocation().saveToDatabase(driver.getId());
//            System.out.println("Driver location updated successfully!");
//        }
//        System.out.println("Ride completed.");
//    }
}



