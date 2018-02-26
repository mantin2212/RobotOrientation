package kalmanFilter.errorHandler;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class CovarianceMatrixHandler {

	private RealMatrix multMeanMatrix;

	private int measurementNumber = 0;

	public CovarianceMatrixHandler(int size) {
		multMeanMatrix = new Array2DRowRealMatrix(size, size);
	}

	public void addMeasurement(RealVector measurement) {

		double difference = 0;

		for (int i = 0; i < multMeanMatrix.getRowDimension(); i++) {
			for (int j = 0; j < multMeanMatrix.getColumnDimension(); j++) {

				difference = measurement.getEntry(i) * measurement.getEntry(j);

				multMeanMatrix.addToEntry(i, j, difference);
			}
		}
		measurementNumber++;
	}

	public RealMatrix getCovarianceMatrix(RealVector means) {

		RealMatrix result = multMeanMatrix.scalarMultiply(1.0 / measurementNumber);

		for (int i = 0; i < result.getRowDimension(); i++)
			for (int j = 0; j < result.getColumnDimension(); j++)
				result.addToEntry(i, j, -means.getEntry(i) * means.getEntry(j));

		return result;
	}
}
