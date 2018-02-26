package kalmanFilter;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class KalmanFilter {

	private RealVector state;// x

	private RealVector controlVector;// u

	private RealMatrix aMatrix;// A
	private RealMatrix bMatrix;// B
	private RealMatrix hMatrix;// H

	private RealMatrix measurementCovariance;// R
	private RealMatrix processErrorCovariance;// Q

	private RealMatrix kalmanGain;// K
	private RealMatrix pMatrix;// P

	public KalmanFilter(RealMatrix a, RealMatrix b, RealMatrix q, RealMatrix r, RealMatrix h, RealVector initialState) {
		this.aMatrix = a;
		this.bMatrix = b;

		this.measurementCovariance = q;
		this.processErrorCovariance = r;

		this.hMatrix = h;

		this.state = initialState;
	}

	public void update(RealVector measurement) {

		RealVector statePrediction = aMatrix.preMultiply(state).add(bMatrix.preMultiply(controlVector));

		RealMatrix pPrediction = aMatrix.multiply(pMatrix).multiply(aMatrix.transpose()).add(processErrorCovariance);

	}

}
