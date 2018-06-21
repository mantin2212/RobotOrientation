package orientationUtils.preferences;

import java.util.function.Supplier;

import orientationUtils.RelativeDataSupplier;

public class OdometryUnit {
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

	private Supplier<Double> yawSupplier;

	public OdometryUnit(Supplier<Integer> leftEncoder, Supplier<Integer> rightEncoder, double robotWidth,
			Supplier<Double> yawSupplier) {
		// creating the encoders' data handlers using the suppliers
		this.leftDistanceSupplier = RelativeDataSupplier.fromIntegerSupplier(leftEncoder);
		this.rightDistanceSupplier = RelativeDataSupplier.fromIntegerSupplier(rightEncoder);
		this.robotWidth = robotWidth;
		this.yawSupplier = yawSupplier;
	}

	public double getLeftDistance() {
		return leftDistanceSupplier.get();
	}

	public double getRightDistance() {
		return rightDistanceSupplier.get();
	}

	public double getRobotWidth() {
		return robotWidth;
	}

	public double getYaw() {
		return yawSupplier.get();
	}
}
