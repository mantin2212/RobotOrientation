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
	 * the encoders of both sides
	 * 
	 * @see RelativeDataHandler
	 */
	private RelativeDataHandler leftEncoder;
	private RelativeDataHandler rightEncoder;
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
		leftEncoder = new RelativeDataHandler(leftEncoderSupplier);
		rightEncoder = new RelativeDataHandler(rightEncoderSupplier);

		this.robotWidth = robotWidth;
	}

	/**
	 * calculates the displacement of the robot since the last check.
	 * 
	 * @param yaw0
	 *            the yaw angle of the robot in the beginning of the movement
	 * @return the displacement vector of the robot, from the last call to the
	 *         function, in the navigation frame
	 */
	public Point getDifference(double yaw0) {
		// the norm of the displacement vector
		double result;

		// getting the displacements of each side of the robot
		double rightDistance = rightEncoder.getLastDifference();
		double leftDistance = leftEncoder.getLastDifference();

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