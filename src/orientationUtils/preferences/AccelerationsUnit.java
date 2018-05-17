package orientationUtils.preferences;

import java.util.function.Supplier;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class AccelerationsUnit {
	private Supplier<Double> accX;
	private Supplier<Double> accZ;
	private Supplier<Double> accY;

	public AccelerationsUnit(Supplier<Double> accelerometerX, Supplier<Double> accelerometerY,
			Supplier<Double> accelerometerZ) {
		this.accX = accelerometerX;
		this.accY = accelerometerY;
		this.accZ = accelerometerZ;
	}

	public double getAccX() {
		return accX.get();
	}

	public double getAccY() {
		return accY.get();
	}

	public double getAccZ() {
		return accZ.get();
	}

	public RealVector getAcceleration() {
		return new ArrayRealVector(new double[] { getAccX(), getAccY(), getAccZ() });
	}
}
