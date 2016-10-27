package com.palyrobotics.frc2016;

import com.palyrobotics.frc2016.behavior.RoutineManager;
import com.palyrobotics.frc2016.routines.TurnAngleRoutine;
import com.palyrobotics.frc2016.subsystems.Drive.DriveGear;
import com.palyrobotics.frc2016.util.CheesyDriveHelper;
import com.palyrobotics.frc2016.util.XboxController;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

	Joystick leftStick = HardwareAdaptor.kLeftStick;
	Joystick rightStick = HardwareAdaptor.kRightStick;
	Joystick operatorStick = HardwareAdaptor.kOperatorStick;
	CheesyDriveHelper cdh = new CheesyDriveHelper(HardwareAdaptor.kDrive);
	RoutineManager routineManager;
	
	
	public OperatorInterface(RoutineManager routineManager) {
		this.routineManager = routineManager;
	}
	
	public void update() {
		
		HardwareAdaptor.kTyrShooter.update(((XboxController) operatorStick).getLeftY());
		HardwareAdaptor.kBreacher.update(((XboxController) operatorStick).getRightY());
		
		cdh.cheesyDrive(-leftStick.getY(), rightStick.getX(), rightStick.getRawButton(1), HardwareAdaptor.kDrive.isHighGear());
		
		// Operator Stick - Intake Control
		if (((XboxController) operatorStick).getRightTriggerPressed()) {
			HardwareAdaptor.kIntake.setSpeed(1.0);
		} else if (((XboxController) operatorStick).getLeftTriggerPressed()) {
			HardwareAdaptor.kIntake.setSpeed(-1.0);
		} else {
			HardwareAdaptor.kIntake.setSpeed(0.0);
		}
		
		// Operator Stick - Shooter Control
		if (((XboxController) operatorStick).getButtonX()) {
			HardwareAdaptor.kTyrShooter.extend();
		} else if (((XboxController) operatorStick).getButtonB()) {
			HardwareAdaptor.kTyrShooter.retract();
		}
		
		// Operator Stick - Latch Control
		if (((XboxController) operatorStick).getButtonA()) {
			HardwareAdaptor.kTyrShooter.lock();
		} else if (((XboxController) operatorStick).getButtonY()) {
			HardwareAdaptor.kTyrShooter.unlock();
		}
		// Operator Stick - Grabber Control
		if (((XboxController) operatorStick).getLeftBumper()) {
			HardwareAdaptor.kTyrShooter.release();
		} else {
			HardwareAdaptor.kTyrShooter.grab();
		}
		
		// Right Stick - Activate routine
		if(rightStick.getRawButton(2)) {
			routineManager.submitRoutine(new TurnAngleRoutine(90, .5));
		}
		
		if(rightStick.getRawButton(4)) {
			HardwareAdaptor.kDrive.setGear(DriveGear.LOW);
		} else if(rightStick.getRawButton(6)) {
			HardwareAdaptor.kDrive.setGear(DriveGear.HIGH);
		}
		
	}
}