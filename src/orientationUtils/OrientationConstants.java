package orientationUtils;

import org.apache.commons.math3.linear.RealMatrix;

/**
 * calculates and returns the variance vector of the sensors.
 * 
 * @return a vector, containing the variance of each sensor
 * 
 * @see VarianceFinder
 */
public class OrientationConstants {
	public static class KalmanFilterMatrices {
		// A - The state transition matrix
		public static final RealMatrix A_MATRIX = Utils.getUnitMatrix(3);

		// B - the control input matrix
		public static final RealMatrix B_MATRIX = Utils.getUnitMatrix(3);

		// H - the measurement matrix
		public static final RealMatrix H_MATRIX = Utils.getUnitMatrix(3);
	}
}
