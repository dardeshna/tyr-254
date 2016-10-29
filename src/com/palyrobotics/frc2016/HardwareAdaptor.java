package com.palyrobotics.frc2016;

import com.palyrobotics.frc2016.subsystems.Breacher;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.palyrobotics.frc2016.subsystems.Intake;
import com.palyrobotics.frc2016.subsystems.Shooter;
import com.palyrobotics.frc2016.util.XboxController;
import com.team254.lib.util.CheesySpeedController;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class HardwareAdaptor {
	/* 
	 * DRIVETRAIN
	 */
	public static Drive kDrive = null;

	static CheesySpeedController kLeftDriveMotor = null;
	static CheesySpeedController kRightDriveMotor = null;
	static Encoder kLeftDriveEncoder = null;
	static Encoder kRightDriveEncoder = null;
	static ADXRS450_Gyro kGyro;
	static DoubleSolenoid kShifterSolenoid = null;

	// Instantiate drive motors
	static {
		
		kLeftDriveMotor = new CheesySpeedController(
				new SpeedController[]{new CANTalon(Constants.kTyrLeftDriveFrontMotorDeviceID), 
						new CANTalon(Constants.kTyrLeftDriveBackMotorDeviceID)},
				new int[]{Constants.kTyrLeftDriveFrontMotorPDP, Constants.kTyrLeftDriveBackMotorPDP});
		kRightDriveMotor = new CheesySpeedController(
				new SpeedController[]{new CANTalon(Constants.kTyrRightDriveFrontMotorDeviceID), 
						new CANTalon(Constants.kTyrRightDriveBackMotorDeviceID)}, 
				new int[]{Constants.kTyrRightDriveBackMotorPDP, Constants.kTyrRightDriveBackMotorPDP});
		kLeftDriveEncoder = new Encoder(
				Constants.kTyrLeftDriveEncoderDIOA, Constants.kTyrLeftDriveEncoderDIOB);
		kRightDriveEncoder = new Encoder(
				Constants.kTyrRightDriveEncoderDIOA, Constants.kTyrRightDriveEncoderDIOB);
		kGyro = new ADXRS450_Gyro();
		kShifterSolenoid = new DoubleSolenoid(
				Constants.kTyrDriveSolenoidExtend, Constants.kTyrDriveSolenoidRetract);
		
		kDrive = new Drive("drive", kLeftDriveMotor, kRightDriveMotor, kLeftDriveEncoder, kRightDriveEncoder, kGyro, kShifterSolenoid);
	}

	/*
	 * INTAKE
	 */
	public static Intake kIntake = null;
	static {
		
		CheesySpeedController kLeftIntakeMotor = new CheesySpeedController(
				new VictorSP(Constants.kTyrLeftIntakeMotorDeviceID),
				Constants.kTyrLeftIntakeMotorPDP);
		CheesySpeedController kRightIntakeMotor = new CheesySpeedController(
				new VictorSP(Constants.kTyrRightIntakeMotorDeviceID),
				Constants.kTyrRightIntakeMotorPDP);
		// null for lack of a potentiometer
		kIntake = new Intake("intake", kLeftIntakeMotor, kRightIntakeMotor, null);
		System.out.println("Intake initialized");
		
	}
	
	/*
	 * SHOOTER/CATAPULT
	 * TyrShooter comes with Grabber
	 */

	// Pneumatic solenoids, only instantiate if Tyr
	static DoubleSolenoid kShooterSolenoid = null;
	static DoubleSolenoid kLatchSolenoid = null;
	static DoubleSolenoid kGrabberSolenoid = null;
	static CheesySpeedController kShooterMotor = null;
	
	public static Shooter kShooter = null;

	static {
		
		kShooterSolenoid = new DoubleSolenoid(
				Constants.kShooterSolenoidPortExtend, Constants.kShooterSolenoidPortRetract);
		kLatchSolenoid = new DoubleSolenoid(
				Constants.kLatchSolenoidPortExtend, Constants.kLatchSolenoidPortRetract);
		kGrabberSolenoid = new DoubleSolenoid(
				Constants.kGrabberSolenoidPortExtend, Constants.kGrabberSolenoidPortRetract);
		kShooterMotor = new CheesySpeedController(new CANTalon(Constants.kTyrShooterMotorDeviceID), 
				Constants.kTyrShooterMotorPDP);
		kShooter = new Shooter("shooter", kShooterMotor, kShooterSolenoid, kLatchSolenoid, kGrabberSolenoid);
		
	}

	/*
	 * BREACHER
	 */
	public static Breacher kBreacher = null;
	public static CheesySpeedController kBreacherMotor = null;
	
	static {
		
		kBreacherMotor = new CheesySpeedController(new CANTalon(Constants.kBreacherMotorDeviceID), Constants.kBreacherMotorPDP);
		kBreacher = new Breacher("breacher", kBreacherMotor);
	}
	
	public static PowerDistributionPanel kPDP = new PowerDistributionPanel();

	// Compressor
	//    public static Relay kCompressorRelay = new Relay(Constants.kCompressorRelayPort);
	//    public static DigitalInput kCompressorSwitch = new DigitalInput(Constants.kPressureSwitchDIO);
	//    public static CheesyCompressor kCompressor = new CheesyCompressor(kCompressorRelay, kCompressorSwitch);
	
	// Operator Interface
	public static Joystick kLeftStick = new Joystick(0);
	public static Joystick kRightStick = new Joystick(1);
	public static Joystick kOperatorStick  = new XboxController(2);
	
}