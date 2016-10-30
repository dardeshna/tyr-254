package com.palyrobotics.lib.util.routines;

import edu.wpi.first.wpilibj.Timer;

/**
 * Runs a routine for the allocated amount of time Note: This ignores the
 * default {@link Routine#isFinished()} of the routine given
 * 
 * @author Devin, Nihar
 *
 */
public class TimedRoutine extends Routine {

	private double mRunTime;
	private double mStartTime;

	private Routine mAction;

	/**
	 * 
	 * @param action
	 *            Routine to update
	 * @param time
	 *            Time in seconds to run this
	 */
	public TimedRoutine(Routine action, double time) {
		this.mAction = action;
		this.mRunTime = time;
		requiredSubsystems = action.requiredSubsystems;
	}

	@Override
	public void start() {
		this.mStartTime = Timer.getFPGATimestamp();
		mAction.start();
	}

	@Override
	public void update() {
		mAction.update();
	}
	
	@Override
	public boolean isFinished() {
		return Timer.getFPGATimestamp() >= mStartTime + mRunTime;
	}
	
	@Override
	public void cleanup() {
		mAction.cleanup();
	}
	
	@Override
	public void cancel() {
		mAction.cancel();
	}

	@Override
	public String getName() {
		return "Timed" + mAction.getName();
	}

}