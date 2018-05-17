package kalmanFilter;

import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import orientationUtils.Orientation3D;

/**
 * this class handles the measurements of sensors on the robot, and converts
 * them into usable values. this includes two main tasks:<br>
 * 1. neutralizing the sensors' biases<br>
 * 2. converting the measurements from the body frame to the navigation
 * frame<br>
 * <br>
 * in order to achieve the second task, the class follows the robot's
 * orientation, and uses it to build a transformation matrix from the body frame
 * to the navigation frame.<br>
 * calling the {@link #apply(RealVector)} method of this class with a body-frame
 * measurement, it should return the navigation-frame real measurement,
 * neutralizing the biases of the sensors.
 * 
 * see page 2908 part 2.1.1 in the article for the use of the transformation
 * matrix. <br>
 * 
 * @see Orientation3D
 * @see Orientation3D#getTransformationMatrix()
 * 
 * @author noam mantin
 *
 */
public class MeasurementHandler implements Function<RealVector, RealVector> {

	// the initial orientation of the robot
	private Orientation3D initialOrientation;

	// suppliers for the orientation angles
	private Supplier<Double> getYaw;
	private Supplier<Double> getPitch;
	private Supplier<Double> getRoll;

	// the biases of the measuring sensors
	private RealVector biases;

	/**
	 * creates a new {@link MeasurementHandler} object with given initial
	 * orientation, angle suppliers and sensors biases.
	 * 
	 * @param initialOrientation
	 *            the initial orientation of the robot.
	 * @param getYaw
	 *            should supply the robot's yaw angle, compared to the initial yaw
	 *            angle.
	 * @param getPitch
	 *            should supply the robot's pitch angle, compared to the initial
	 *            pitch angle.
	 * @param getRoll
	 *            should supply the robot's roll angle, compared to the initial roll
	 *            angle.
	 * @param biases
	 *            the biases vector of the measuring sensors (usually
	 *            accelerometers)
	 * 
	 * @throws IllegalArgumentException
	 *             if the biases vector doesn't have the correct size (3), an
	 *             exception is thrown.
	 */
	public MeasurementHandler(Orientation3D initialOrientation, Supplier<Double> getYaw, Supplier<Double> getPitch,
			Supplier<Double> getRoll, RealVector biases) {
		this.initialOrientation = initialOrientation;

		this.getPitch = getPitch;
		this.getYaw = getYaw;
		this.getRoll = getRoll;

		if (biases.getDimension() == 3)
			this.biases = biases;
		else
			throw new IllegalArgumentException("the biases' vector should have exactly 3 elements");
	}

	/**
	 * creates a new {@link MeasurementHandler} object with given initial
	 * orientation, angle suppliers and with sensor biases set to 0.
	 * 
	 * @param initialOrientation
	 *            the initial orientation of the robot.
	 * @param getYaw
	 *            should supply the robot's yaw angle, compared to the initial yaw
	 *            angle.
	 * @param getPitch
	 *            should supply the robot's pitch angle, compared to the initial
	 *            pitch angle.
	 * @param getRoll
	 *            should supply the robot's roll angle, compared to the initial roll
	 *            angle.
	 */
	public MeasurementHandler(Orientation3D initialState, Supplier<Double> getYaw, Supplier<Double> getPitch,
			Supplier<Double> getRoll) {
		this(initialState, getYaw, getPitch, getRoll, new ArrayRealVector(new double[] { 0, 0, 0 }));
	}

	/**
	 * sets the biases vector to a given one.
	 * 
	 * @param biases
	 *            the new biases vector.
	 * 
	 * @throws IllegalArgumentException
	 *             if the biases vector doesn't have the correct size (3), an
	 *             exception is thrown.
	 */
	public void setBiasesVector(RealVector biases) {
		if (biases.getDimension() == 3)
			this.biases = biases;
		else
			throw new IllegalArgumentException("the biases' vector should have exactly 3 elements");
	}

	/**
	 * converts a given vector from the body frame to the navigation frame, using
	 * the current orientation of the robot.
	 * 
	 * @param vector
	 *            the vector, in the robot's body frame.
	 * @return the given vector, in navigation frame.
	 */
	private RealVector toNavigationFrame(RealVector vector) {
		// calculating the current orientation
		Orientation3D currentOrientation = initialOrientation.getRelativeOrientation(getYaw.get(), getPitch.get(),
				getRoll.get());

		RealMatrix transformation = currentOrientation.getTransformationMatrix();
		return transformation.preMultiply(vector);
	}

	@Override
	public RealVector apply(RealVector measurement) {
		/*
		 * neutralizing the sensor biases and rotating the measurement according to the
		 * robot's orientation
		 */
		return toNavigationFrame(measurement.subtract(biases));
	}
}
