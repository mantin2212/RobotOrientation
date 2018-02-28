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

	public void update(RealVector measurement, RealVector controlChanges) {
	}

}
