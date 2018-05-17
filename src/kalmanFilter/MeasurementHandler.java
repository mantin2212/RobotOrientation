package kalmanFilter;

import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import orientationUtils.Orientation3D;
import orientationUtils.Utils;

public class MeasurementHandler implements Function<RealVector, RealVector> {

	private Orientation3D initialState;

	private Supplier<Double> getYaw;
	private Supplier<Double> getPitch;
	private Supplier<Double> getRoll;

	private RealVector biases;

	public MeasurementHandler(Orientation3D initialState, Supplier<Double> getYaw, Supplier<Double> getPitch,
			Supplier<Double> getRoll, RealVector biases) {
		this.initialState = initialState;

		this.getPitch = getPitch;
		this.getYaw = getYaw;
		this.getRoll = getRoll;

		if (biases.getDimension() == 3)
			this.biases = biases;
		else
			throw new IllegalArgumentException("the biases' vector should have 3 elements");
	}

	public MeasurementHandler(Orientation3D initialState, Supplier<Double> getYaw, Supplier<Double> getPitch,
			Supplier<Double> getRoll) {
		this(initialState, getYaw, getPitch, getRoll, new ArrayRealVector(new double[] { 0, 0, 0 }));
	}

	private RealVector toNavigationFrame(RealVector vector) {
		// calculating the current orientation angles
		double yaw = initialState.getYaw() + getYaw.get();
		double pitch = initialState.getPitch() + getPitch.get();
		double roll = initialState.getRoll() + getRoll.get();

		RealMatrix transformation = Utils.getTransformationMatrix(yaw, roll, pitch);
		return transformation.preMultiply(vector);
	}

	@Override
	public RealVector apply(RealVector measurement) {
		return toNavigationFrame(measurement).subtract(biases);
	}
}
