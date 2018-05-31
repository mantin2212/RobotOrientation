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
import orientationUtils.Orientation3D;
import orientationUtils.OrientationConstants;
import orientationUtils.Point3D;
import orientationUtils.RelativeDataSupplier;
import orientationUtils.Utils;
import orientationUtils.preferences.AccelerationsUnit;
import orientationUtils.preferences.AnglesUnit;
import orientationUtils.preferences.OdometryUnit;
import utils.Point;

public class RobotOrientation {

	private AccelerationsUnit accelerationsUnit;

	private OdometryHandler odometryHandler;
	private AnglesConvertor measurementHandler;

	private KalmanFilter movementFilter;
	private Point3D position;

	private RelativeDataSupplier timeController;

	public RobotOrientation(AccelerationsUnit accUnit, AnglesUnit anglesUnit, OdometryUnit odometryUnit,
			Point3D initialPosition, Orientation3D initialOrientation, Supplier<Double> getRelativeTime) {
		this.accelerationsUnit = accUnit;

		this.measurementHandler = new AnglesConvertor(anglesUnit);
		this.odometryHandler = new OdometryHandler(odometryUnit);
		
		this.timeController = new RelativeDataSupplier(getRelativeTime);
		this.position = initialPosition;
	
	}
	
	// TODO - maybe find a better name for the variable getRelativeTime
	public void addErrorFinder(ErrorFinder errorFinder) {
		// build kalman filter
		RealVector initialStateEstimate = new ArrayRealVector(new Double[] { 0.0, 0.0, 0.0 });
		RealMatrix processNoise = Utils.getDiagonalMatrix(errorFinder.getVarianceVector());
		RealMatrix measurmentNoise = Utils.get0Matrix(2);

		ProcessModel processModel = new DefaultProcessModel(OrientationConstants.KalmanFilterMatrices.A_MATRIX,
				OrientationConstants.KalmanFilterMatrices.B_MATRIX, processNoise, initialStateEstimate, null);
		MeasurementModel measurementModel = new DefaultMeasurementModel(
				OrientationConstants.KalmanFilterMatrices.H_MATRIX, measurmentNoise);

		movementFilter = new KalmanFilter(processModel, measurementModel);
	}

	public void update() {
		// getting the time difference
		double dt = timeController.get();

		/*
		 * calculating the velocity measurement by using: v = P/dt, when P is
		 * the robot's displacement vector between two points and dt is the time
		 * passed between these points
		 */
		Point measurement = odometryHandler.getDifference();
		RealVector measurementVector = new ArrayRealVector(
				new double[] { measurement.getX() / dt, measurement.getY() / dt });

		// getting the fixed control change from the acceleration unit
		RealVector controlChanges = measurementHandler.apply(accelerationsUnit.getAcceleration());

		// activating the kalman filter
		movementFilter.predict(controlChanges);
		movementFilter.correct(measurementVector);

		// getting the filter's velocity estimation
		RealVector velocity = movementFilter.getStateEstimationVector();

		// dividing the velocity vector into x,y,z values.
		double velocityX = velocity.getEntry(0);
		double velocityY = velocity.getEntry(1);
		double velocityZ = velocity.getEntry(2);

		// moving the robot's position according to d = vt.
		position.move(dt * velocityX, dt * velocityY, dt * velocityZ);
	}

	public Point3D getPosition() {
		return position;
	}

}