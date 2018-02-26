package kalmanFilter.errorHandler;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class ErrorFinder {

	private ConstantsErrorHandler constantsErrorFixer;

	private CovarianceMatrixHandler handler;

	public ErrorFinder(int size, RealVector expectedVector) {

		constantsErrorFixer = new ConstantsErrorHandler(size, expectedVector);

		handler = new CovarianceMatrixHandler(size);
	}

	public void addMeasurement(RealVector measurement) {
		constantsErrorFixer.addMeasurement(measurement);
		handler.addMeasurement(measurement);
	}

	public RealVector getErrorVector() {
		return constantsErrorFixer.getErrorVector();
	}

	public RealMatrix getCovarianceMatrix() {
		return handler.getCovarianceMatrix(getErrorVector());
	}
}
