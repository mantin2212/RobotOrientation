package errorHandler;

import org.apache.commons.math3.linear.RealVector;

/**
 * this class is being used to find the "errors" of the sensors. the "error" of
 * a sensor is defined by the bias and the variance of its measurements.
 *
 * The general flow of the usage of this classe's methods is as follows:
 * <ul>
 * <li>constructing a new ErrorFinder in the size of the number of sensors</li>
 * <li>taking measurements samples from the sensors and adding them to the
 * ErrorFinder via the {@link #addMeasurement(measurements)} method</li>
 * <li>after the wanted number of samples have been taken from the sensors and
 * added to the ErrorFinder, use {@link #getBiasVector(expectedValuesVector)}
 * and {@link #getVarianceVector()} methods to get the bias and variance vectors
 * respectively</li>
 * </ul>
 * 
 * 
 * this class should be used in the disabledPeriodic method of Robot.java
 * (taking a lot of samples in a stationary state is important because in that
 * state you can know what the values that the sensors suppose to return, and
 * thus you can calculate the biases of the sensors)
 *
 */
public class ErrorFinder {

	private BiasFinder biasFinder;
	private VarianceFinder varianceFinder;

	/**
	 * this constructs a new ErrorFinder with a given size. this initializes
	 * both bias finder and variance finder
	 * 
	 * @param VectorSize
	 *            - the size of the measurements vector (this can also be
	 *            referred to as the number of sensors used)
	 */
	public ErrorFinder(int VectorSize) {
		biasFinder = new BiasFinder(VectorSize);
		varianceFinder = new VarianceFinder(VectorSize);
	}

	public void addMeasurement(double... measurement) {
		biasFinder.addMeasurement(measurement);
		varianceFinder.addResultVector(measurement);
	}

	public RealVector getBiasVector(RealVector expectedValuesVector) {
		return biasFinder.getErrorVector(expectedValuesVector);
	}

	public RealVector getVarianceVector() {
		return varianceFinder.getVarianceVector();
	}
}
