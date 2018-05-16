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

import orientationUtils.OrientationConstants;
import orientationUtils.Point3D;
import orientationUtils.RelativeDataSupplier;
import orientationUtils.Utils;

public class RobotOrientation {

	private KalmanFilter movementFilter;
	private Point3D position;

	private RelativeDataSupplier timeController;

	public RobotOrientation(double robotWidth) {

	}

	public void initialize(Point3D initialPosition, Supplier<Double> getRelativeTime) {

	}

	public void update() {
	}

	public Point3D getPosition() {
		return position;
	}

}