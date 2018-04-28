package orientationUtils;

import java.util.function.Supplier;

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
