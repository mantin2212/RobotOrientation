package org.usfirst.frc.team2212.odometry;

import java.util.function.Supplier;

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

	public OdometryUnit(Supplier<Double> leftEncoder, Supplier<Double> rightEncoder, double robotWidth,
			Supplier<Double> yawSupplier) {
		// creating the encoders' data handlers using the suppliers
		this.leftDistanceSupplier = new RelativeDataSupplier(leftEncoder);
		this.rightDistanceSupplier = new RelativeDataSupplier(rightEncoder);
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
