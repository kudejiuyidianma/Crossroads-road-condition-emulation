package HW3;
/**ID: 113923920
 * CSE 214 Homework 4
 * @author Yuqing Wang
 * R02
 */
public class Intersection{
	private final int MAX_ROADS = 4;
	
	private TwoWayRoad[] roads;
	
	private int lightIndex;
	
	private int countdownTimer;
	
	
	/**
	 * Constructor which initializes the roads array.
	 * @param initRoads
	 */
	public Intersection(TwoWayRoad[] initRoads) {
		if(initRoads!=null&&initRoads.length<=MAX_ROADS) {
			boolean indice = true;
			for(int i=0; i<initRoads.length; i++) {
				indice = indice && (initRoads[i] == null);
				if(indice == false) break;
			}
			this.roads = initRoads;	
			this.lightIndex = 0;
			this.countdownTimer = roads[lightIndex].getGreenTime();
		}
		else throw new IllegalArgumentException();
	}
	
	
	/**
	 * Performs a single iteration through the intersection. 
	 * @return An array of Vehicles which have passed though the intersection during this time step.
	 */
	public Vehicle[] timestep() {
		Vehicle[] answer = roads[lightIndex].proceed(countdownTimer);
		if(lightIndex != roads.length-1) {
			if(countdownTimer!=1) {
				countdownTimer--;
			}
			else if(countdownTimer == 1) {
				lightIndex++;
				this.countdownTimer = roads[lightIndex].getGreenTime();
			}
		}
		else {
			if(countdownTimer!=1) {
				countdownTimer--;
			}
			else if(countdownTimer == 1) {
				lightIndex = 0;
				this.countdownTimer = roads[lightIndex].getGreenTime();
			}
		}
		return answer;
		
	}
	
	
	/**
	 * Enqueues a vehicle onto a lane in the intersection.
	 * @param roadIndex Index of the road in roads which contains the lane to enqueue onto.
	 * @param wayIndex Index of the direction the vehicle is headed.
	 * @param laneIndex Index of the lane on which the vehicle is to be enqueue. 
	 * @param vehicle The Vehicle to enqueue onto the lane.
	 */
	public void enqueueVehicle(int roadIndex, int wayIndex, int laneIndex, Vehicle vehicle) {
		if(roadIndex>=0&&roadIndex<roads.length&&wayIndex>=0&&wayIndex<TwoWayRoad.NUM_WAYS&&
				laneIndex>=0&&laneIndex<TwoWayRoad.NUM_LANES&&vehicle != null) {
			roads[roadIndex].enqueueVehicle(wayIndex, laneIndex, vehicle);
		}
		else throw new IllegalArgumentException();
	}
	
	
	/**
	 * Prints the intersection to the terminal in a neatly formatted manner. 
	 */
	public void display() {
		String div = "";
		for(int i=0; i<31; i++) {
			div+="=";
		}
		String ddiv = "";
		for(int i=0; i<31; i++) {
			ddiv += "-";
		}
		String indent = "    ";
		
		//LightValue
		
		for(int i=0; i<getNumRoads(); i++) {
			System.out.println(indent + roads[i].getName() + ": ");
			System.out.println(indent + String.format("%30s%22s", "FORWARD","BACKWARD"));
			System.out.println(indent + String.format("%-30s%44s", div,div));
			TwoWayRoad road = roads[i];
			System.out.print(indent);
			road.displayLane(TwoWayRoad.FORWARD_WAY, TwoWayRoad.LEFT_LANE);
			System.out.print(" [L]      [R] ");
			road.displayLane(TwoWayRoad.BACKWARD_WAY, TwoWayRoad.RIGHT_LANE);
			System.out.println();
			System.out.println(indent + String.format("%-30s%44s", ddiv, ddiv));
			
			System.out.print(indent);
			road.displayLane(TwoWayRoad.FORWARD_WAY, TwoWayRoad.MIDDLE_LANE);
			System.out.print(" [M]      [M] ");
			road.displayLane(TwoWayRoad.BACKWARD_WAY, TwoWayRoad.MIDDLE_LANE);
			System.out.println();
			System.out.println(indent + String.format("%-30s%44s", ddiv, ddiv));
			
			System.out.print(indent);
			road.displayLane(TwoWayRoad.FORWARD_WAY, TwoWayRoad.RIGHT_LANE);
			System.out.print(" [R]      [L] ");
			road.displayLane(TwoWayRoad.BACKWARD_WAY, TwoWayRoad.LEFT_LANE);
			System.out.println();
			System.out.println(indent + String.format("%-30s%44s", div, div));
			System.out.println();
		}
		
	}
	

	
	/**
	 * The getter for the number of roads.
	 * @return return the number of roads in the intersection.
	 */
	public int getNumRoads() {
		return this.roads.length;
	}
	
	
	/**
	 * The getter for the light index of the intersection that is active.
	 * @return the light index of the intersection
	 */
	public int getLightIndex() {
		return this.lightIndex;
	}
	
	
	/**
	 * The getter for the current count down timer.
	 * @return the current value of the count down timer.
	 */
	public int getCountdownTimer() {
		
		return this.countdownTimer;
	}
	
	
	/**
	 * The getter for the current light value of the road which is active.
	 * @return the current light value.
	 */
	public LightValue getCurrentLightValue() {
		LightValue result = null;
		int left = (int) Math.floor(1.0/TwoWayRoad.NUM_LANES*roads[lightIndex].getGreenTime());
		if(getCountdownTimer()>left) {
			result = LightValue.GREEN;
		}
		else if(getCountdownTimer()<=left) {
			result = LightValue.LEFT_SIGNAL;
		}
		return result;
	}
	
	
}
