package utils;

import org.apache.commons.math3.linear.RealMatrix;

public class OrientationConstants {
	public static class KalmanFilterMatrices {
		public static final RealMatrix aMatrix = Utils.getUnitMatrix(2);
		public static final RealMatrix bMatrix = Utils.getUnitMatrix(2);
		public static final RealMatrix hMatrix = Utils.getUnitMatrix(2);
	}
}
