/*
 * This program takes category, address, latitude, and longitude for each point
 * and creates a Location object for that point.
 *
 * Compilation: javac-introcs Location.java
 * Run: java-introcs Location
 */

public class Location {
    // Instance variables
    private String category; // Category of the location
    private String address; // Address of the location
    private double latitude; // Latitude of the location
    private double longitude; // Longitude of the location

    // Initializes instant variables. Creates a Location object with 4 arguments.
    public Location(String category, String address, double latitude, double longitude) {
        this.category = category;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Returns the category.
    public String getCategory() {
        return this.category;
    }

    // Returns the address.
    public String getAddress() {
        return this.address;
    }

    // Returns the latitude.
    public double getLatitude() {
        return this.latitude;
    }

    // Returns the longitude.
    public double getLongitude() {
        return this.longitude;
    }

    // Returns a String representation of the location.
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Category: " + this.category + "\n");
        result.append("Address: " + this.address + "\n");
        result.append("Latitude: " + this.latitude + "\n");
        result.append("Longitude: " + this.longitude + "\n");

        return result.toString();
    }

    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) {
        Location loc1 = new Location("Bus Stop", "123 Street,NYC", 40, 40);
        System.out.println("Category: " + loc1.category); // Category: Bus Stop
        System.out.println("Address: " + loc1.address); // Address: 123 Street,NYC
        System.out.println("Latitude: " + loc1.latitude); // Latitude: 40.0
        System.out.println("Longitude: " + loc1.longitude); // Longitude: 40.0
        System.out.println();

        /* Expected Output:
         * Category: General
         * Address: XYZ Street
         * Latitude: 15.0
         * Longitude: 12.0
         *
         */
        Location loc2 = new Location("General", "XYZ Street", 15, 12);
        System.out.println(loc2.toString());
    }
}
