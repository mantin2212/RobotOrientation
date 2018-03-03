package kalmanFilter;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import routes.ArgPoint;
import utils.OrientationConstants;

public class RobotOrientation {

	private KalmanFilter movementFilter;
	private ArgPoint position;

	private TimeController controller;

	public RobotOrientation(ArgPoint initialPosition, TimeController controller, RealMatrix processNoise,
			RealMatrix measurementNoise) {

		this.position = initialPosition;

		RealVector initial = new ArrayRealVector(new Double[] { 0.0, 0.0 });

		ProcessModel processModel = new DefaultProcessModel(OrientationConstants.KalmanFilterMatrices.aMatrix,
				OrientationConstants.KalmanFilterMatrices.bMatrix, processNoise, initial, null);

		MeasurementModel measurementModel = new DefaultMeasurementModel(
				OrientationConstants.KalmanFilterMatrices.hMatrix, measurementNoise);

		movementFilter = new KalmanFilter(processModel, measurementModel);

		this.controller = controller;
	}

	public ArgPoint getPosition() {
		return position;
	}

	public void update(RealVector measurement, RealVector controlChanges, double yawAngle) {

		double dt = controller.getDT();

		movementFilter.predict(controlChanges);
		movementFilter.correct(measurement);

		RealVector state = movementFilter.getStateEstimationVector();

		double velocityXb = state.getEntry(0);
		double velocityYb = state.getEntry(1);

		double velocityXn = velocityXb * Math.cos(yawAngle) + velocityYb * Math.sin(yawAngle);
		double velocityYn = velocityXb * Math.sin(yawAngle) - velocityYb * Math.cos(yawAngle);

		position.move(velocityXn * dt, velocityYn * dt);
	}
}
