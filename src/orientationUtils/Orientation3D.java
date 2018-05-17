package orientationUtils;

import org.apache.commons.math3.linear.RealMatrix;

public class Orientation3D {

	private double yaw;
	private double pitch;
	private double roll;

	public Orientation3D(double yaw, double roll, double pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}

	public double getPitch() {
		return pitch;
	}

	public double getRoll() {
		return roll;
	}

	public double getYaw() {
		return yaw;
	}

	public Orientation3D getRelativeOrientation(double dYaw, double dPitch, double dRoll) {
		return new Orientation3D(yaw + dYaw, roll + dRoll, pitch + dPitch);
	}

	public RealMatrix getTransformationMatrix() {
		return Utils.getTransformationMatrix(yaw, roll, pitch);
	}
}
