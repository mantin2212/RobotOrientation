package orientationUtils.preferences;

import java.util.function.Supplier;

public class AnglesUnit {

	private Supplier<Double> yaw;
	private Supplier<Double> pitch;
	private Supplier<Double> roll;

	public AnglesUnit(Supplier<Double> yaw, Supplier<Double> pitch, Supplier<Double> roll) {
		this.yaw = yaw;
		this.roll = roll;
		this.pitch = pitch;
	}

	public double getYaw() {
		return yaw.get();
	}

	public double getPitch() {
		return pitch.get();
	}

	public double getRoll() {
		return roll.get();
	}

}
