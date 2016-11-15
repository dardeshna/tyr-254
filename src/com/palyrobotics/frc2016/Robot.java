package com.palyrobotics.frc2016;

import com.palyrobotics.frc2016.auto.AutoMode;
import com.palyrobotics.frc2016.auto.AutoModeExecuter;
import com.palyrobotics.frc2016.auto.AutoModeSelector;
import com.palyrobotics.frc2016.behavior.RoutineManager;
import com.palyrobotics.frc2016.subsystems.Breacher;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.palyrobotics.frc2016.subsystems.Intake;
import com.palyrobotics.frc2016.subsystems.Shooter;
import com.team254.lib.util.Looper;
import com.team254.lib.util.RobotData;
import com.team254.lib.util.SystemManager;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot {

	public enum RobotState {
		DISABLED, AUTONOMOUS, TELEOP
	}
	public static RobotState robotState = RobotState.DISABLED;
	public static RobotState getState() {
		return robotState;
	}
	public static void setState(RobotState state) {
		robotState = state;
	}
	
	Looper subsystemLooper = new Looper();
	
	// Subsystems
	Drive drive = HardwareAdaptor.kDrive;
	Shooter shooter = HardwareAdaptor.kShooter;
	Intake intake = HardwareAdaptor.kIntake;
	Breacher breacher = HardwareAdaptor.kBreacher;
	PowerDistributionPanel pdp = HardwareAdaptor.kPDP;

	RoutineManager routineManager = new RoutineManager();
	OperatorInterface operatorInterface = new OperatorInterface(routineManager);
	AutoModeExecuter autoModeRunner = new AutoModeExecuter(routineManager);

	Joystick leftStick = HardwareAdaptor.kLeftStick;
	Joystick rightStick = HardwareAdaptor.kRightStick;
	Joystick operatorStick = HardwareAdaptor.kOperatorStick;

	Dashboard dashboard = Dashboard.getInstance();
	NetworkTable sensorTable;

	static {
		SystemManager.getInstance().add(new RobotData());
	}

	@Override
	public void robotInit() {
		System.out.println("Start robotInit()");
		subsystemLooper.register(drive);
		subsystemLooper.register(shooter);
		subsystemLooper.register(breacher);
		sensorTable = NetworkTable.getTable("Sensor");
		dashboard.init();
	}

	@Override
	public void autonomousInit() {
		setState(RobotState.AUTONOMOUS);
		drive.reset();
		
		AutoMode mode = AutoModeSelector.getInstance().getAutoMode();
		autoModeRunner.setAutoMode(mode);
		// Prestart auto mode
		autoModeRunner.start();
		// Start control loops
		subsystemLooper.start();
	}

	@Override
	public void autonomousPeriodic() {
		dashboard.update();
	}

	@Override
	public void teleopInit() {
		setState(RobotState.TELEOP);
		System.out.println("Start teleopInit()");
		subsystemLooper.start();
	}

	@Override
	public void teleopPeriodic() {
		// Passes joystick control to subsystems for their processing
		
		operatorInterface.update();
		routineManager.update();

		// Update sensorTable with encoder distances
		sensorTable.putString("left", String.valueOf(HardwareAdaptor.kLeftDriveEncoder.getDistance()));
		sensorTable.putString("right", String.valueOf(HardwareAdaptor.kRightDriveEncoder.getDistance()));
		dashboard.update();
	}

	@Override
	public void disabledInit() {
		setState(RobotState.DISABLED);

		System.out.println("Start disabledInit()");
		System.out.println("Current Auto Mode: "+AutoModeSelector.getInstance().getAutoMode().toString());
		// Stop auto mode
		autoModeRunner.stop();

		// Stop routines
		routineManager.reset();

		// Stop control loops
		subsystemLooper.stop();

		drive.idle();
		shooter.idle();
		intake.idle();
		breacher.idle();
		
		System.gc();

		System.out.println("end disable init!");
	}

	@Override
	public void disabledPeriodic() {
		if(Dashboard.getInstance().getSelectedAutoMode() != "-1") {
			AutoModeSelector.getInstance().setFromDashboard();
		}
	}
}