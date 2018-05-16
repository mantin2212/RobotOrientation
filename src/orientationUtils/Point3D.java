package orientationUtils;

/**
 * A class representing a 3D point with x, y, and z positions 
 *
 */
public class Point3D {

	private double x;
	private double y;
	private double z;

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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

	public void move(double dx, double dy, double dz) {
		x += dx;
		y += dy;
		z += dz;
	}
}