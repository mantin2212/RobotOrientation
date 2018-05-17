package orientationUtils;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
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

	/**
	 * This method returns the transformation matrix to transform measurements from
	 * the body frame to the navigation frame. </br>
	 * </br>
	 * see page 2908 part 2.1.1 in the article for the use of the transformation
	 * matrix. </br>
	 * see page 2909 part 2.1.2 in the article for the building of the
	 * transformation matrix.
	 * 
	 * @return - The transformation matrix to transform measurements from the body
	 *         frame to the navigation frame
	 */
	public RealMatrix getTransformationMatrix() {
		RealMatrix result = new Array2DRowRealMatrix(3, 3);

		// defining variables for the sine and cosine results of the angles
		double cosRoll = Math.cos(roll);
		double cosYaw = Math.cos(yaw);
		double cosPitch = Math.cos(pitch);

		double sinRoll = Math.sin(roll);
		double sinYaw = Math.sin(yaw);
		double sinPitch = Math.sin(pitch);

		// calculating each value of the matrix
		result.setEntry(0, 0, cosYaw * cosPitch);
		result.setEntry(0, 1, cosYaw * sinPitch * sinRoll - cosYaw * sinRoll);
		result.setEntry(0, 2, cosYaw * sinPitch * cosRoll + sinYaw * sinRoll);

		result.setEntry(1, 0, sinYaw * cosPitch);
		result.setEntry(1, 1, sinYaw * sinPitch * sinRoll + cosYaw * cosRoll);
		result.setEntry(1, 2, sinYaw * sinPitch * cosRoll - cosYaw * sinRoll);

		result.setEntry(2, 0, -sinPitch);
		result.setEntry(2, 1, cosPitch * sinRoll);
		result.setEntry(2, 2, cosPitch * cosRoll);

		return result;
	}
}
