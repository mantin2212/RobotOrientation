package utils;

import org.apache.commons.math3.linear.RealMatrix;

public class OrientationConstants {
	/**
	 * This class contains the Matrices in use in the kalman filter:</br>
	 * <ul>
	 * <li>A - the state transition matrix</li>
	 * <li>B - the control input matrix</li>
	 * <li>H - the measurement matrix</li>
	 * <li>Q - the process noise covariance matrix</li>
	 * <li>R - the measurement noise covariance matrix</li>
	 * </ul>
	 */
	public static class KalmanFilterMatrices {

		// A - The state transition matrix
		public static final RealMatrix A_MATRIX = Utils.getUnitMatrix(3);

		// B - the control input matrix
		public static final RealMatrix B_MATRIX = Utils.getUnitMatrix(3);

		// H - the measurement matrix
		public static final RealMatrix H_MATRIX = Utils.getUnitMatrix(3);
	}
}
