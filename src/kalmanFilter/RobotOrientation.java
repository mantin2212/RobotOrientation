package kalmanFilter;

import java.util.function.Supplier;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import odometry.OdometryHandler;
import orientationUtils.OrientationConstants;
import orientationUtils.Point3D;
import orientationUtils.RelativeDataSupplier;
import orientationUtils.Utils;

public class RobotOrientation {

	private Supplier<Double> accelerometerX;
	private Supplier<Double> accelerometerY;
	private Supplier<Double> accelerometerZ;

	private OdometryHandler odometryHandler;

	private KalmanFilter movementFilter;
	private Point3D position;

	private RelativeDataSupplier timeController;

	public RobotOrientation(Supplier<Double> accelerometerX, Supplier<Double> accelerometerY,
			Supplier<Double> accelerometerZ, Supplier<Double> leftEncoder, Supplier<Double> rightEncoder,
			double robotWidth) {

		this.accelerometerX = accelerometerX;
		this.accelerometerY = accelerometerY;
		this.accelerometerZ = accelerometerZ;

		this.odometryHandler = new OdometryHandler(leftEncoder, rightEncoder, robotWidth);

	}

	public void initialize(Point3D initialPosition, Supplier<Double> getRelativeTime) {

	}

	public void update() {
	}

	public Point3D getPosition() {
		return position;
	}

}