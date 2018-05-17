package orientationUtils.preferences;

import java.util.function.Supplier;

public class PrincipalAxesUnit {

	private Supplier<Double> yaw;
	private Supplier<Double> pitch;
	private Supplier<Double> roll;

	public PrincipalAxesUnit(Supplier<Double> yaw, Supplier<Double> pitch, Supplier<Double> roll) {
		this.yaw = yaw;
		this.roll = roll;
		this.pitch = pitch;
	}

	public Supplier<Double> getYaw() {
		return yaw;
	}

	public Supplier<Double> getPitch() {
		return pitch;
	}

	public Supplier<Double> getRoll() {
		return roll;
	}

}
