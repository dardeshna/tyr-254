package com.palyrobotics.frc2016.routines;

import static com.palyrobotics.frc2016.HardwareAdaptor.kShooter;

import com.palyrobotics.frc2016.Requirables;
import com.palyrobotics.lib.util.routines.Routine;

import edu.wpi.first.wpilibj.Timer;

public class ShootRoutine extends Routine {
	// Used to track the waitTime
	private Timer mTimer = new Timer();		
	// Wait time before unlocking shooter (for grabber and extending lag)
	private final double waitTime = .35;
	private boolean mIsDone = false;
	
	public ShootRoutine() {
		requires(Requirables.rShooter);
	}
	
	@Override
	public void start() {
		mTimer.reset();
		mTimer.start();
	}
	
	@Override
	public void update() {
		// Extend shooter and release grabber
		kShooter.extend();
		kShooter.release();
		if(mTimer.get()>= waitTime) {
			// Wait for potential lag, then shoot
			kShooter.unlock();
			mIsDone = true;
		}
	}

	@Override
	public boolean isFinished() {
		return mIsDone;
	}
	
	@Override
	public String getName() {
		return "ShootSequence";
	}
}