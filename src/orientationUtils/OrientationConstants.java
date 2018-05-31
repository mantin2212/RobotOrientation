package orientationUtils;

import org.apache.commons.math3.linear.RealMatrix;

/**
 * This class contains the Matrices in use in the kalman filter:</br>
 * <ul>
 * <li>A - the state transition matrix</li>
 * <li>B - the control input matrix</li>
 * <li>H - the measurement matrix</li>
 * </ul>
 */
public class OrientationConstants {
	public static class KalmanFilterMatrices {
		// A - The state transition matrix
		public static final RealMatrix A_MATRIX = Utils.getUnitMatrix(2);

		// B - the control input matrix
		public static final RealMatrix B_MATRIX = Utils.getUnitMatrix(2);

		// H - the measurement matrix
		public static final RealMatrix H_MATRIX = Utils.getUnitMatrix(2);
	}
}
