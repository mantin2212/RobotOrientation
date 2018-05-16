package orientationUtils;

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
}
