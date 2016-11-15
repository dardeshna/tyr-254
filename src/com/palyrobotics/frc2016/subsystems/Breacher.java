package com.palyrobotics.frc2016.subsystems;

import com.team254.lib.util.CheesySpeedController;
import com.team254.lib.util.Loop;
import com.team254.lib.util.StateHolder;
import com.team254.lib.util.Subsystem;

public class Breacher extends Subsystem implements Loop {

	private CheesySpeedController m_breacher_motor;
	
	public Breacher(String name, CheesySpeedController motor) {
		super(name);
		m_breacher_motor = motor;
	}
	
	public void update(double joystickInput) {
		state = SubsystemState.OPEN;
		m_breacher_motor.set(joystickInput);
	}
	
	public void idle() {
		state = SubsystemState.IDLE;
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
