package HW3;
/**ID: 113923920
 * CSE 214 Homework 4
 * @author Yuqing Wang
 * R02
 */
public class VehicleList {
	private VehicleNode head;
	private VehicleNode tail;
	private VehicleNode cursor;
	
	
	/**
	 * Default constructor.
	 */
	public VehicleList() {
		head = null;
		tail = null;
		cursor = null;
	}
	
	
	/**
	 * Add a new head of the Vehicle linked list.
	 * @param element the vehicle will add at head.
	 */
	public void addNewHead(Vehicle element) {
		VehicleNode newNode = new VehicleNode(element);
		newNode.setLink(head);
		head = newNode;
		if(tail == null) {
			tail = head;
			cursor = head;
		}
	}
	
	/**
	 * Add a new vehicle after head
	 * @param element the vehicle will add after head.
	 */
	public void addAfter(Vehicle element) {
		VehicleNode newNode = new VehicleNode(element);
		if(cursor == null) {
			head = newNode;
			tail = newNode;
			cursor = newNode;
		}
		else {
			newNode.setLink(cursor.getLink());
			cursor.setLink(newNode);
			cursor = newNode;
			if(cursor.getLink() == null) tail = cursor;
		}
		
	}
	
	
	/**
	 * Remove the vehicle after head.
	 */
	public void removeAfter() {
		if(cursor != tail) {
			cursor.setLink(cursor.getLink().getLink());
			if(cursor.getLink()==null) tail = cursor;
		}
		
	}
	
	
	/**
	 * Remove head.
	 */
	public void removeHead() {
		if(head!=null) 
			head = head.getLink();
		if(head == null)
			tail = null;
		cursor = head;
	}
	
	
	/**
	 * Determine if the linked list is empty
	 * @return linked list is empty or not.
	 */
	public boolean isEmpty() {
		return (head == null);
	}
	
	
	
	/**
	 * Get the size of the linked list.
	 * @return the size of the linked list
	 */
	public int size() {
		int size = 0;
		VehicleNode nodePtr = head;
		if(head == null) return size;
		else {
			size++;
			while(nodePtr != tail) {
				nodePtr = nodePtr.getLink();
				size++;
			}
			return ++size;
		}
	}
}
