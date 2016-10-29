package com.palyrobotics.lib.util.routines;

/**
 * Composite action, running all sub-routines in sequence
 * 
 * @author Devin, Nihar
 */
public class SequentialRoutine extends Routine {
	// List of routines to run, in the order to run them in
	private final Routine[] routines;
	private int currentRoutine = 0;
	/** Used for {@link SequentialRoutine#isFinished()} and to not update once done **/
	private boolean mIsDone = false;

	/**
	 * Runs the routines in sequence
	 * @param routines Ordered list of routines to run
	 */
	public SequentialRoutine(Routine... routines) {
		this.routines = routines;
		requiredSubsystems = Routine.subsystems(routines);
	}

	@Override
	public boolean isFinished() {
		return mIsDone;
	}

	@Override
	public void update() {
		if(mIsDone) {
			return;
		}
		
		getCurrentRoutine().update();
		if (getCurrentRoutine().isFinished()) {
			getCurrentRoutine().cleanup();
			if(currentRoutine == routines.length-1) {
				mIsDone = true;
			}
			if (!mIsDone) {
				currentRoutine++;
				getCurrentRoutine().start();
			}
		}
	}

	@Override
	public void start() {
		getCurrentRoutine().start();
	}

	@Override
	public void cancel() {
		getCurrentRoutine().cancel();
	}

	@Override
	public String getName() {
		String name = "SequentialRoutine";
		for(Routine r : routines) {
			name+=r.getName();
		}
		return name;
	}

	public Routine getCurrentRoutine() {
		return routines[currentRoutine];
	}
}
