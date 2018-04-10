package utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class VarianceFinder {

	private RealVector variableResultSum;
	private RealVector variableResultSquareSum;

	private int resultNumber = 0;

	public VarianceFinder(int variableNumber) {
		variableResultSum = new ArrayRealVector(variableNumber);
		variableResultSquareSum = new ArrayRealVector(variableNumber);
	}

	public void addResultVector(double... newResults) {

		RealVector resultVector = new ArrayRealVector(newResults);

		variableResultSum.add(resultVector);
		variableResultSquareSum.add(Utils.getSquared(resultVector));

		resultNumber++;
	}

	public RealVector getVarianceVector() {

		RealVector resultSquareMeanVector = variableResultSquareSum.mapDivide(resultNumber);
		RealVector resultMeanVector = variableResultSum.mapDivide(resultNumber);

		return resultSquareMeanVector.subtract(Utils.getSquared(resultMeanVector));
	}
}
