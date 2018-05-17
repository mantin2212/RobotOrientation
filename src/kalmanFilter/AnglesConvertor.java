// TODO - change package name
package kalmanFilter;

import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import orientationUtils.Orientation3D;
import orientationUtils.Utils;
import orientationUtils.preferences.AnglesUnit;

public class AnglesConvertor implements Function<RealVector, RealVector> {

	private AnglesUnit anglesUnit;

	private RealVector biases;

	public AnglesConvertor(AnglesUnit anglesUnit, RealVector biases) {

		this.anglesUnit = anglesUnit;

		if (biases.getDimension() == 3)
			this.biases = biases;
		else
			throw new IllegalArgumentException("the biases' vector should have 3 elements");
	}

	public AnglesConvertor(AnglesUnit anglesUnit) {
		this(anglesUnit, new ArrayRealVector(new double[] { 0, 0, 0 }));
	}

	private RealVector toNavigationFrame(RealVector vector) {
		RealMatrix transformation = anglesUnit.getCurrentState().getTransformationMatrix();
		return transformation.preMultiply(vector);
	}

	@Override
	public RealVector apply(RealVector measurement) {
		return toNavigationFrame(measurement.subtract(biases));
	}
}
