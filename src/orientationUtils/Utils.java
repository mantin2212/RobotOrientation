package orientationUtils;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import utils.Point;

public class Utils {

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

	public static RealMatrix getUnitMatrix(int size) {
		RealMatrix result = new Array2DRowRealMatrix(size, size);

		for (int i = 0; i < result.getRowDimension(); i++)
			result.setEntry(i, i, 1);

		return result;
	}

	/**
	 * multiplies a given vector by itself, element by element
	 * 
	 * @param vector
	 *            the vector which should be squared
	 * @return a vector, containing the squares of the elements in the given vector
	 */
	public static RealVector getSquared(RealVector vector) {
		return vector.ebeMultiply(vector);
	}

	/**
	 * calculates and returns a vector by its norm and argument
	 * 
	 * @param norm
	 *            the norm of the wanted vector
	 * @param argument
	 *            the argument of the wanted vector
	 * @return a vector with the given norm and argument, presented as a point
	 */
	public static Point byAngleAndSize(double norm, double argument) {
		// calculating the x, y of the result vector
		double resultX = norm * Math.cos(argument);
		double resultY = norm * Math.sin(argument);
		// returning the result as a point
		return new Point(resultX, resultY);
	}

	/**
	 * rotates a given vector, by a given angle, and returns the result
	 * 
	 * @param vector
	 *            the vector, presented as a point
	 * @param angle
	 *            the wanted rotate angle
	 * @return a vector with the same size as the given one, and with the wanted
	 *         argument (v.arg+angle)
	 */
	public static Point rotate(Point vector, double angle) {
		// calculating the norm and argument of the vector
		double norm = Point.distance(vector, new Point(0, 0));
		double argument = Math.atan(vector.getY() / vector.getX());

		// returning a vector with the same size and the new argument
		return byAngleAndSize(norm, argument + angle);
	}

	/**
	 * uses the cosine law to return the third side of a triangle, knowing the two
	 * other sides and the angle between them
	 * 
	 * @param side1
	 *            one side of a triangle
	 * @param side2
	 *            another side of the triangle
	 * @param angle
	 *            the angle between the two sides above
	 * @return the third side of the triangle, calculated by the formula: s3 =
	 *         sqrt(s1^2+s2^2-2*s1*s2*cos(angle))
	 */
	public static double cosineLaw(double side1, double side2, double angle) {
		return Math.sqrt(side1 * side1 + side2 * side2 - 2 * side1 * side2 * Math.cos(angle));
	}

}