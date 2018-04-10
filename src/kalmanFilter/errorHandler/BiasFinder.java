package kalmanFilter.errorHandler;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * this class is meant to find the bias (average error) of a set of devices,
 * usually sensors, by analyzing their measurements in a static state.
 * 
 * @author noam mantin
 */
public class BiasFinder {

	// the sum of the measurement vectors
	private RealVector resultSum;

	// the number of measurement, set to 0
	private int measurementNumber = 0;

	/**
	 * creates a new {@link BiasFinder} object, with a default result sum (0 vector)
	 * 
	 * @param measurementVectorSize
	 */
	public BiasFinder(int measurementVectorSize) {
		this.resultSum = new ArrayRealVector(measurementVectorSize);
	}

	/**
	 * adds a received measurement vector to the statistics.
	 * 
	 * @param measurement
	 *            the measured vector
	 */
	public void addMeasurement(RealVector measurement) {
		if (measurement.getDimension() == resultSum.getDimension()) {
			// adding the new measurement
			resultSum.add(measurement);
			// updating the measurement number
			measurementNumber++;
		} else
			throw new IllegalArgumentException("illegal measurement vector size");
	}

	/**
	 * returns a vector, which contains the average bias of the data suppliers,
	 * according to a received expected vector *
	 * 
	 * @param expected
	 *            the data which should be measured
	 * @return the average difference between the measurements to the expected
	 *         vector
	 */
	public RealVector getErrorVector(RealVector expected) {
		/*
		 * calculating the average bias using: bias = Sm/n-e, when: Sm- the sum of the
		 * measurements, n- the number of measurements, and e- the expected state the
		 * sensors should measure
		 */
		RealVector errorAvg = resultSum.mapDivide(measurementNumber).subtract(expected);

		return errorAvg;
	}
}