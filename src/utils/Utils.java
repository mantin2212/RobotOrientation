package utils;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * This class contains useful static methods which calculate several important
 * things to be used in the RobotOrientation project
 *
 */
public class Utils {

	/**
	 * This method returns the transformation matrix to transform measurements
	 * from the body frame to the navigation frame. </br>
	 * </br>
	 * see page 2908 part 2.1.1 in the article for the use of the transformation
	 * matrix. </br>
	 * see page 2909 part 2.1.2 in the article for the building of the
	 * transformation matrix.
	 * 
	 * @param yaw
	 *            - the measured yaw angle
	 * @param roll
	 *            - the measured roll angle
	 * @param pitch
	 *            - the measured pitch angle
	 * @return - The transformation matrix to transform measurements from the
	 *         body frame to the navigation frame
	 */
	public static RealMatrix getTransformationMatrix(double yaw, double roll, double pitch) {
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

	/**
	 * This method returns the unit matrix (also called "Identity Matrix").
	 * </br>
	 * To learn more about the unit matrix, see
	 * <a href="https://en.wikipedia.org/wiki/Identity_matrix">The Unit
	 * Matrix</a>
	 * 
	 * @param size
	 *            - the size (number of rows and columns) of the unit matrix
	 * @return - a unit matrix in the wanted size
	 */
	public static RealMatrix getUnitMatrix(int size) {
		RealMatrix result = new Array2DRowRealMatrix(size, size);

		for (int i = 0; i < result.getRowDimension(); i++)
			result.setEntry(i, i, 1);

		return result;
	}
}
