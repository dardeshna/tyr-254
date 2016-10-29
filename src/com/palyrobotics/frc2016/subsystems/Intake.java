package com.palyrobotics.frc2016.subsystems;

import com.palyrobotics.frc2016.subsystems.controllers.ConstantVoltageController;
import com.team254.lib.util.CheesySpeedController;
import com.team254.lib.util.Controller;
import com.team254.lib.util.Loop;
import com.team254.lib.util.StateHolder;
import com.team254.lib.util.Subsystem;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

/** 
 * Tyr: Stationary accumulator with 2 motors
 * Derica: Mobile intake on pivot with 1 motor and potentiometer
 * @author Nihar
 */
public class Intake extends Subsystem implements Loop {
	// Used mainly for autonomous raising and lowering of the shooter
	public enum IntakeState {
		CONTROLLER, IDLE, OPEN
	}
	public IntakeState state = IntakeState.IDLE;
	
	// One of the following will be null depending on the robot
	CheesySpeedController m_left_motor = null;
	CheesySpeedController m_right_motor = null;
	CheesySpeedController m_arm_motor = null;
	
	// Potentiometer may exist for Derica intake's arm, if null, disables holding position
	AnalogPotentiometer m_arm_potentiometer = null;
	Controller m_controller = null;
	
	/**
	 * Set intake to a single speed (both motors if Tyr)
	 * Positive is to intake, negative is to exhaust
	 * @param speed target speed (negative is exhaust)
	 */
	private void setSpeed(double speed) {
		m_left_motor.set(speed);
		m_right_motor.set(speed);
	}
	
	public void intake() {
		state = IntakeState.CONTROLLER;
		m_controller = new ConstantVoltageController(1.0);
	}
	
	public void expel() {
		state = IntakeState.CONTROLLER;
		m_controller = new ConstantVoltageController(-1.0);

	}
	
	public void spin(double speed) {
		state = IntakeState.OPEN;
		m_controller = null;
		setSpeed(speed);
	}
	
	public void idle() {
		state = IntakeState.IDLE;
		setSpeed(0);
		m_controller = null;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onLoop() {
		if (state == IntakeState.CONTROLLER) {
			setSpeed(((ConstantVoltageController) m_controller).get());
		}
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Tyr - pass left then right motors
	 * Derica - pass intake motor then arm motor
	 * Pass potentiometer if there is one, else pass null
	 * @param name name of subsystem
	 * @param motor1 Left motor, or intake motor
	 * @param motor2 Right motor, or arm motor
	 * @param arm_potentiometer Set null if none, otherwise Derica's arm potentiometer 
	 */
	public Intake(String name, CheesySpeedController motor1, 
			CheesySpeedController motor2, AnalogPotentiometer armPotentiometer) {
		super(name);
		
		m_left_motor = motor1;
		m_right_motor = motor2;
		m_arm_motor = null;
		
	}
	
	@Override
	public void reloadConstants() {
	}

	@Override
	public void getState(StateHolder states) {
	}

	
}
