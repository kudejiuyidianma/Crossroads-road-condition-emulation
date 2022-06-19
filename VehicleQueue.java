package HW3;

import java.util.LinkedList;

public class VehicleQueue extends LinkedList<Vehicle>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VehicleNode front;
	private VehicleNode rear;
	private int size;
	
	
	/**
	 * Default Constructor.
	 */
	public VehicleQueue() {
		front = null;
		rear = null;
		size = 0;
	}
	
	
	
	/**
	 * Determine if the queue is empty.
	 * @return queue is empty or not.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}
	
	
	/**
	 * Get the size of the queue
	 * @return the size of the queue 
	 */
	public int size() {
		 return this.size;
	}
	
	
	/**
	 * Add a new car into the queue.
	 * @param newVehicle
	 */
	public void enqueue(Vehicle newVehicle) {
		VehicleNode newNode = new VehicleNode(newVehicle);
		if(front == null) {
			front = newNode;
			rear = newNode;
			size++;
		}
		else{
			rear.setLink(newNode);
			rear = newNode;
			size++;
		}
	}
	
	/**
	 * Get a car out of the queue.
	 * @return the car get out of the queue
	 */
	public Vehicle dequeue() {
		if(front != null) {
			Vehicle answer;
			answer = front.getData();
			front = front.getLink();
			size--;
			if(front == null) rear = null;
			return answer;
		}
		return null; 
	}







	
	
	
}
