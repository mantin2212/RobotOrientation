package errorHandler;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * this class is meant to find the bias (average error) of a set of devices,
 * usually sensors, by analyzing their measurements in a static state.
 * 
 * @author noam mantin
 */
class BiasFinder {

	// the sum of the measurement vectors
	private RealVector meaesurementSumVector;

	// the number of measurements, set to 0
	private int measurementNumber = 0;

	/**
	 * creates a new {@link BiasFinder} object, with a default result sum (0 vector)
	 * 
	 * @param measurementVectorSize
	 *            the number of devices
	 */
	public BiasFinder(int measurementVectorSize) {
		this.meaesurementSumVector = new ArrayRealVector(measurementVectorSize);
	}

	/**
	 * adds a received measurement vector to the analysis.
	 * 
	 * @param newMeasurementVector
	 *            the measured vector
	 */
	protected void addMeasurement(double... newMeasurementVector) {
		if (newMeasurementVector.length == meaesurementSumVector.getDimension()) {
			// adding the new measurement
			meaesurementSumVector.add(new ArrayRealVector(newMeasurementVector));
			// updating the measurement number
			measurementNumber++;
		} else
			throw new IllegalArgumentException("illegal measurement vector size");
	}

	/**
	 * returns a vector, which contains the average bias of the data suppliers,
	 * according to a received expected vector
	 * 
	 * @param expected
	 *            the data which should be measured
	 * @return the average difference between the measurements to the expected
	 *         measurement vector
	 */
	// TODO - change name to getBiasVector
	public RealVector getErrorVector(RealVector expected) {
		/*
		 * calculating the average bias using: bias = Sm/n-e, when: Sm- the sum of the
		 * measurements, n- the number of measurements, and e- the expected state the
		 * sensors should measure
		 */
		RealVector errorAvg = meaesurementSumVector.mapDivide(measurementNumber).subtract(expected);

		return errorAvg;
	}
}