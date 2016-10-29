package com.palyrobotics.frc2016.actions;

import static com.palyrobotics.frc2016.HardwareAdaptor.kShooter;

import com.palyrobotics.lib.util.routines.Action;

import edu.wpi.first.wpilibj.Timer;

public class ShootSequence extends Action {

	private Timer timer = new Timer();		
	private final double waitTime = .35;
	private boolean isDone = false;
	
	public ShootSequence() {
		requires(kShooter);
	}
	
	@Override
	public void start() {
		timer.reset();
		timer.start();
	}
	
	@Override
	public void update() {
		// Extend shooter and release grabber
		kShooter.extend();
		kShooter.release();
		if(timer.get()>= waitTime) {
			// Wait for potential lag, then shoot
			kShooter.unlock();
			isDone = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isDone;
	}

}