package com.example.myapplication.mod1;

/* This class consists of three links to the reference nodes.
 * The distances themselves can be extracted from the links.
 * 
 */

public class LocationData {
	protected double latitude;
	protected double longitude;
	
	public LocationData() {
		latitude = -1;
		longitude = -1;
	}
	public LocationData(double lat, double lon) {
		this.latitude = lat;
		this.longitude = lon;
	}
	
	public double getLat() {
		return latitude;
	}
	
	public double getLon() {
		return longitude;
	}
	
	public String toString() {
		String out = latitude + ", " + longitude;
		return out;
	}
}
