package restaurantDeliveryService;

import java.time.LocalTime;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DriverQueue {
	
	private int size;
	private final Lock queueLock = new ReentrantLock();
    private Condition emptyCondition = queueLock.newCondition();
    private Condition fullCondition = queueLock.newCondition();
	 
    DriverQueue(int capacity) {
    	this.size = capacity;
    }
    
	public void deliver(String location, int distance) throws InterruptedException {
      queueLock.lock();
      try{
    	  while(size == 0) {
    		  System.out.println("All driver in " + location + " has out!");
              emptyCondition.await();
          }
    	  System.out.println("["+ LocalTime.now()  + "] Starting delivery of " + Thread.currentThread().getName() );
    	  size--;  	  
    	  Thread.sleep(distance);       
      } catch (InterruptedException e) {
         e.printStackTrace();
      } 
      finally {
    	  System.out.println ("["+ LocalTime.now()  + "] Finished delivey of " + Thread.currentThread().getName() + "!");
    	  size++;
    	  fullCondition.signalAll();
          queueLock.unlock();
      }
   }
}
