package HW3;
import java.util.ArrayList;
import java.util.Scanner;
/**ID: 113923920
 * CSE 214 Homework 4
 * @author Yuqing Wang
 * R02
 */
public class IntersectionSimulator {
	public static void main(String[] args) {
		
		if(args.length>1) {
			int simulationTime = Integer.parseInt(args[0]);
			double arrivalProbability = Double.parseDouble(args[1]);
			Boolean checkArrProb = (arrivalProbability>0&&arrivalProbability<=1);
			
			int numRoad = Integer.parseInt(args[2]);
			Boolean checkNumRoad = (numRoad>0&&numRoad<=4);
			
			String[] roadNames = new String[numRoad];
			Boolean checkRoadNames = true;
			for(int i=0; i<numRoad; i++) {
				roadNames[i] = args[3+i];
				if(args[3+i] == null || args[3+i] == "") {
					checkRoadNames = false;
				}
				else {
					boolean checkDuplicate = true;
					for(int j=0; j<i; j++) {
						if(roadNames[j].equalsIgnoreCase(args[3+i])) {
							checkDuplicate = false;
							break;
						}
					}
					
					if(checkDuplicate) {
						roadNames[i] = args[3+i];
					}
					else if(!checkDuplicate) checkRoadNames = false;
					
				}
			}
			
			
			int[] maxGreenTimes = new int[numRoad];
			for(int i=0; i<numRoad; i++) {
				maxGreenTimes[i] = Integer.parseInt(args[3+numRoad+i]);
			}
			
			if(checkArrProb&&checkNumRoad&&checkRoadNames) {
				simulate(simulationTime,arrivalProbability,roadNames, maxGreenTimes);
				System.out.println();
				System.out.println("Starting Simulation... \n");
			}
			else if(!checkArrProb) System.out.println("The value of arrival probability you input is illegal.");
			else if(!checkNumRoad) System.out.println("The number of roads you input is illegal.");
			else if(!checkRoadNames) System.out.println("The road name you input is duplicate or null.");
			
		}
		
		
		
		else {
			Scanner stdin = new Scanner(System.in);
			
			System.out.print("Input the simulation time: ");
			int simulationTime = stdin.nextInt();
			
			System.out.print("Input the arrival probability: ");
			double arrivalProbability = stdin.nextDouble();
			Boolean arrProb = false;
			while(!arrProb) {
				if(arrivalProbability>0&&arrivalProbability<=1) {
					arrProb = true;
				}
				else {
					System.out.println("The value of arrival probability you input is illegal. "
						+ "It shoule greater than 0 and smaller or equal to 1. Please enter again.");
					System.out.print("Input the arrival probability: ");
					arrivalProbability = stdin.nextDouble();
				}
			}
			
			System.out.print("Input number of Streets: ");
			int numRoad = stdin.nextInt();
			Boolean checkNumRoad = false;
			while(!checkNumRoad) {
				if(numRoad>0&&numRoad<=4) {
					checkNumRoad = true;
				}
				else {
					System.out.println("The number of roads you input is illegal. "
							+ "It shoule greater than 0 and smaller or equal to 4. Please enter again.");
					System.out.print("Input number of Streets: ");
					numRoad = stdin.nextInt();
				}
			}
			
			stdin.nextLine();
			String[] roadNames = new String[numRoad];
			for(int i=0; i<numRoad; i++) {
				System.out.print("Input Street " + (int)(i+1) + " name: ");
				String name = stdin.nextLine();
				if(name == null || name == "") {
					System.out.println("The name is null.");
					i--;
				}
				else {
					boolean duplicate = false;
					for(int j=0; j<i; j++) {
						if(roadNames[j].equalsIgnoreCase(name)) {
							System.out.println("Duplicate Detected.");
							duplicate = true;
							i--;
							break;
						}
					}
					
					if(duplicate == false) {
						roadNames[i] = name;
					}
					
				}
			}
			
			int[] maxGreenTimes = new int[numRoad];
			for(int i=0; i<numRoad; i++) {
				System.out.print("Input max green time for " + roadNames[i] + ": ");
				int greenTime = stdin.nextInt();
				maxGreenTimes[i] = greenTime;
			}
			stdin.close();
			System.out.println();
			System.out.println("Starting Simulation... \n");
			
		
			simulate(simulationTime,arrivalProbability,roadNames, maxGreenTimes);
		}
	
	}
	
	
	/**
	 * The actual simulation method.
	 * @param simulationTime The times that allow vehicle arrive.
	 * @param arrivalProbability The probability for vehicle arrive to each lane. 
	 * @param roadNames The names for each road in the intersection.
	 * @param maxGreenTimes The max green time for the light of each road in the intersection
	 */
	public static void simulate(int simulationTime, double arrivalProbability, String[] roadNames, int[] maxGreenTimes) {
		TwoWayRoad[] roads = new TwoWayRoad[roadNames.length];
		for(int i=0; i<roads.length; i++) {
			roads[i] = new TwoWayRoad(roadNames[i], maxGreenTimes[i]);
		}
		Intersection intersection = new Intersection(roads);
		BooleanSource arrival = new BooleanSource(arrivalProbability);
		int timeStep = 1;
		
		//Statistics:
		int currentWait = 0;
		int totalWaitTime = 0;
		int totalPass = 0;
		double averageWaitTime = 0;
		int longestWaitTime = 0;
		int arrivalNum=0;
		int departNum = 0;
		
		//divisors
		String betweenSteps = "";
		for(int i=0; i<81; i++) {
			betweenSteps+= "#";
		}
		String indent = "    ";
		
		//simulation with cars arrival
		while(timeStep<=simulationTime) {
			
			System.out.println(betweenSteps);
			System.out.println("Time Step: " + timeStep);
			System.out.println();
			
			//Cars Arrive
			
			ArrayList<Vehicle> arrivalCar = new ArrayList<Vehicle>();
			ArrayList<String> output = new ArrayList<String>();
			for(int i=0; i<roadNames.length;i++) {
				for(int j=0; j<TwoWayRoad.NUM_WAYS; j++) {
					for(int k=0; k<TwoWayRoad.NUM_LANES; k++) {
						if(arrival.occurs()) {
							Vehicle newVehicle = new Vehicle(timeStep);
							intersection.enqueueVehicle(i, j, k, newVehicle);
							int zero = 2-newVehicle.getSerialId()/10;
							String s ="Car[";
							String way = null;
							String lane = null;
							for(int a=0; a<zero; a++) {
								s+="0";
							}
							switch(j) {
							case 0: way = "FORWARD"; break;
							case 1: way = "BACKWARD";
							}
							switch(k) {
							case 0: lane = "LEFT"; break;
							case 1: lane = "MIDDLE"; break;
							case 2: lane = "RIGHT";
							}
							arrivalCar.add(newVehicle);
							String result = indent + indent + s + newVehicle.getSerialId() + "] entered " + roadNames[i] + ", going " + way + "in " + lane +" lane.";
							output.add(result);
						}
					}
				}
			}
			arrivalNum += arrivalCar.size();
			
			LightValue light =  intersection.getCurrentLightValue();
			int timer = intersection.getCountdownTimer();
			Vehicle[] passing = intersection.timestep();
			
			System.out.println(indent+ light + " light for " + roadNames[intersection.getLightIndex()] + ".");
			System.out.println(indent + "Timer = " + timer);
			System.out.println();
			
			
			System.out.println(indent + "ARRIVING CARS: ");
			
			for(int i=0; i<output.size(); i++) {
				System.out.println(output.get(i));
			}
			
			System.out.println();
			
			System.out.println(indent + "PASSING CARS: ");
			
			
			//Cars depart
			for(int i=0; i<passing.length; i++) {
				String s = "";
				int zero = 2-passing[i].getSerialId()/10;
				s += indent + "Car[" ;
				for(int j=0; j<zero; j++) {
					s += "0";
				}
				s += passing[i].getSerialId() + "]";
				int waitTime = timeStep-passing[i].getTimeArrived();
				System.out.println(indent + s + " passes through. Wait time of " + waitTime + ".");
				if(waitTime>longestWaitTime) longestWaitTime=waitTime;
				totalWaitTime += waitTime;
				departNum++;
			}
			System.out.println();
			totalPass = departNum;
			
			System.out.println();
			intersection.display();
			timeStep++;
			
			currentWait = arrivalNum-departNum;
			if(totalPass!=0) {
				averageWaitTime = (double) totalWaitTime/totalPass;
			}
			else averageWaitTime = 0;
			
			System.out.println("\n");
			System.out.println(indent + "STATISTICS: ");
			System.out.println(indent + indent + String.format("%-24s", "Cars currently waiting: ") + currentWait + " cars");
			System.out.println(indent + indent + String.format("%-24s", "Total cars passed: ") + totalPass + " cars");
			System.out.println(indent + indent + String.format("%-24s", "Total wait time: ") + totalWaitTime + " turns");
			System.out.println(indent + indent + String.format("%-24s%.2f", "Average wait time: ", averageWaitTime) + " turns");
			System.out.println();
			
		}
		
		
		//Stop simulation
		while(currentWait!=0) {
				System.out.println(betweenSteps);
				System.out.println();
				System.out.println("Time Step: " + timeStep);
				System.out.println();
				
				LightValue light =  intersection.getCurrentLightValue();
				int timer = intersection.getCountdownTimer();
				departNum = 0;
				Vehicle[] depart = intersection.timestep();
				System.out.println(indent + "Timer = " + timer);
				System.out.println(indent+light + " light for " + roadNames[intersection.getLightIndex()] + ".");
				
				System.out.println();
			
				System.out.println("Cars no longer arriving.");
				System.out.println();
			
				System.out.println("ARRIVING CARS:");
				System.out.println();
			
				System.out.println("PASSING CARS:");
				for(int i=0; i<depart.length; i++) {
					if(depart[i] == null) break;
					String s = "";
					int zero = 2-depart[i].getSerialId()/10;
					s += indent + "Car[" ;
					for(int j=0; j<zero; j++) {
						s += "0";
					}
					s += depart[i].getSerialId() + "]";
					int waitTime = timeStep-depart[i].getTimeArrived();
					System.out.println(indent + s + " passes through. Wait time of " + waitTime + ".");
					totalWaitTime += waitTime;
					if(waitTime>longestWaitTime) longestWaitTime = waitTime;
					departNum++;
				}
				totalPass = totalPass + departNum;
				
				currentWait = arrivalNum - totalPass;
				intersection.display();
				timeStep++;
				if(totalPass!=0) {
					averageWaitTime = (double)totalWaitTime/totalPass;
				}
				else averageWaitTime = 0;
				
				
				System.out.println();
				System.out.println(indent + "STATISTICS: ");
				System.out.println(indent + indent + String.format("%-24s", "Cars currently waiting: ") + currentWait + " cars");
				System.out.println(indent + indent + String.format("%-24s", "Total cars passed: ") + totalPass + " cars");
				System.out.println(indent + indent + String.format("%-24s", "Total wait time: ") + totalWaitTime + " turns");
				System.out.println(indent + indent + String.format("%-24s%.2f", "Average wait time: ", averageWaitTime) + " turns");
				System.out.println();
			
		}
		
		System.out.println(betweenSteps);
		System.out.println(betweenSteps);
		System.out.println(betweenSteps);
		
		System.out.println();
		System.out.println("SIMULATION SUMMARY: ");
		System.out.println(indent + String.format("%-22s", "Total Time: ") + timeStep + " steps");
		System.out.println(indent + String.format("%-22s", "Total cars passed: ") + totalPass + " vehicles");
		System.out.println(indent + String.format("%-22s", "Longest wait time: ") + longestWaitTime + " turns");
		System.out.println(indent + String.format("%-22s", "Total wait time: ") + totalWaitTime + " turns");
		System.out.println(indent + String.format("%-22s%.2f", "Average wait time: ", averageWaitTime) + " turns");
	}
	

	
	
}
