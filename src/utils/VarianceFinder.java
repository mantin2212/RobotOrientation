package utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * this class is used to find the variance of multiple random variables, using
 * their returned values.
 * 
 * @author noam mantin
 */
public class VarianceFinder {

	// the sum of the variables' returned values
	private RealVector variableResultSum;
	// the sum of the variables' returned values' squares
	private RealVector variableResultSquareSum;

	// the number of values returned
	private int resultNumber = 0;

	/**
	 * creates a new {@link VarianceFinder} object, with a given variable number
	 * 
	 * @param variableNumber
	 *            the number of random variables
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

	public RealVector getVarianceVector() {

		RealVector resultSquareMeanVector = variableResultSquareSum.mapDivide(resultNumber);
		RealVector resultMeanVector = variableResultSum.mapDivide(resultNumber);

		return resultSquareMeanVector.subtract(Utils.getSquared(resultMeanVector));
	}
}
