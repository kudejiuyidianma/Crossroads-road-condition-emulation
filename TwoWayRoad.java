package HW3;
import java.util.ArrayList;
/**ID: 113923920
 * CSE 214 Homework 4
 * @author Yuqing Wang
 * R02
 */
public class TwoWayRoad {
	
	public final static int FORWARD_WAY = 0;

	public final static int BACKWARD_WAY = 1;

	public final static int NUM_WAYS = 2;

	public final static int LEFT_LANE = 0;

	public final static int MIDDLE_LANE = 1;

	public final static int RIGHT_LANE = 2;

	public final static int NUM_LANES = 3;

	private String name;

	private int greenTime;
	
	private int leftSignalGreenTime;
	
	private VehicleQueue[][] lanes;
	
	private LightValue lightValue;

	
	/**
	 * Default Constructor. 
	 * @param initName The name of the road.
	 * @param initGreenTime The amount of time that the light will be active for this particular road. 
	 * This is the total of the time the light should display green for cars going forward/turning right, as well as for cars going left.
	 */
	public TwoWayRoad(String initName, int initGreenTime) {
		if(initGreenTime>0) {
			lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
			for(int i=0; i<NUM_WAYS; i++) {
				for(int j=0; j<NUM_LANES; j++) {
					lanes[i][j] = new VehicleQueue();
				}
			}
			this.name = initName;
			this.greenTime = initGreenTime;
			this.leftSignalGreenTime = (int) Math.floor(1.0/NUM_LANES*initGreenTime);
		}
		else if(initGreenTime<=0 || initName == null) 
			throw new IllegalArgumentException("initGreenTime > 0 and initName should not be null.");
	}
	
	
	
	/**
	 * The getter for the amount of time that the light will be active for the particular road.
	 * @return the green time for the particular road.
	 */
	public int getGreenTime() {
		return this.greenTime;
	}
	
	
	/**
	 * The getter for the light value for the particular road.
	 * @return the light value for the particular road.
	 */
	public LightValue getLightValue() {
		return this.lightValue;
	}
	

	
	/**
	 * The getter for the name for the particular road.
	 * @return The name for the particular road.
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * Checks if a specified lane is empty.
	 * @param wayIndex The direction of the lane.
	 * @param laneIndex The index of the lane to check.
	 * @return true if the lane is empty, else false.
	 */
	public boolean isEmpty(int wayIndex, int laneIndex) {
		if(wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2) 
			throw new IllegalArgumentException();			
		return lanes[wayIndex-1][laneIndex-1].isEmpty();
	}
	
	
	
	/**
	 * Executes the passage of time in the simulation. The timerVal represents the current value of a count down timer counting down total green time steps. 
	 * @param timerVal The current timer value, determines the state of the light.
	 * @return An array of Vehicles that has been dequeued during this time step.
	 */
	public Vehicle[] proceed(int timerVal) {
		if(lanes != null) {
			if(timerVal>0) {
				ArrayList<Vehicle> list = new ArrayList<Vehicle>();
				
				Boolean turnLeft = lanes[FORWARD_WAY][RIGHT_LANE].isEmpty()&&lanes[FORWARD_WAY][MIDDLE_LANE].isEmpty()
						&&lanes[BACKWARD_WAY][RIGHT_LANE].isEmpty()&&lanes[BACKWARD_WAY][MIDDLE_LANE].isEmpty();
				Boolean turnRed = lanes[FORWARD_WAY][LEFT_LANE].isEmpty()&&lanes[BACKWARD_WAY][LEFT_LANE].isEmpty();
				
				if(timerVal>this.leftSignalGreenTime&&(!turnLeft)) {
					this.lightValue = LightValue.GREEN;
				}
				else if(timerVal<=this.leftSignalGreenTime||turnLeft) {
					this.lightValue = LightValue.LEFT_SIGNAL;
				}
				else if(turnRed) {
					this.lightValue = LightValue.RED;
				}
				
				switch(lightValue) {
				case GREEN: {
					for(int j=0; j<NUM_WAYS; j++) {
						for(int k=0 ; k<4; k++) {
							if(lanes[j][MIDDLE_LANE].isEmpty()) break;
							list.add(lanes[j][MIDDLE_LANE].dequeue());
							if(lanes[j][MIDDLE_LANE].isEmpty()) break;
							
						}
						
						for(int k=0 ; k<4; k++) {
							if(lanes[j][RIGHT_LANE].isEmpty()) break;
							list.add(lanes[j][RIGHT_LANE].dequeue());
							if(lanes[j][RIGHT_LANE].isEmpty()) break;
						}
					}
					break;
				}	
				case LEFT_SIGNAL: {
					for(int k=0 ; k<4; k++){
						if(lanes[FORWARD_WAY][LEFT_LANE].isEmpty()) break;
						list.add(lanes[FORWARD_WAY][LEFT_LANE].dequeue());
						if(lanes[FORWARD_WAY][LEFT_LANE].isEmpty()) break;
					}
					for(int k=0 ; k<4; k++){
						if(lanes[BACKWARD_WAY][LEFT_LANE].isEmpty()) break;
						list.add(lanes[BACKWARD_WAY][LEFT_LANE].dequeue());
						if(lanes[BACKWARD_WAY][LEFT_LANE].isEmpty()) break;
					}
					break;
				}
				case RED:{}
				}
				
				
				Vehicle[] proceed = new Vehicle[list.size()];
				for(int j=0; j<list.size(); j++) {
					proceed[j] = list.get(j);
				}
				int i=proceed.length;
				
				return proceed;
			}
			else throw new IllegalArgumentException("timeVal should greater than 0.");
		}
		System.out.println("TwoWayRoad object is not instantiated.");
		return null;
	}
	
	
	
	/**
	 * Enqueues a vehicle into a the specified lane.
	 * @param wayIndex The direction the car is going in.
	 * @param laneIndex The lane the car arrives in.
	 * @param vehicle The vehicle to enqueue; must not be null.
	 */
	public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle) {
		if(lanes!= null) {
			if(!(wayIndex>1||wayIndex<0||laneIndex<0||laneIndex>2||vehicle == null)) {
				lanes[wayIndex][laneIndex].enqueue(vehicle);
			}
			else throw new IllegalArgumentException();
		}
	}
	
	
	/**
	 * Display the cars in the specified lane.
	 * @param wayIndex The direction the car is going in.
	 * @param laneIndex The lane the car arrives in.
	 */
	public void displayLane(int wayIndex, int laneIndex) {
		ArrayList<Vehicle> display = new ArrayList<Vehicle>();
		VehicleQueue queue = lanes[wayIndex][laneIndex];
		
		while(!queue.isEmpty()) {
			display.add(queue.dequeue());
		}
		for(int i=0;i<display.size(); i++) {
			queue.enqueue(display.get(i));
		}
		
		
			if(wayIndex == FORWARD_WAY) {
				for(int i=0; i<=30-(5*display.size());i++){
					System.out.print(" ");
				}
				int size = display.size();
				for(int i=size-1; i>=0; i--) {
					int zero = 2-display.get(i).getSerialId()/10;
					System.out.print("[");
					for(int j=0; j<zero; j++) {
						System.out.print("0");
					}
					System.out.print(display.get(i).getSerialId() + "]");
				}
			}
			
			else if(wayIndex == BACKWARD_WAY) {
				for(int i=0; i<display.size();i++) {
					int zero = 2-display.get(i).getSerialId()/10;
					System.out.print("[");
					for(int j=0; j<zero; j++) {
						System.out.print("0");
					}
					System.out.print(display.get(i).getSerialId() + "]");
				}
				
			
		
		}
	}
}
