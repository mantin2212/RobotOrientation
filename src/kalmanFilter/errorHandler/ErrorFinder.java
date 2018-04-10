package kalmanFilter.errorHandler;

import org.apache.commons.math3.linear.RealVector;

import utils.VarianceFinder;

public class ErrorFinder {

	private BiasFinder biasFinder;
	private VarianceFinder varianceFinder;

	public ErrorFinder(int size) {
		biasFinder = new BiasFinder(size);
		varianceFinder = new VarianceFinder(size);
	}

	public void addMeasurement(double... measurement) {
		biasFinder.addMeasurement(measurement);
		varianceFinder.addResultVector(measurement);
	}

	public RealVector getErrorVector(RealVector expectedVector) {
		return biasFinder.getErrorVector(expectedVector);
	}

	public RealVector getVarianceVector() {
		return varianceFinder.getVarianceVector();
	}
}
