package kalmanFilter.errorHandler;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class ConstantsErrorHandler {

	private RealVector expected;
	private RealVector resultSum;

	private int measurementNumber = 0;

	public ConstantsErrorHandler(int size, RealVector expectedVector) {
		this.expected = expectedVector;

		this.resultSum = new ArrayRealVector(size);

		resultSum.subtract(resultSum);
	}

	public void addMeasurement(RealVector measurement) {
		resultSum.add(measurement);
		measurementNumber++;
	}

	public RealVector getErrorVector() {
		RealVector errorSum = resultSum.subtract(expected.mapMultiplyToSelf(measurementNumber));

		RealVector errorAvg = errorSum.mapDivide(measurementNumber);

		return errorAvg;
	}
}