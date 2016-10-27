package com.palyrobotics.frc2016.subsystems;

import com.palyrobotics.frc2016.subsystems.controllers.DriveController;
import com.palyrobotics.frc2016.subsystems.controllers.GyroTurnAngleController;
import com.palyrobotics.frc2016.subsystems.controllers.team254.DriveStraightController;
import com.team254.lib.util.CheesySpeedController;
import com.team254.lib.util.DriveSignal;
import com.team254.lib.util.Loop;
import com.team254.lib.util.Position;
import com.team254.lib.util.StateHolder;
import com.team254.lib.util.Subsystem;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import com.team254.lib.util.gyro.GyroThread;
import edu.wpi.first.wpilibj.Encoder;

public class Drive extends Subsystem implements Loop {

	private DoubleSolenoid m_shifter_solenoid = null;
	private CheesySpeedController m_left_motor;
	private CheesySpeedController m_right_motor;
	protected Encoder m_left_encoder;
	protected Encoder m_right_encoder;
	protected ADXRS450_Gyro m_gyro;
	private DriveController m_controller = null;
	
	// Derica is always considered high gear
	public enum DriveGear {HIGH, LOW}
	public DriveGear mGear;
	
	// Encoder DPP
	protected final double m_inches_per_tick;

	protected final double m_wheelbase_width; // Get from CAD
	protected final double m_turn_slip_factor; // Measure empirically
	private Position m_cached_pose = new Position(0, 0, 0, 0, 0, 0); // Don't allocate poses at 200Hz!

	public Drive(String name, CheesySpeedController left_drive,
			CheesySpeedController right_drive, Encoder left_encoder,
			Encoder right_encoder, ADXRS450_Gyro gyro, DoubleSolenoid shifter_solenoid) {
		super(name);
		m_wheelbase_width = 26.0;
		m_turn_slip_factor = 1.2;
		// TODO: Encoder DPP's
		m_inches_per_tick = 0.184;
		
		this.m_left_motor = left_drive;
		this.m_right_motor = right_drive;
		this.m_left_encoder = left_encoder;
		this.m_right_encoder = right_encoder;
		this.m_left_encoder.setDistancePerPulse(m_inches_per_tick);
		this.m_right_encoder.setDistancePerPulse(m_inches_per_tick);
		this.m_gyro = gyro;
		this.m_shifter_solenoid = shifter_solenoid;
	}

	public void setOpenLoop(DriveSignal signal) {
		m_controller = null;
		setDriveOutputs(signal);
	}
	
	public void setGear(DriveGear targetGear) {
		switch(targetGear) {
			case HIGH:
				//TODO Which is high and which is low?
				m_shifter_solenoid.set(Value.kForward);
				break;
			case LOW:
				m_shifter_solenoid.set(Value.kReverse);
				break;
		}
	}
	
	public boolean isHighGear() {
		return mGear == DriveGear.HIGH;
	}
	
	public void turnAngle(double heading, double maxVel) {
		m_controller = new GyroTurnAngleController(getPhysicalPose(), heading, maxVel);
	}
	
	public void reset() {
		m_left_encoder.reset();
		m_right_encoder.reset();
		m_controller = null;
	}

	@Override
	public void getState(StateHolder states) {
		//        states.put("gyro_angle", m_gyro.getAngle());
		states.put("left_encoder", m_left_encoder.getDistance());
		states.put("left_encoder_rate", m_left_encoder.getRate());
		states.put("right_encoder_rate", m_right_encoder.getRate());
		states.put("right_encoder", m_right_encoder.getDistance());

		Position setPointPose = m_controller == null ? getPhysicalPose(): m_controller.getCurrentSetpoint();
				states.put("drive_set_point_pos",
					DriveStraightController.encoderDistance(setPointPose));
				states.put("turn_set_point_pos", setPointPose.getHeading());
				states.put("left_signal", m_left_motor.get());
				states.put("right_signal", m_right_motor.get());
				states.put("on_target", (m_controller != null && m_controller.onTarget()) ? 1.0 : 0.0);
	}

	@Override
	public void onStart() {
	}
	@Override
	public void onStop() {
	}
	@Override
	public void onLoop() {
		if(!hasController()) {
			return;
		}
		setDriveOutputs(m_controller.update(getPhysicalPose()));
	}

	private void setDriveOutputs(DriveSignal signal) {
		m_left_motor.set(signal.leftMotor);
		m_right_motor.set(-signal.rightMotor);
	}

	/**
	 * @return The pose according to the current sensor state
	 */
	public Position getPhysicalPose() {
		m_cached_pose.reset(
				m_left_encoder.getDistance(),
				m_right_encoder.getDistance(),
				m_left_encoder.getRate(),
				m_right_encoder.getRate(),
				m_gyro.getAngle(),
				m_gyro.getRate());
		return m_cached_pose;
	}

	public DriveController getController() {
		return m_controller;
	}

	@Override
	public void reloadConstants() {
		// TODO Auto-generated method stub

	}

	public boolean controllerOnTarget() {
		return m_controller != null && m_controller.onTarget();
	}

	public boolean hasController() {
		return m_controller != null;
	}
}