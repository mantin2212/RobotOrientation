// TODO - change package name
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
import orientationUtils.preferences.AccelerationsUnit;
import orientationUtils.preferences.OdometryUnit;
import orientationUtils.preferences.AnglesUnit;

public class RobotOrientation {

	private AccelerationsUnit accelerationsUnit;
	private AnglesUnit anglesUnit;

	private OdometryHandler odometryHandler;
	private MeasurementHandler measurementHandler;

	private KalmanFilter movementFilter;
	private Point3D position;

	private RelativeDataSupplier timeController;

	public RobotOrientation(AccelerationsUnit accUnit, AnglesUnit anglesUnit, OdometryUnit odometryUnit) {
		this.accelerationsUnit = accUnit;
		this.anglesUnit = anglesUnit;

		this.measurementHandler = new MeasurementHandler(anglesUnit);
		this.odometryHandler = new OdometryHandler(odometryUnit);
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