package com.palyrobotics.frc2016.routines;

import com.palyrobotics.frc2016.HardwareAdaptor;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.team254.lib.util.DriveSignal;

import edu.wpi.first.wpilibj.Timer;

/**
 * Drives forward a specified distance
 * Uses right encoder to determine if distance is reached
 * Times out after specified seconds, default m_default_timeout
 * @author Nihar
 */
public class EncoderDriveRoutine extends Routine {
	/*
	 * START = Set new drive setpoint
	 * DRIVING = Waiting to reach drive setpoint
	 * DONE = reached target or not operating
	 */
	private enum EncoderDriveRoutineStates {
		START, DRIVING, DONE
	}

	EncoderDriveRoutineStates m_state = EncoderDriveRoutineStates.START;
	private double distance;
	private double velocity;
	private static final double m_default_velocity_setpoint = 0.5;
	
	// Timeout after x seconds
	private double m_timeout;
	private static final double m_default_timeout = 5;
	Timer m_timer = new Timer();

	private Drive drive = HardwareAdaptor.kDrive;
	
	/**
	 * Constructs with target distance
	 * Uses default timeout and default velocity setpoint
	 * @param distance Target distance to travel
	 */
	public EncoderDriveRoutine(double distance) {
		this(distance, m_default_timeout, m_default_velocity_setpoint);
	}
	
	/**
	 * Constructs with specified timeout
	 * @param distance Target distance to travel
	 * @param timeout Time (seconds) before timeout
	 */
	public EncoderDriveRoutine(double distance, double timeout) {
		this(distance, timeout, m_default_velocity_setpoint);

	}
	
	/**
	 * 
	 * @param distance Target distance to travel
	 * @param timeout Time (seconds) before timeout
	 * @param velocity Target velocity
	 */
	public EncoderDriveRoutine(double distance, double timeout, double velocity) {
		requires(drive);
		this.distance = distance;
		this.m_timeout = timeout;
		setVelocity(velocity);
	}
	
	/**
	 * Sets the velocity setpoint
	 * @param velocity target velocity to drive at (0 to 1)
	 * @return true if valid setspeed
	 */
	public boolean setVelocity(double velocity) {
		if(velocity > 0) {
			this.velocity = velocity;
			return true;
		}
		return false;
	}

	//Routines just change the states of the robotsetpoints, which the behavior manager then moves the physical subsystems based on.
	@Override
	public void update() {
		EncoderDriveRoutineStates new_state = m_state;
		switch (m_state) {
		case START:
			m_timer.reset();
			m_timer.start();
			new_state = EncoderDriveRoutineStates.DRIVING;
			break;
		case DRIVING:
			drive.setOpenLoop(new DriveSignal(velocity, velocity));
			if(drive.getPhysicalPose().getRightDistance() > distance) {
				new_state = EncoderDriveRoutineStates.DONE;
			}
			if(m_timer.get() > m_timeout) {
				new_state = EncoderDriveRoutineStates.DONE;
			}
			break;
		default:
			break;
		}
		
		if(new_state != m_state) {
			m_state = new_state;
			m_timer.reset();
		}
		
	}

	@Override
	public void cancel() {
		m_state = EncoderDriveRoutineStates.DONE;
		cleanup();
	}
	
	@Override
	public void cleanup() {
		m_timer.stop();
		m_timer.reset();
		drive.reset();
		drive.setOpenLoop(DriveSignal.NEUTRAL);	
	}

	@Override
	public void start() {
		drive.reset();
		m_timer.reset();
	}

	@Override
	public boolean isFinished() {
		return m_state == EncoderDriveRoutineStates.DONE;
	}

	@Override
	public String getName() {
		return "Encoder Drive Forward";
	}

}
