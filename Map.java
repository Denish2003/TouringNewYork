/*
 * This program reads text file which contains important information like
 * .csv filepath, map title, maximum and minimum latitude and longitudes.
 *
 * Example : NewYork.txt
 * * data/SeatingLocations.csv
 * * data/NYCMap.jpg
 * * 40.4855
 * * 40.9166
 * * -74.2635
 * * -73.7018
 *
 * After reading the text file, it draws map and plot points using latitude and
 * longitudes provided by .csv file. It also enables users to interact with map
 * such as highlighting certain category of seating locations and displaying
 * address of the seating location upon clicking on the point.
 *
 * This program was intended just to produce interactive map of New York City's
 * seating locations, but it also works for other Cities if the .csv files of the
 * given city contains category, address, latitude, and longitude columns. User
 * can get the map, max and min latitude and longitude for their text file using
 * the website: openstreetmap.org
 *
 * Compilation: javac-introcs Map.java
 * Run: java-introcs Map < data/NewYork.txt
 */

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Map implements ItemListener, DrawListener {
    // Creates frame that map will be drawn on to.
    private Draw draw = new Draw();
    private JFrame frame = new JFrame();

    // Instance Variables
    private final double latMin; // Minimum y-value for canvas
    private final double latMax; // Maximum y-value for canvas
    private final double longMin; // Minimum x-value for canvas
    private final double longMax; // Maximum x-value for canvas

    // Create and Label check-boxes.
    private JCheckBox busRoute = new JCheckBox("Bus Route");
    private JCheckBox general = new JCheckBox("General");
    private JCheckBox seniorCenter = new JCheckBox("Senior Center");
    private JCheckBox plaza = new JCheckBox("Plaza");
    private JCheckBox municipalFacilities = new JCheckBox("Municipal Facilities");

    // Creates as array of Locations
    private Location[] multiLocations;
    private JPanel panel = new JPanel();
    private JLabel addInf = new JLabel();

    // Initial Frame with image and points plotted.
    public Map(double latMin, double latMax, double longMin, double longMax,
               String[][] dataset, String mapTitle) {
        // Initialization of instance variables.
        this.latMin = latMin;
        this.latMax = latMax;
        this.longMin = longMin;
        this.longMax = longMax;

        // Initializing standard drawing
        draw.setYscale(latMin, latMax); // Range of y-coordinate. Latitude
        draw.setXscale(longMin, longMax); // Range of x-coordinate. Longitude
        draw.enableDoubleBuffering();

        // add empty canvas on the frame.
        JLabel canvas = draw.getJLabel();
        frame.add(canvas, BorderLayout.CENTER);
        // Exits the program when close ("X") is clicked.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Listener on the map.
        draw.addListener(this);

        // Initialize the panel and add it to the frame.
        addInf.setText("Address");
        panel.add(addInf);
        frame.add(panel, BorderLayout.SOUTH);

        // Set frame title
        frame.setTitle("Map");

        // Call instance methods.
        itemStateChanged(null); // draw default map
        checkBox();
        mapProjection(dataset, mapTitle);

    }

    // Takes 2D array of dataset and mapTitle an argument and creates array of
    // Locations. Projects the map using the array.
    public void mapProjection(String[][] dataset, String mapTitle) {
        // Length of the 2D array
        int numRow = dataset.length;
        int numCol = dataset[0].length;

        // Initializes the multiLocations array.
        this.multiLocations = new Location[numRow - 1];

        // Creates and Initializes column number of category, address, latitude,
        // and longitude to zero.
        int colCat = 0;
        int colAdd = 0;
        int colLat = 0;
        int colLong = 0;

        // Loops through the dataset row 1 ("0") and determine the index number
        // of category, address, latitude, and longitude.
        for (int i = 0; i < numCol; i++) {
            if (dataset[0][i].contains("Category"))
                colCat = i;
            else if (dataset[0][i].contains("Address"))
                colAdd = i;
            else if (dataset[0][i].contains("Latitude"))
                colLat = i;
            else if (dataset[0][i].contains("Longitude"))
                colLong = i;
        }

        // Create and Initializes category, address, latitude, and longitude.
        String category = "";
        String address = "";
        double latitude = 0;
        double longitude = 0;

        // Loops through the 2D dataset array and add each point as its own
        // location in multiLocations array.
        for (int i = 1; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                if (j == colCat)
                    category = dataset[i][j];
                else if (j == colAdd)
                    address = dataset[i][j];
                else if (j == colLat)
                    latitude = Double.parseDouble(dataset[i][j]);
                else if (j == colLong)
                    longitude = Double.parseDouble(dataset[i][j]);

                // Create Location object.
                Location x = new Location(category, address, latitude, longitude);

                // Add Location to multiLocations array.
                multiLocations[i - 1] = x;
            }
        }

        // Pen color and size
        draw.setPenRadius(0.005);
        draw.setPenColor(Color.RED);

        // x and y coordinate to start the map
        double imgX = this.longMin + (this.longMax - this.longMin) / 2;
        double imgY = this.latMin + (this.latMax - this.latMin) / 2;

        // Draws map using the map title provides via text file.
        draw.picture(imgX, imgY, mapTitle);

        // Plot points onto the map using latitude and longitude.
        for (int i = 1; i < numRow - 1; i++) {
            double y = multiLocations[i].getLatitude();
            double x = multiLocations[i].getLongitude();
            draw.point(x, y);
        }
        draw.show();

        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);
    }

    // Add check-boxes to the frame and add item listener to them.
    public void checkBox() {
        // Create and initializes legend using JPanel.
        JPanel legend = new JPanel();

        // Add each checkbox to the legend
        legend.add(busRoute);
        legend.add(general);
        legend.add(seniorCenter);
        legend.add(plaza);
        legend.add(municipalFacilities);

        // Add item listener to each checkbox.
        busRoute.addItemListener(this);
        general.addItemListener(this);
        seniorCenter.addItemListener(this);
        plaza.addItemListener(this);
        municipalFacilities.addItemListener(this);

        // add legend to the frame.
        frame.add(legend, BorderLayout.NORTH);
    }

    // Sees if the user clicks the GUI and reacts accordingly
    public void mousePressed(double x, double y) {
        double UNCERTAINTY = 0.003;

        // Checks if mouse in clicked on any point on the map. If yes, then prints
        // the address of the location. If no, then prints "Not a Seating Location".
        for (int i = 0; i < multiLocations.length; i++) {
            double cliLat = multiLocations[i].getLatitude();
            double cliLong = multiLocations[i].getLongitude();
            boolean latitude = ((y - UNCERTAINTY) <= cliLat) && ((y + UNCERTAINTY) >= cliLat);
            boolean longitude = ((x - UNCERTAINTY) <= cliLong) && ((x + UNCERTAINTY) >= cliLong);

            if (latitude && longitude) {
                // Find the address of the point the mouse is clicked on.
                String address = multiLocations[i].getAddress();

                // Prints out the address
                System.out.println(address);

                // Add address to the panel
                addInf.setText(address);
                panel.add(addInf);
                break;
            }
            addInf.setText("Not a Seating Location");
            panel.add(addInf);
        }
        // Prints out which coordinates of the point mouse is clicked on.
        System.out.println("Clicked at (" + x + "," + y + ")");
    }

    private void helper(String name, Color color) {
        // Number of Locations
        int n = multiLocations.length;

        for (int i = 1; i < n - 1; i++) {
            if (multiLocations[i].getCategory().contains(name)) {
                double y = multiLocations[i].getLatitude();
                double x = multiLocations[i].getLongitude();
                draw.setPenColor(color);
                draw.point(x, y);
            }
        }
    }

    // Checks if any checkbox is selected or unselected. Changes color for all
    // the points that fall under that category upon selecting that checkbox and
    // returns to its original color upon unselecting the checkbox.
    public void itemStateChanged(ItemEvent e) {
        if (e == null) return;


        if (busRoute.isSelected())
            helper("Bus Route", Color.DARK_GRAY);
        else
            helper("Bus Route", Color.RED);

        if (general.isSelected())
            helper("General", Color.BLUE);
        else
            helper("General", Color.RED);

        if (seniorCenter.isSelected())
            helper("Senior Center", Color.GREEN);
        else
            helper("Senior Center", Color.RED);

        if (plaza.isSelected())
            helper("Plaza", Color.BLACK);
        else
            helper("Plaza", Color.RED);

        if (municipalFacilities.isSelected())
            helper("Municipal Facilities", Color.orange);
        else
            helper("Municipal Facilities", Color.RED);

        draw.show();
        frame.repaint();
    }

    // Reads in the text file and projects the map.
    public static void main(String[] args) {
        // Read csv file name from text file and covert it into a 2D array.
        String filepath = StdIn.readLine();
        String[][] dataset = SimpleCSV.parseCSV(filepath);

        // Reads image name which is to be projected in the background.
        String mapTitle = StdIn.readLine();

        // Maximum and Minimum values for X/Y scale of the canvas.
        double latMin = Double.parseDouble(StdIn.readLine());
        double latMax = Double.parseDouble(StdIn.readLine());
        double longMin = Double.parseDouble(StdIn.readLine());
        double longMax = Double.parseDouble(StdIn.readLine());

        Map map = new Map(latMin, latMax, longMin, longMax, dataset, mapTitle);
    }
}
