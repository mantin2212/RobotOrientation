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

import errorHandler.ErrorFinder;
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

	// TODO - maybe find a better name for the variable getRelativeTime
	public void initialize(ErrorFinder errorFinder, Point3D initialPosition, Supplier<Double> getRelativeTime) {
		this.timeController = new RelativeDataSupplier(getRelativeTime);
		this.position = initialPosition;
		/*
		 * handle the biases (use getBiasVector from errorFinder somewhere
		 * somehow)
		 */
		// build kalman filter
		RealVector initialStateEstimate = new ArrayRealVector(new Double[] { 0.0, 0.0, 0.0 });
		RealMatrix processNoise = Utils.getDiagonalMatrix(errorFinder.getVarianceVector());
		RealMatrix measurmentNoise = Utils.get0Matrix();

		ProcessModel processModel = new DefaultProcessModel(OrientationConstants.KalmanFilterMatrices.A_MATRIX,
				OrientationConstants.KalmanFilterMatrices.B_MATRIX, processNoise, initialStateEstimate, null);
		MeasurementModel measurementModel = new DefaultMeasurementModel(
				OrientationConstants.KalmanFilterMatrices.H_MATRIX, measurmentNoise);

		movementFilter = new KalmanFilter(processModel, measurementModel);
	}

	public void update() {
	}

	public Point3D getPosition() {
		return position;
	}

}