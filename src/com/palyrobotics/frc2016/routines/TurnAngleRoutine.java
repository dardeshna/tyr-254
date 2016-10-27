package com.palyrobotics.frc2016.routines;

import com.palyrobotics.frc2016.HardwareAdaptor;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.team254.lib.util.DriveSignal;

public class TurnAngleRoutine extends Routine {

	private Drive drive = HardwareAdaptor.kDrive;
	
	private double angle;
	private double maxVel;
	
	private States m_state = States.START;
	
	private enum States {
		START, TURNING, DONE
	}
	
	public TurnAngleRoutine(double angle, double maxVel) {
		requires(drive);
		this.angle = angle;
		this.maxVel = maxVel;
	}
	
	@Override
	public void start() {
		drive.reset();
		m_state = States.START;
	}

	@Override
	public void update() {
		
		switch(m_state) {
		case START:
			System.out.println("Set setpoint: "+angle);
			drive.turnAngle(angle, maxVel);
			
			m_state = States.TURNING;
			break;
			
		case TURNING:
			if(drive.controllerOnTarget()) {
				m_state = States.DONE;
			}
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void cancel() {
		m_state = States.DONE;
	}
	
	@Override
	public void cleanup() {
		drive.reset();
		drive.setOpenLoop(DriveSignal.NEUTRAL);
	}

	@Override
	public boolean isFinished() {
		return m_state == States.DONE;
	}

	@Override
	public String getName() {
		return "EncoderTurnAngleRoutine";
	}
}