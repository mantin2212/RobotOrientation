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
import orientationUtils.RelativeDataHandler;
import orientationUtils.Utils;

public class RobotOrientation {

	private KalmanFilter movementFilter;
	private Point3D position;

	private RelativeDataHandler timeController;

	public RobotOrientation(Point3D initialPosition, Supplier<Double> getRelativeTime, RealMatrix processNoise,
			RealMatrix measurementNoise) {

		this.position = initialPosition;

		RealVector initial = new ArrayRealVector(new Double[] { 0.0, 0.0, 0.0 });

		ProcessModel processModel = new DefaultProcessModel(OrientationConstants.KalmanFilterMatrices.A_MATRIX,
				OrientationConstants.KalmanFilterMatrices.B_MATRIX, processNoise, initial, null);

		MeasurementModel measurementModel = new DefaultMeasurementModel(
				OrientationConstants.KalmanFilterMatrices.H_MATRIX, measurementNoise);

		movementFilter = new KalmanFilter(processModel, measurementModel);

		this.timeController = new RelativeDataHandler(getRelativeTime);
	}

	public Point3D getPosition() {
		return position;
	}

	public void update(RealVector measurement, RealVector controlChanges, double yawAngle, double rollAngle,
			double pitchAngle) {

		double dt = timeController.getLastDifference();

		movementFilter.predict(controlChanges);
		movementFilter.correct(measurement);

		RealVector state = movementFilter.getStateEstimationVector();

		RealMatrix transformationMatrix = Utils.getTransformationMatrix(yawAngle, rollAngle, pitchAngle);

		state = transformationMatrix.preMultiply(state);

		double velocityX = state.getEntry(0);
		double velocityY = state.getEntry(1);
		double velocityZ = state.getEntry(2);

		position.move(dt * velocityX, dt * velocityY, dt * velocityZ);
	}
}