// TODO - change package name
package main;

import java.util.function.Function;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import orientationUtils.Orientation3D;
import orientationUtils.preferences.AnglesUnit;

/**
 * this class handles the measurements of sensors on the robot, and converts
 * them into usable values. this includes two main tasks:<br>
 * 1. neutralizing the sensors' biases<br>
 * 2. converting the measurements from the body frame to the navigation
 * frame<br>
 * <br>
 * in order to achieve the second task, the class follows the robot's
 * orientation, and uses it to build a transformation matrix from the body frame
 * to the navigation frame.<br>
 * calling the {@link #apply(RealVector)} method of this class with a body-frame
 * measurement, it should return the navigation-frame real measurement,
 * neutralizing the biases of the sensors.
 * 
 * see page 2908 part 2.1.1 in the article for the use of the transformation
 * matrix. <br>
 * 
 * @see Orientation3D
 * @see Orientation3D#getTransformationMatrix()
 * 
 * @author noam mantin
 *
 */
public class MeasurementFixer implements Function<RealVector, RealVector> {

	private AnglesUnit anglesUnit;

	private RealVector biases;

	/**
	 * creates a new {@link MeasurementFixer} object, with a given
	 * {@link AnglesUnit} and the biases vector of the measuring sensors
	 * 
	 * @param anglesUnit
	 *            the angles unit used to follow the robot's angles
	 * 
	 * @param biases
	 *            the biases vector of the measuring sensors (usually
	 *            accelerometers)
	 * 
	 * @throws IllegalArgumentException
	 *             if the biases vector doesn't have the correct size (3), an
	 *             exception is thrown.
	 */
	public MeasurementFixer(AnglesUnit anglesUnit, RealVector biases) {
		this.anglesUnit = anglesUnit;

		if (biases.getDimension() == 2)
			this.biases = biases;
		else
			throw new IllegalArgumentException("the biases' vector should have 3 elements");
	}

	/**
	 * creates a new {@link MeasurementFixer} object, with a given
	 * {@link AnglesUnit} and a biases vector set to 0.
	 * 
	 * @param anglesUnit
	 *            the angles unit used to follow the robot's angles
	 * 
	 * @throws IllegalArgumentException
	 *             if the biases vector doesn't have the correct size (2), an
	 *             exception is thrown.
	 */
	public MeasurementFixer(AnglesUnit anglesUnit) {
		this(anglesUnit, new ArrayRealVector(new double[] { 0, 0 }));
	}

	@Override
	public RealVector apply(RealVector accMeasurementsVector) {
		/*
		 * neutralizing the sensor biases and rotating the measurement according
		 * to the robot's orientation
		 */
		return anglesUnit.getCurrentState().toNavigationFrame(accMeasurementsVector.subtract(biases));
	}
}
