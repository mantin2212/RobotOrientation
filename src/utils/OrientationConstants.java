package utils;

import org.apache.commons.math3.linear.RealMatrix;

public class OrientationConstants {
	public static class KalmanFilterMatrices {
		public static final RealMatrix A_MATRIX = Utils.getUnitMatrix(3);
		public static final RealMatrix B_MATRIX = Utils.getUnitMatrix(3);
		public static final RealMatrix H_MATRIX = Utils.getUnitMatrix(3);
	}
}
