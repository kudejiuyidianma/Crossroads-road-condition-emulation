package HW3;
/**ID: 113923920
 * CSE 214 Homework 4
 * @author Yuqing Wang
 * R02
 */
public class BooleanSource {
	
	private double probability;

	
	/**
	 * Constructor which initializes the probability to the indicated parameter.
	 * @param initProbability Probability used to construct this BooleanSource object. The probability should be greater than 0 and less than or equal to 1.
	 */
	public BooleanSource(double initProbability) {
		if(initProbability>1 || initProbability<=0) 
			throw new IllegalArgumentException("0 < initProbability ¡Ü 1.");
		probability = initProbability;
	}
	
	
	/**
	 * Method which returns true with the probability indicated by the member variable probability.
	 * @return Boolean value indicating whether an event has occurred or not.
	 */
	public boolean occurs() {
		return (Math.random() < probability);
	}
	
	
	/**
	 * Getter for the probability.
	 * @return the probability
	 */
	public double getProb() {
		return this.probability;
	}
	
	
	
	/**
	 * Setter for the probability
	 * @param newProb the new value of the probability.
	 */
	public void setProb(double newProb) {
		this.probability = newProb;
	}

}
