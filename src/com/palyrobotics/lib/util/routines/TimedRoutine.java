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

	private Routine mRoutine;

	/**
	 * 
	 * @param routine
	 *            Routine to update
	 * @param time
	 *            Time in seconds to run this
	 */
	public TimedRoutine(Routine routine, double time) {
		this.mRoutine = routine;
		this.mRunTime = time;
		required = routine.required;
	}

	@Override
	public void start() {
		this.mStartTime = Timer.getFPGATimestamp();
		mRoutine.start();
	}

	@Override
	public void update() {
		mRoutine.update();
	}
	
	@Override
	public boolean isFinished() {
		return Timer.getFPGATimestamp() >= mStartTime + mRunTime;
	}
	
	@Override
	public void cleanup() {
		mRoutine.cleanup();
	}
	
	@Override
	public void cancel() {
		mRoutine.cancel();
	}

	@Override
	public String getName() {
		return "Timed" + mRoutine.getName();
	}

}