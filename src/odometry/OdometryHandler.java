package odometry;

import java.util.function.Supplier;

import orientationUtils.*;
import utils.Point;

public class OdometryHandler {

	private RelativeDataHandler leftEncoder;
	private RelativeDataHandler rightEncoder;

	private double robotWidth;

	public OdometryHandler(Supplier<Double> leftEncoderSupplier, Supplier<Double> rightEncoderSupplier,
			double robotWidth) {
		leftEncoder = new RelativeDataHandler(leftEncoderSupplier);
		rightEncoder = new RelativeDataHandler(rightEncoderSupplier);

		this.robotWidth = robotWidth;
	}

	public Point getDifference(double yaw0) {

		double progress;

		double rightDistance = rightEncoder.getLastDifference();
		double leftDistance = leftEncoder.getLastDifference();

		double yawDifference = (leftDistance - rightDistance) / robotWidth;

		if (rightDistance == leftDistance)
			progress = rightDistance;
		else {

			double distance = (rightDistance + leftDistance) / 2;

			double rotationRadius = distance / yawDifference;

			progress = Utils.findThirdSide(rotationRadius, rotationRadius, yawDifference);
		}

		return orientationUtils.Utils.rotate(new Point(progress, 0), yaw0 + 1 / 2 * yawDifference);
	}
}