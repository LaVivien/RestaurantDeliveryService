package restaurantDeliveryService;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Restaurant {

@SerializedName("name")
@Expose
private String name;
@SerializedName("address")
@Expose
private String address;
@SerializedName("latitude")
@Expose
private Double latitude;
@SerializedName("longitude")
@Expose
private Double longitude;
@SerializedName("drivers")
@Expose
private Integer drivers;

@SerializedName("menu")
@Expose
private List<String> menu = null;

//add new one
private DriverQueue driverQueue = null;


public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public Double getLatitude() {
return latitude;
}

public void setLatitude(Double latitude) {
this.latitude = latitude;
}

public Double getLongitude() {
return longitude;
}

public void setLongitude(Double longitude) {
this.longitude = longitude;
}

public Integer getDrivers() {
return drivers;
}

public void setDrivers(Integer drivers) {
this.drivers = drivers;
}

public List<String> getMenu() {
return menu;
}

public void setMenu(List<String> menu) {
	this.menu = menu;
}

public DriverQueue getDriverQueue() {
return driverQueue;
}

public void setDriverQueue(DriverQueue dq) {
this.driverQueue = dq;
}

}


public class RestaurantList {

@SerializedName("data")
@Expose
private List<Restaurant> data = null;

public List<Restaurant> getData() {
return data;
}

public void setData(List<Restaurant> data) {
this.data = data;
}
}
