package com.palyrobotics.frc2016;

import edu.wpi.first.wpilibj.Joystick;

import com.palyrobotics.frc2016.behavior.Commands;
import com.palyrobotics.lib.util.Latch;
import com.palyrobotics.lib.util.XboxController;

public class OperatorInterface {
	private Commands m_commands = new Commands();

	Joystick leftStick = HardwareAdaptor.kLeftStick;
	Joystick rightStick = HardwareAdaptor.kRightStick;
	XboxController operatorStick = HardwareAdaptor.kOperatorStick;

	Latch driveForwardLatch = new Latch();

	public void reset() {
		m_commands = new Commands();
	}

	public Commands getCommands() {
		// Operator Stick - Intake Control
		if (operatorStick.getRawButton(1)) {
			m_commands.intake_request = Commands.IntakeRequest.INTAKE;
		} else if (operatorStick.getRawButton(2)) {
			m_commands.intake_request = Commands.IntakeRequest.EXHAUST;
		} else {
			m_commands.intake_request = Commands.IntakeRequest.NONE;
		}
		// Operator Stick - Shooter Control
		if (operatorStick.getRawButton(3)) {
			m_commands.shooter_request = Commands.ShooterRequest.EXTEND;
		} else if (operatorStick.getRawButton(2)) {
			m_commands.shooter_request = Commands.ShooterRequest.RETRACT;
		} else {
			m_commands.shooter_request = Commands.ShooterRequest.NONE;
		}
		// Operator Stick - Latch Control
		if (operatorStick.getRawButton(5)) {
			m_commands.latch_request = Commands.LatchRequest.LOCK;
		} else if (operatorStick.getRawButton(3)) {
			m_commands.latch_request = Commands.LatchRequest.UNLOCK;
		} else {
			m_commands.latch_request = Commands.LatchRequest.NONE;
		}
		// Operator Stick - Grabber Control
		if (operatorStick.getRawButton(4)) {
			m_commands.grabber_request = Commands.GrabberRequest.RELEASE;
		} else {
			m_commands.grabber_request = Commands.GrabberRequest.GRAB;
		}

		// Operator Stick - Activate routine
		if (driveForwardLatch.update(leftStick.getRawButton(6))) {
			m_commands.timer_drive_request = Commands.TimerDriveRequest.ACTIVATE;
		} else if(driveForwardLatch.update(leftStick.getRawButton(5))) {
			m_commands.encoder_drive_request = Commands.EncoderDriveRequest.ACTIVATE;
		} else {
			m_commands.timer_drive_request = Commands.TimerDriveRequest.NONE;
			m_commands.encoder_drive_request = Commands.EncoderDriveRequest.NONE;
		}

		// Left Stick trigger cancels current routine
		m_commands.cancel_current_routine = leftStick.getTrigger(); // Cancels routine?

		return m_commands;
	}

	public void printXbox() {
		System.out.println("Left X: "+operatorStick.getLeftX());
		System.out.println("Left Y: "+operatorStick.getLeftY());
		System.out.println("Right X: "+operatorStick.getRightX());
		System.out.println("Right X: "+operatorStick.getRightY());
		if(operatorStick.getButtonA()) {
			System.out.println("Button A pressed");
		}
		if(operatorStick.getButtonB()) {
			System.out.println("Button B pressed");
		}
		if(operatorStick.getButtonX()) {
			System.out.println("Button X pressed");
		}
		if(operatorStick.getButtonY()) {
			System.out.println("Button Y pressed");
		}
		if(operatorStick.getLeftStickPressed()) {
			System.out.println("Left Stick pressed");
		}
		if(operatorStick.getRightStickPressed()) {
			System.out.println("Right Stick pressed");
		}

	}
}