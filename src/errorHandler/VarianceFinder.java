package errorHandler;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import utils.Utils;

/**
 * this class is used to find the variance of multiple random variables, using
 * their returned values.
 * 
 * in the RobotOrientation project, we use this class to find the variance of
 * the sensors in order to determine the amount of reliability of each one of
 * them. The sensors with the smallest variance are considered to be the most
 * reliable.
 * 
 * @author noam mantin
 */
class VarianceFinder {

	// the sum of the variables' returned values (in our case - the sum of the
	// measurement vectors
	private RealVector variableResultSum;
	// the sum of the variables' returned values' squares
	private RealVector variableResultSquareSum;

	// the number of values returned (in our case - the number of samples taken
	// from the sensor)
	private int resultNumber = 0;

	/**
	 * creates a new {@link VarianceFinder} object, with a given variable number
	 * 
	 * @param variableNumber
	 *            the number of random variables (or - the number of the
	 *            different types of measurements (which can also be referred to
	 *            as the number of sensors))
	 */
	public VarianceFinder(int variableNumber) {
		variableResultSum = new ArrayRealVector(variableNumber);
		variableResultSquareSum = new ArrayRealVector(variableNumber);
	}

	/**
	 * adds a new result vector to the calculation
	 * 
	 * @param newResults
	 *            a vector which should contain the variables' returned values
	 *            (or - the measured samples vector)
	 */
	public void addResultVector(double... newResults) {
		if (newResults.length == variableResultSum.getDimension()) {
			// converting the result vector into a real vector
			RealVector resultVector = new ArrayRealVector(newResults);

			// adding the vector to the result sum
			variableResultSum.add(resultVector);
			// adding the vector^2 to the result square sum
			variableResultSquareSum.add(Utils.getSquared(resultVector));

			resultNumber++;
		} else
			throw new IllegalArgumentException("illegal result vector size");
	}

	/**
	 * calculates and returns the variance vector according to the results added
	 * by {@link #addResultVector(newResults)} until this method was called.
	 * 
	 * @return The variance vector of the given random variables vectors (or -
	 *         the given sensors samples' vectors)
	 * 
	 */
	public RealVector getVarianceVector() {

		RealVector resultSquareMeanVector = variableResultSquareSum.mapDivide(resultNumber);
		RealVector resultMeanVector = variableResultSum.mapDivide(resultNumber);

		return resultSquareMeanVector.subtract(Utils.getSquared(resultMeanVector));
	}
}
