package com.palyrobotics.frc2016.subsystems.controllers;

import com.palyrobotics.frc2016.Constants;
import com.palyrobotics.frc2016.HardwareAdaptor;
import com.palyrobotics.frc2016.Robot;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.team254.lib.util.DriveSignal;
import com.team254.lib.util.Position;

/**
 * Turns drivetrain using the gyroscope
 * @author Nihar
 *
 */
public class GyroTurnAngleController implements DriveController {
	
	private double maxVel;
	
	private double P;
	private double I;
	private double D;
	
	private Drive kDrive = HardwareAdaptor.kDrive;
	
	private Position setpoint;
	
	public GyroTurnAngleController(Position priorSetpoint, double heading, double maxVel) {
		this.maxVel = maxVel;
		setpoint = priorSetpoint.copy();
		setpoint.m_heading+=heading;
	}
	
	@Override
	public DriveSignal update(Position pose) {
		P = setpoint.getHeading()-pose.getHeading();
		I = I + P * Constants.kLooperDt;
		
		D = -pose.getHeadingVelocity();
		
		double leftSpeed = Math.max(-maxVel, 
				Math.min(maxVel, Constants.kGyroTurnKp*P + Constants.kGyroTurnKi*I + Constants.kGyroTurnKd*D));
		double rightSpeed = -leftSpeed;
//		System.out.println("PID calc: " + Constants.kGyroTurnKp*P + Constants.kGyroTurnKi*I + Constants.kGyroTurnKd*D);
//		System.out.println("Left speed "+leftSpeed);
		return new DriveSignal(leftSpeed, rightSpeed);
	}

	@Override
	public Position getCurrentSetpoint() {
		return setpoint;
	}

	@Override
	public boolean onTarget() {
		System.out.println("Gyro Turn angle error: " + String.valueOf(Math.abs(setpoint.getHeading()-kDrive.getPhysicalPose().getHeading())).substring(0, 4));
		if(Math.abs(setpoint.getHeading()-kDrive.getPhysicalPose().getHeading()) < Constants.kAcceptableGyroTurnError &&
				Math.abs(kDrive.getPhysicalPose().getHeadingVelocity()) < Constants.kAcceptableGyroTurnStopSpeed) {
			System.out.println("Gyro turn on target");
			return true;
		} else return false;
	}

}
