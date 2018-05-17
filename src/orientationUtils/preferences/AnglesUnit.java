package orientationUtils.preferences;

import java.util.function.Supplier;

import orientationUtils.Orientation3D;

public class AnglesUnit {

	private Supplier<Double> yaw;
	private Supplier<Double> pitch;
	private Supplier<Double> roll;
	private Orientation3D initialState;

	/**
	 * creates a new {@link MeasurementHandler} object with given initial
	 * orientation, angle suppliers and sensors biases.
	 * 
	 * @param yaw
	 *            should supply the robot's yaw angle, compared to the initial yaw
	 *            angle.
	 * @param pitch
	 *            should supply the robot's pitch angle, compared to the initial
	 *            pitch angle.
	 * @param roll
	 *            should supply the robot's roll angle, compared to the initial roll
	 *            angle.
	 * @param initialState
	 *            the initial orientation of the robot.
	 * 
	 */
	public AnglesUnit(Supplier<Double> yaw, Supplier<Double> pitch, Supplier<Double> roll, Orientation3D initialState) {
		this.yaw = yaw;
		this.roll = roll;
		this.pitch = pitch;
		this.initialState = initialState;
	}

	public double getYaw() {
		return yaw.get();
	}

	public double getPitch() {
		return pitch.get();
	}

	public double getRoll() {
		return roll.get();
	}

	public Orientation3D getInitialState() {
		return initialState;
	}

	public Orientation3D getCurrentState() {
		return initialState.getRelativeOrientation(getYaw(), getPitch(), getRoll());
	}
}
