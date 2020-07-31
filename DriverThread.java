package restaurantDeliveryService;

public class DriverThread implements Runnable {
	   private DriverQueue drivers;
	   private int distance;
	   private String location;
	 
	   public DriverThread(DriverQueue drivers, String location, int distance) {
	      this.drivers = drivers;
	      this.location = location;
	      this.distance = distance;
	   }
	 
	   @Override
	   public void run()  {	      
	      try {
	    	  drivers.deliver(location, distance);
	      } catch ( InterruptedException e) {
	    	  System.out.println("InterruptedException!");
	      }	      	
	   }
}
