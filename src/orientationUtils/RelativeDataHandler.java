package orientationUtils;

import java.util.function.Supplier;

/**
 * This class represents an object with a changing value. </br>
 * The object may be a sensor measuring changing values, or any other data
 * supplier.</br>
 * </br>
 * 
 * The instances of this class have one method called
 * {@link #getLastDifference()}, which returns the difference between the
 * current measured value and the last measured value.</br>
 * </br>
 * 
 * Use an instance of this class when you are interested in the difference
 * between two measurements, and not in each measurment's singular value.
 * 
 * <p>
 * note - in this project, this class is used in order to give us the difference
 * between two measurements of an encoder instead of the value of each
 * measurement.
 * </p>
 * 
 */
public class RelativeDataHandler {

	private Supplier<Double> relativeDataSupplier;

	private double lastValue;

	public RelativeDataHandler(Supplier<Double> relativeValueSupplier) {
		this.relativeDataSupplier = relativeValueSupplier;
		lastValue = relativeDataSupplier.get();
	}

	public double getLastDifference() {
		double currentValue = relativeDataSupplier.get();

		double result = currentValue - lastValue;
		lastValue = currentValue;

		return result;
	}

}
