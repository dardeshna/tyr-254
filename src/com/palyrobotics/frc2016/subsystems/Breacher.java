package com.palyrobotics.frc2016.subsystems;

import com.team254.lib.util.CheesySpeedController;
import com.team254.lib.util.Loop;
import com.team254.lib.util.StateHolder;
import com.team254.lib.util.Subsystem;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Breacher extends Subsystem implements Loop{

	private CheesySpeedController m_breacher_motor;
	private AnalogPotentiometer m_potentiometer;
	
	public enum BreacherState {
		OPEN, IDLE
	}
	public BreacherState state = BreacherState.IDLE;
	
	public Breacher(String name, CheesySpeedController motor) {
		super(name);
		m_breacher_motor = motor;
	}
	
	public Breacher(String name, CheesySpeedController motor, AnalogPotentiometer breacher_potentiometer) {
		super(name);
		m_breacher_motor = motor;
		m_potentiometer = breacher_potentiometer;
	}

	public void update(double joystickInput) {
		state = BreacherState.OPEN;
		m_breacher_motor.set(joystickInput);
	}
	
	public void idle() {
		state = BreacherState.IDLE;
		m_breacher_motor.set(0);
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onLoop() {
	
	}

	@Override
	public void onStop() {

	}
	
	@Override
	public void getState(StateHolder states) {

	}

	@Override
	public void reloadConstants() {
		
	}

}
