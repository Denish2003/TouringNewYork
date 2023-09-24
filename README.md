# TouringNewYork
#### Authors: Denish Patel (dp9798@princeton.edu) & Bryan Alexis (ba9071@princeton.edu)

A Java program that reads a text file containing important information such as .csv file paths, map title, and latitude and longitude boundaries. The program draws an interactive map and plots points using latitude and longitude coordinates provided by a .csv file. Users can interact with the map, highlighting specific categories of seating locations and displaying addresses upon clicking on points.

### Required Installation 
In order to use sucessfully run and compile all the libraries in this project. You are required to install Princeton java environment, which can be installed using the following link: https://lift.cs.princeton.edu/java/windows/

### How to Use
1. Make sure you have all the files downloaded on your local device before moving on to the next steps. Make sure everything is in one place.
   a. If your data folder is not in the same folder as the other classes. You will have to update the file path in the program.
2. Compile all the classes using compile script provided in each class.
   a. javac-introcs, wrapper script will only be availabe to use, if your required instatallation is done correctly.
3. After successful completion of previous step, go ahead and run the program using the run script provided in each class.
   a. Map class produces the final result. SimpleCSV and Location are just helper classes. 

### Images of Program in Use
<img src="https://github.com/Denish2003/TouringNewYork/blob/main/readmeImg1.PNG" width="325px" height="400px">
<img src="https://github.com/Denish2003/TouringNewYork/blob/main/readmeImg2.png" width="325px" height="400px">
<img src="https://github.com/Denish2003/TouringNewYork/blob/main/readmeImg3.png" width="325px" height="400px">

### Features
- Parses .csv files to obtain location data.
- Plots points on an interactive map using latitude and longitude.
- Highlights specific categories of seating locations.
- Displays address details upon clicking on points.
- Works for other cities by providing .csv files with category, address, latitude, and longitude columns.

### Notes
- Although this program was created to visualize the seating locations for New York City, it can also be used for other location if the text file and .csv files is properly formatted like one provided for New York city.
- The OpenStreetMap website (openstreetmap.org) can be used to obtain map images and latitude/longitude boundaries.

### Acknowledgement 
Thank you to our project advisor Gabriel Contreras and Princeton Fall 2022 COS 126 , Computer Science: An Interdisciplinary Approach, staff for helping us with bringing our project to life.
