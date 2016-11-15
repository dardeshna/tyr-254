package com.palyrobotics.frc2016.subsystems.helpers;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.team254.lib.util.DriveSignal;
import com.team254.lib.util.Subsystem.SubsystemState;

import edu.wpi.first.wpilibj.DriverStation;

public class ProportionalDriveHelper {

	private Drive drive;
	private DriveSignal signal = new DriveSignal(0, 0);

	public ProportionalDriveHelper(Drive drive) {
		this.drive = drive;
	}

	public void pDrive(double throttle, double wheel) {
		if (DriverStation.getInstance().isAutonomous()) {
			return;
		}

		if (drive.state != SubsystemState.OPEN) {
			return;
		}

		double angularPower = wheel;
		double rightPwm = throttle;
		double leftPwm = throttle;
		leftPwm += angularPower;
		rightPwm -= angularPower;

		signal.leftMotor = leftPwm;
		signal.rightMotor = rightPwm;
		drive.setOpenLoop(new DriveSignal(leftPwm, rightPwm));
	}

}