package kalmanFilter;

import java.util.function.Supplier;

public class TimeController {

	private Supplier<Double> relativeTimeSupplier;

	private double lastTime;
	private double currentTime;

	private TimeController(Supplier<Double> relativeTimeSupplier) {
		this.relativeTimeSupplier = relativeTimeSupplier;
		lastTime = 0;
		currentTime = 0;
	}

	public double getDT() {
		double result = currentTime - lastTime;

		lastTime = currentTime;
		currentTime = relativeTimeSupplier.get();

		return result;
	}
}
