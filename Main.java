package restaurantDeliveryService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import com.google.gson.Gson;
import java.util.*;

public class Main {
	 	
	 private static RestaurantList restaurants;
	 private static Queue<Order> orders = new LinkedList<>();
	 private static Double userLatitude;
	 private static Double userLongitude;
	
    private static void loadRestaurantFile(Scanner sc) {
    	boolean valid = false;
   		while (!valid) {
              System.out.println("What is the name of the file containing the restaurant information?");
              String fn = sc.nextLine();
              try {
            	  BufferedReader br = new BufferedReader(new FileReader(fn));
            	  restaurants = new Gson().fromJson(br, RestaurantList.class);
                  System.out.println("The file has been properly read");
                  if (restaurants != null) {
        			     for (Restaurant t : restaurants.getData()) {
        			       System.out.println(t.getName());
        			       System.out.println(t.getMenu());
        			     }
                  }		 
                  valid = true;
              } catch (Exception e) {
            	  System.out.println("Invalid, try again.");
              }
          }         
    }
    
	//Read schedule file
	private static void readSchedule(Scanner sc) {
		boolean valid = false;
		while (!valid) {
			 System.out.println("\nWhat is the name of the file containing the schedule information?");
             String fn = sc.nextLine();
             try {
		        Scanner sc1 = new Scanner(new File(fn));
				while(sc1.hasNextLine()) {
					String line = sc1.nextLine();			
					Order schedule = new Order(line);
					orders.add(schedule);	
					System.out.println(schedule);
				}
			    valid = true;
			    sc1.close();
             } catch (Exception e) {
           	  	System.out.println("Invalid, try again.");
             }
		}
	}
	
    private static void getUserLocation(Scanner sc) {
    	userLatitude = getLatitude(sc);
    	userLongitude = getLongitude(sc);
    }

    private static double getLatitude(Scanner sc) {
    	String query = "\nWhat is your latitude?";
        double latitude = 0.0;
        while (true) {
            System.out.println(query);
            String latitudeString = sc.nextLine();
            try {
                latitude = Double.parseDouble(latitudeString);
                if (latitude < -90.0 || latitude > 90.0) {
                	throw new NumberFormatException();
                }
                return latitude;
            } catch (NumberFormatException ignore) { }
        }
    }

    private static double getLongitude(Scanner sc) {
    	String query = "\nWhat is your longitude?";
        double longitude = 0.0;
        while (true) {
            System.out.println(query);
            String longitudeString = sc.nextLine();
            try {
                longitude = Double.parseDouble(longitudeString);
                if (longitude < -180.0 || longitude > 180.0) {
                	throw new NumberFormatException();
                }
                return longitude;
            } catch (NumberFormatException ignore) { }
        }
    }
    
    private static double calculateDistance(double latitude, double longitude) {
    	return 3963.0 * Math.acos((Math.sin(Math.toRadians(userLatitude)) * Math.sin(Math.toRadians(latitude))) + Math.cos(Math.toRadians(userLatitude))
        * Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(userLongitude) - Math.toRadians(longitude)));
    }
    
    public static void scheduleDrivers () throws InterruptedException {    
    	//get drivers number in restaurant, initialize DriverQueue with the number, save back to restaurant
    	for (Restaurant r: restaurants.getData()) {
    		int driverNum = r.getDrivers();
    		DriverQueue drivers = new DriverQueue(driverNum);
    		r.setDriverQueue(drivers);
    	}
    	System.out.println("Starting execution of program...");
    	//start thread for each order
    	while (orders.size() > 0) {
    		Order order = orders.remove();
    		Restaurant r = getRestaurantByName(order.location);
    		double dist = calculateDistance(r.getLatitude(), r.getLongitude());
    		Thread thread = new Thread(new DriverThread(r.getDriverQueue(), r.getName(), (int)dist*1000*2), order.foodName + " from " + order.location + "!");
            Thread.sleep(order.readyTime*1000); 
            thread.start();   
            //wait for last to finish
            if (orders.size() == 0) {            	
            	while (thread.isAlive()) {
            		Thread.sleep(250); 
            	}
            	System.out.println("orders complete!");
            }
    	}  	
    }

    
    private static Restaurant getRestaurantByName(String name) {
    	 for (Restaurant r : restaurants.getData()) {
		      if (r.getName().equals(name))
		    	  return r;
		 }
    	return null;
    }
    
    public static void main(String[] args) throws InterruptedException {    	
        Scanner sc = new Scanner(System.in);
        loadRestaurantFile(sc);
        readSchedule(sc);
        getUserLocation(sc);
        scheduleDrivers ();        	            
    }
}
