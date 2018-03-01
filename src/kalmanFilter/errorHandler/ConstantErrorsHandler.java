package kalmanFilter.errorHandler;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class ConstantErrorsHandler {

	private RealVector resultSum;

	private int measurementNumber = 0;

	public ConstantErrorsHandler(int size) {
		this.resultSum = new ArrayRealVector(size);
	}

	public void addMeasurement(RealVector measurement) {
		resultSum.add(measurement);
		measurementNumber++;
	}

	public RealVector getErrorVector(RealVector expected) {

		RealVector errorAvg = resultSum.mapDivide(measurementNumber).subtract(expected);

		return errorAvg;
	}
}