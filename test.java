package HW3;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class test {
	public static void main(String[] args) {
		TwoWayRoad r1 = new TwoWayRoad("r1",4);
		TwoWayRoad r2 = new TwoWayRoad("r2",3);
		TwoWayRoad[] roads = new TwoWayRoad[2];
		roads[0] = r1;
		roads[1] = r2;
		Intersection intersection = new Intersection(roads);
		Vehicle vehicle0= new Vehicle(1);
		Vehicle vehicle1= new Vehicle(1);
		Vehicle vehicle2= new Vehicle(1);
		
		r1.enqueueVehicle(0, 0, vehicle0);
		r1.enqueueVehicle(0, 1, vehicle1);
		r1.enqueueVehicle(0, 2, vehicle2);
		r1.displayLane(0, 1);
		
		r1.proceed(4);
		
		r1.displayLane(0, 1);
		}
		
		
	
}
