package errorHandler;

import org.apache.commons.math3.linear.RealVector;

import orientationUtils.VarianceFinder;

public class ErrorFinder {

	private BiasFinder biasFinder;
	private VarianceFinder varianceFinder;

	public ErrorFinder(int size) {
		biasFinder = new BiasFinder(size);
		varianceFinder = new VarianceFinder(size);
	}

	/**
	 * adds a given measurement to the analyzation
	 * 
	 * @param measurement
	 *            the measurement vector
	 */
	public void addMeasurement(double... measurement) {
		biasFinder.addMeasurement(measurement);
		varianceFinder.addResultVector(measurement);
	}

	/**
	 * returns the bias vector of the sensors, according to a given expected vector
	 * 
	 * @param expectedVector
	 *            the expected measurement vector in the current state,.
	 * @return the bias vector of the sensors, or their average error from the
	 *         expected measurement
	 * @see BiasFinder
	 */
	public RealVector getErrorVector(RealVector expectedVector) {
		return biasFinder.getErrorVector(expectedVector);
	}

	/**
	 * calculates and returns the variance vector of the sensors.
	 * 
	 * @return a vector, containing the variance of each sensor
	 * 
	 * @see VarianceFinder
	 */
	public RealVector getVarianceVector() {
		return varianceFinder.getVarianceVector();
	}
}
