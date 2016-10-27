package com.palyrobotics.frc2016.routines;

import com.palyrobotics.frc2016.HardwareAdaptor;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.team254.lib.util.DriveSignal;

import edu.wpi.first.wpilibj.Timer;

public class DriveTimeRoutine extends Routine {

	private enum DriveTimeRoutineStates {
		START, DRIVING, DONE
	}

	DriveTimeRoutineStates m_state = DriveTimeRoutineStates.START;
	Timer timer = new Timer();
	// Default values for time and velocity setpoints
	private double time;
	private double velocity;
	
	private boolean m_is_new_state = true;

	private Drive drive = HardwareAdaptor.kDrive;
		
	/**
	 * Constructs with a specified time setpoint and velocity
	 * @param time How long to drive (seconds)
	 * @param velocity What velocity to drive at (0 to 1)
	 */
	public DriveTimeRoutine(double time, double velocity) {
		requires(drive);
		setTimeSetpoint(time);
		setVelocity(velocity);
	}
	
	/**
	 * Sets the time setpoint that will be used
	 * @param time how long to drive forward in seconds
	 */
	public void setTimeSetpoint(double time) {
		this.time = time;
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
		DriveTimeRoutineStates new_state = m_state;
		switch (m_state) {
		case START:
			// Only set the setpoint the first time the state is START
			if(m_is_new_state) {
				timer.reset();
				timer.start();

			}

			new_state = DriveTimeRoutineStates.DRIVING;
			break;
		case DRIVING:
			drive.setOpenLoop(new DriveSignal(velocity, velocity));

			if(timer.get() >= time) {

				new_state = DriveTimeRoutineStates.DONE;
			}
			break;
		default:
			break;
		}

		m_is_new_state = false;
		if(new_state != m_state) {
			m_state = new_state;
			//m_timer.reset();
			m_is_new_state = true;
		}
		
	}

	
	@Override
	public void cancel() {
		m_state = DriveTimeRoutineStates.DONE;
		cleanup();
	}
	
	@Override
	public void cleanup() {
		timer.stop();
		timer.reset();
		drive.reset();
		drive.setOpenLoop(DriveSignal.NEUTRAL);	
	}
	
	@Override
	public void start() {
		drive.reset();
		timer.reset();
		m_state = DriveTimeRoutineStates.START;
	}
	
	@Override
	public boolean isFinished() {
		// allow
		return m_state == DriveTimeRoutineStates.DONE && m_is_new_state==false;
	}

	@Override
	public String getName() {
		return "Timer Drive Forward";
	}

}
