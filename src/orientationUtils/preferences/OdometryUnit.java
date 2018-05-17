package orientationUtils.preferences;

import java.util.function.Supplier;

import orientationUtils.RelativeDataSupplier;

public class OdometryUnit {
	private RelativeDataSupplier leftDistanceSupplier;
	private RelativeDataSupplier rightDistanceSupplier;
	private double robotWidth;

	public OdometryUnit(Supplier<Double> leftEncoder, Supplier<Double> rightEncoder, double robotWidth) {
		this.leftDistanceSupplier = new RelativeDataSupplier(leftEncoder);
		this.rightDistanceSupplier = new RelativeDataSupplier(rightEncoder);
		this.robotWidth = robotWidth;
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
}
