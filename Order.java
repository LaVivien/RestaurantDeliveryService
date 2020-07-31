package restaurantDeliveryService;

public class Order {
	int readyTime;
	String location;
	String foodName;
	
    public Order (String line) {
    	String[] tokens = line.split(",");   	
    	readyTime = Integer.parseInt(tokens[0].trim());
    	location = tokens[1].trim();
    	foodName = tokens[2].trim(); 	
    }
    
    public String toString() {
    	return (readyTime + " " + location + " " + foodName); 
    }
}
