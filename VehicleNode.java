package HW3;
/**ID: 113923920
 * CSE 214 Homework 4
 * @author Yuqing Wang
 * R02
 */
public class VehicleNode {
	private Vehicle data;
	private VehicleNode link;

	
	/**
	 * Constructor for the vehicle node
	 * @param data Vehicle will be turn into Vehicle node type.
	 */
	public VehicleNode(Vehicle data){
		this.data = data;
		link = null;
	}
	
	
	/**
	 * Getter for the node data
	 * @return the vehicle at the certain node.
	 */
	public Vehicle getData() {
		return this.data;
	}
	
	
	/**
	 * Getter for the next node to the certain node.
	 * @return
	 */
	public VehicleNode getLink() {
		return this.link;
	}
	
	
	/**
	 * Setter for the data of the node
	 * @param newVehicle the new data for the certain note
	 */
	public void setData(Vehicle newVehicle) {
		this.data = newVehicle;
	}
	
	
	/**
	 * Setter for the link of the node
	 * @param newLink new link for the certain node
	 */
	public void setLink(VehicleNode newLink) {
		this.link = newLink;
	}
}
