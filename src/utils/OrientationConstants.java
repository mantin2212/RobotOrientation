package utils;

public class OrientationConstants {
	public static class KalmanFilterMatrices {
		public static final double[][] aMatrix = Utils.getUnitMatrix(2).getData();
		public static final double[][] bMatrix = Utils.getUnitMatrix(2).getData();
		public static final double[][] hMatrix = Utils.getUnitMatrix(2).getData();
	}
}
