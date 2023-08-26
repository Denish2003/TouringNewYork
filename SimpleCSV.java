/*
 * This program parse .csv file into a 2D array.
 *   - It was take filepath for the .csv file as a command-line argument.
 *   Example: data/SeatingLocations.cvs
 *
 * Compilation: javac-introcs SimpleCSV.java
 * Run: java-introcs SimpleCSV data/SeatingLocations.csv
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SimpleCSV {

    // Take filepath for csv file as a String argument.
    // Read csv file and parse it into a 2D array.
    public static String[][] parseCSV(String path) {
        List<String[]> list = new ArrayList<>();
        try {
            // Reads in data from .csv file
            Reader in = new FileReader(path);
            BufferedReader reader = new BufferedReader(in);
            String line = reader.readLine();

            // Read each line and split it into an array.
            while (line != null) {
                String[] x = line.split(",");
                list.add(x);
                line = reader.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid file path: " + path);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Number of Rows and Column need for 2D array.
        int numRow = list.size();
        int numCol = list.get(0).length;

        // Create and initialize 2D array.
        String[][] dataset = new String[numRow][numCol];

        // Stores values read from .csv file into a 2D array
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                dataset[i][j] = list.get(i)[j];
            }
        }
        return dataset;
    }

    // Testing parseCSV method for few random points to see if correct output
    // is printed.
    public static void main(String[] args) {
        // Take filepath as a command-line argument.
        String filepath = args[0];

        // Parse .csv file and save as 2D array.
        String[][] dataset = SimpleCSV.parseCSV(filepath);

        // For SeatingLocation.csv
        System.out.println(dataset[0][10]); // Category
        System.out.println(dataset[0][12]); // Address
        System.out.println(dataset[135][10]); // BID
        System.out.println(dataset[200][0]); // POINT (-73.90807643938491 40.859442082693526)
    }
}
