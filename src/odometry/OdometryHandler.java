package odometry;

import java.util.function.Supplier;

import orientationUtils.*;
import utils.Point;

/**
 * a class which uses encoders to find the progress of a robot between discrete
 * times
 * 
 * @author noam mantin
 *
 */
public class OdometryHandler {

	/**
	 * the {@link Supplier} of the difference between the encoders of both
	 * sides' values
	 * 
	 * @see RelativeDataSupplier
	 */
	private RelativeDataSupplier leftDistanceSupplier;
	private RelativeDataSupplier rightDistanceSupplier;
	// the width of the robot
	private double robotWidth;

	/**
	 * creates a new {@link OdometryHandler} object, with given parameters
	 * 
	 * @param leftEncoderSupplier
	 *            the value supplier from the left encoder
	 * @param rightEncoderSupplier
	 *            the value supplier from the right encoder
	 * @param robotWidth
	 *            the width of the relevant robot
	 */
	public OdometryHandler(Supplier<Double> leftEncoderSupplier, Supplier<Double> rightEncoderSupplier,
			double robotWidth) {
		// creating the encoders' data handlers using the suppliers
		leftDistanceSupplier = new RelativeDataSupplier(leftEncoderSupplier);
		rightDistanceSupplier = new RelativeDataSupplier(rightEncoderSupplier);

		this.robotWidth = robotWidth;
	}

	/**
	 * Calculates and returns the displacement of the robot since the last
	 * check.</br>
	 * 
	 * for more information about this process, see pages 2910-2911 in the main
	 * article from the README file
	 * 
	 * @param yaw0
	 *            the yaw angle of the robot in the beginning of the movement
	 * @return the displacement vector of the robot, from the last call to this
	 *         method, in the navigation frame.
	 * 
	 */
	public Point getDifference(double yaw0) {
		// the norm of the displacement vector
		double result;

		// getting the displacements of each side of the robot
		double rightDistance = rightDistanceSupplier.get();
		double leftDistance = leftDistanceSupplier.get();

		// calculating the yaw difference using geometry laws
		double yawDifference = (leftDistance - rightDistance) / robotWidth;

		if (rightDistance == leftDistance) {
			// the robot moved in a straight line
			result = rightDistance;
		} else {
			// calculating the displacement of the middle of the robot
			double distance = (rightDistance + leftDistance) / 2;
			// calculating the rotation radius of the robot's movement
			double rotationRadius = distance / yawDifference;
			// using the cosine law to find the robot's displacement's norm
			result = Utils.findThirdSide(rotationRadius, rotationRadius, yawDifference);
		}
		// the argument of the displacement vector
		double arg = yaw0 + 1 / 2 * yawDifference;

		// building the displacement vector by an argument and a norm
		return Utils.byAngleAndSize(result, arg);
	}
}