package utils;

import java.util.function.Supplier;

public class RelativeDataHandler {

	private Supplier<Double> relativeDataSupplier;

	private double lastValue;
	private double currentValue;

	public RelativeDataHandler(Supplier<Double> relativeValueSupplier) {
		this.relativeDataSupplier = relativeValueSupplier;
		lastValue = 0;
		currentValue = 0;
	}

	public double getLastDifference() {
		double result = currentValue - lastValue;

		lastValue = currentValue;
		currentValue = relativeDataSupplier.get();

		return result;
	}

}
