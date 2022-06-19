package HW3;
/**ID: 113923920
 * CSE 214 Homework 4
 * @author Yuqing Wang
 * R02
 */
public class Vehicle {
	private static int serialCounter = 0;

	private int serialId;

	private int timeArrived;

	
	
	/**
	 * Default Constructor.
	 * @param initTimeArrived Time the vehicle arrived at the intersection.
	 */
	public Vehicle(int initTimeArrived) {
		if(initTimeArrived<=0) throw new IllegalArgumentException("initTimeArrived > 0");
		this.timeArrived = initTimeArrived;
		serialCounter++;
		serialId = serialCounter;
	}
	
	
	
	/**
	 * Getter for serial id.
	 * @return the value of serialId of the car.
	 */
	public int getSerialId() {
		return this.serialId;
	}
	
	
	/**
	 * Getter for arrived time.
	 * @return the value of time when the car arriving.
	 */
	public int getTimeArrived() {
		return this.timeArrived;
	}
}
