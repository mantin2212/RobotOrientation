package kalmanFilter;

import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.linear.RealVector;

import routes.ArgPoint;

public class RobotOrientation {

	private KalmanFilter movementFilter;
	private ArgPoint position;

	private TimeController controller;

	public RobotOrientation(ArgPoint initialPosition, TimeController controller) {
		this.position = initialPosition;

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
