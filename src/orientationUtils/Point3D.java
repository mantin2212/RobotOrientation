package orientationUtils;

/**
 * A class representing a 3D point with x, y, and z positions and yaw, roll, and
 * pitch angles
 *
 */
public class Point3D {

	private double x;
	private double y;
	private double z;

	private double yaw;
	private double pitch;
	private double roll;

	public Point3D(double x, double y, double z, double yaw, double roll, double pitch) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
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

	public void move(double dx, double dy, double dz) {
		x += dx;
		y += dy;
		z += dz;
	}
}