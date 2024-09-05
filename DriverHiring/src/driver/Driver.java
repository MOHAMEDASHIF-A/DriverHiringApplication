package driver;


class Driver {
    private int id;
    private String name;
    private int experience;
    private double rating;
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
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Driver(int id, String name, int experience, double rating) {
		super();
		this.id = id;
		this.name = name;
		this.experience = experience;
		this.rating = rating;
	}
	public Driver(String name, int experience, double rating) {
		super();
		this.name = name;
		this.experience = experience;
		this.rating = rating;
		
	}

    // Constructors, getters, setters
}