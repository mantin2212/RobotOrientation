package odometry;

import utils.Point;

/**
 * This class contains useful static methods which calculate several important
 * things to be used in the RobotOrientation project
 *
 */
public class Utils {

	/**
	 * This method receives a vector presented in the polar form (norm and
	 * argument) and return the cartesian form of this vector (the X component
	 * and the Y component)
	 * 
	 * @param norm
	 *            the vector's length
	 * @param argument
	 *            the argument of the vector (the argument between the vector
	 *            and the X axis).
	 * @return a point representing the the vector by its X and Y component (the
	 *         cartesian form of the vector) in that order: (X,Y)
	 */
	public static Point convertPolarToCartesian(double norm, double argument) {
		// calculating the X and Y components
		double legX = norm * Math.cos(argument);
		double legY = norm * Math.sin(argument);
		// returning the result as a point
		return new Point(legX, legY);
	}

	/**
	 * rotates a given vector, by a given angle, and returns the result
	 * 
	 * @param vector
	 *            the vector, represented in the cartesian form (as a point)
	 * @param angle
	 *            the wanted rotate angle
	 * @return a vector with the same size as the given one, and with the wanted
	 *         argument (v.arg+angle). The returned vector is represented in the
	 *         cartesian form (by it's X and Y components) as a point
	 */
	public static Point rotateVector(Point vector, double angle) {
		// calculating the norm and argument of the vector
		double norm = Point.distance(vector, new Point(0, 0));
		double argument = Math.atan(vector.getY() / vector.getX());

		// returning a vector with the same size and the new argument
		return convertPolarToCartesian(norm, argument + angle);
	}

	/**
	 * uses the cosine law to return the third side of a triangle, knowing the
	 * two other sides and the angle between them
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