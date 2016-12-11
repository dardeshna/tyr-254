package com.palyrobotics.frc2016;

import com.palyrobotics.lib.util.Requirable;

public class Requirables {

	public static Requirable rShooterPneumatics = new Requirable();
	public static Requirable rShooterMotor = new Requirable();
		
	public static Requirable rDrive = new Requirable();
	public static Requirable rIntake = new Requirable();
	public static Requirable rBreacher = new Requirable();
	
	public static Requirable rShooter = new Requirable(rShooterPneumatics, rShooterMotor);
	
}