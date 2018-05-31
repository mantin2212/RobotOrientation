package odometry;

import java.util.function.Supplier;

import orientationUtils.Utils;
import orientationUtils.preferences.OdometryUnit;
import utils.Point;

/**
 * a class which uses encoders to find the progress of a robot between discrete
 * times
 * 
 * @author noam mantin
 *
 */
public class OdometryHandler {

	private OdometryUnit odometryUnit;

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
	public OdometryHandler(OdometryUnit odometryUnit) {
		this.odometryUnit = odometryUnit;
	}

	/**
	 * Calculates and returns the displacement of the robot since the last
	 * check.</br>
	 * 
	 * for more information about this process, see pages 2910-2911 in the main
	 * article from the README file
	 * 
	 * @return the position rate vector of the robot (ΔX and ΔY), from the
	 *         last call to this method, in the navigation frame.
	 * 
	 */
	public Point getDifference() {
		// getting the yaw angle of the robot in the end of the movement
		double yaw = odometryUnit.getYaw();

		/*
		 * The robot's displacement's norm, i.e the length of the straight line
		 * starting from the previous center location of the robot and ending in
		 * the current center location. Signified in the article by the letter
		 * Δλ
		 */
		double centerDistance;

		/*
		 * Getting the distance each side of the robot has passed. Signified in
		 * the article by the letters a(l) and a(r)
		 */
		double rightDistance = odometryUnit.getRightDistance();
		double leftDistance = odometryUnit.getLeftDistance();

		/*
		 * calculating the yaw difference using geometry laws. Signified by Δϕ
		 * in the article. (equation 22)
		 */
		double yawDifference = (leftDistance - rightDistance) / odometryUnit.getRobotWidth();

		if (rightDistance == leftDistance) {
			// the robot moved in a straight line
			centerDistance = rightDistance;
		} else {
			/*
			 * calculating the length of the arch the middle of the robot has
			 * passed. Signified in the article as ak (equation 21)
			 */
			double distance = (rightDistance + leftDistance) / 2;
			/*
			 * calculating the rotation radius of the robot's movement.
			 * Signified by r in the article. (equation 23).
			 */
			double rotationRadius = distance / yawDifference;
			/*
			 * using the cosine law to find the robot's displacement's norm
			 * (Δλ). (equation 24)
			 */
			centerDistance = Utils.cosineLaw(rotationRadius, rotationRadius, yawDifference);
		}
		// finding the argument of the displacement vector.
		double arg = yaw - 1 / 2 * yawDifference;

		/*
		 * Building the cartesian displacement vector by an argument (arg) and a
		 * norm (Δλ).
		 * 
		 * Now, as we have the argument and the norm of the displacement vector
		 * (or in other words, the polar form of the vector), we can convert it
		 * to the Cartesian form (represented by its X and Y components). Doing
		 * so gives us the robot's position in the navigation system (equation
		 * 26)
		 */
		return Utils.convertPolarToCartesian(centerDistance, arg);
	}
}