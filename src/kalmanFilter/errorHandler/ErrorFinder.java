package kalmanFilter.errorHandler;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class ErrorFinder {

	private ConstantErrorsFinder constantErrorsHandler;
	private CovarianceMatrixHandler covarianceMatrixFinder;

	public ErrorFinder(int size) {
		constantErrorsHandler = new ConstantErrorsFinder(size);
		covarianceMatrixFinder = new CovarianceMatrixHandler(size);
	}

	public void addMeasurement(RealVector measurement) {
		constantErrorsHandler.addMeasurement(measurement);
		covarianceMatrixFinder.addMeasurement(measurement);
	}

	public RealVector getErrorVector(RealVector expectedVector) {
		return constantErrorsHandler.getErrorVector(expectedVector);
	}

	public RealMatrix getCovarianceMatrix(RealVector expectedVector) {
		return covarianceMatrixFinder.getCovarianceMatrix(getErrorVector(expectedVector));
	}
}
