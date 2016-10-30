package com.palyrobotics.frc2016;

import com.palyrobotics.frc2016.auto.AutoMode;
import com.palyrobotics.frc2016.auto.AutoModeExecuter;
import com.palyrobotics.frc2016.auto.AutoModeSelector;
import com.palyrobotics.frc2016.behavior.RoutineManager;
import com.palyrobotics.frc2016.subsystems.Breacher;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.palyrobotics.frc2016.subsystems.Intake;
import com.palyrobotics.frc2016.subsystems.Shooter;
import com.team254.lib.util.DriveSignal;
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
	public static RobotState s_robot_state = RobotState.DISABLED;
	public static RobotState getState() {
		return s_robot_state;
	}
	public static void setState(RobotState state) {
		s_robot_state = state;
	}
	
	Looper subsystem_looper = new Looper();

	AutoModeExecuter autoModeRunner = new AutoModeExecuter();
	
	// Subsystems
	Drive drive = HardwareAdaptor.kDrive;
	Shooter shooter = HardwareAdaptor.kShooter;
	Intake intake = HardwareAdaptor.kIntake;
	Breacher breacher = HardwareAdaptor.kBreacher;
	PowerDistributionPanel pdp = HardwareAdaptor.kPDP;

	RoutineManager routine_manager = new RoutineManager();
	OperatorInterface operator_interface = new OperatorInterface(routine_manager);

	Joystick leftStick = HardwareAdaptor.kLeftStick;
	Joystick rightStick = HardwareAdaptor.kRightStick;
	Joystick operatorStick = HardwareAdaptor.kOperatorStick;

	Dashboard mDashboard = Dashboard.getInstance();
	NetworkTable sensorTable;

	static {
		SystemManager.getInstance().add(new RobotData());
	}

	@Override
	public void robotInit() {
		System.out.println("Start robotInit()");
		subsystem_looper.register(drive);
		subsystem_looper.register(shooter);
		subsystem_looper.register(breacher);
		sensorTable = NetworkTable.getTable("Sensor");
		mDashboard.init();
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
		subsystem_looper.start();
	}

	@Override
	public void autonomousPeriodic() {
		mDashboard.update();
	}

	@Override
	public void teleopInit() {
		setState(RobotState.TELEOP);
		System.out.println("Start teleopInit()");
		subsystem_looper.start();
	}

	@Override
	public void teleopPeriodic() {
		// Passes joystick control to subsystems for their processing
		
		operator_interface.update();
		routine_manager.update();

		// Update sensorTable with encoder distances
		sensorTable.putString("left", String.valueOf(HardwareAdaptor.kLeftDriveEncoder.getDistance()));
		sensorTable.putString("right", String.valueOf(HardwareAdaptor.kRightDriveEncoder.getDistance()));
		mDashboard.update();
	}

	@Override
	public void disabledInit() {
		setState(RobotState.DISABLED);

		System.out.println("Start disabledInit()");
		System.out.println("Current Auto Mode: "+AutoModeSelector.getInstance().getAutoMode().toString());
		// Stop auto mode
		autoModeRunner.stop();

		// Stop routines
		routine_manager.reset();

		// Stop control loops
		subsystem_looper.stop();

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