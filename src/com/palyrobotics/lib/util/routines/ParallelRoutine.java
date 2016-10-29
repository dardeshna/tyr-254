package com.palyrobotics.lib.util.routines;

/**
 * Composite Routine, running all sub-routines at the same time
 * All actions are started then updated until all actions report being done.
 * 
 * @author Devin, Nihar
 */
public class ParallelRoutine extends Routine {
	
	// All the routines to run in parallel
	private final Routine[] routines;

	/**
	 * 
	 * @param routines A list of routines
	 */
	public ParallelRoutine(Routine... routines) {
		this.routines = routines;
		requiredSubsystems = Routine.subsystems(this.routines);
	}

	@Override
	public void start() {
		for (Routine r : routines) {
			r.start();
		}
	}
	
	@Override
	public void update() {
		for (Routine r : routines) {
			if (!r.isFinished()) {
				r.update();
				if (r.isFinished()) {
					r.cleanup();
				}
			}
		}
	}

	@Override
	public boolean isFinished() {
		for (Routine r : routines) {
			if (!r.isFinished()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void cleanup() {
		for (Routine r : routines) {
			r.cleanup();
		}
	}

	@Override
	public void cancel() {
		for (Routine r : routines) {
			r.cancel();
		}
	}

	@Override
	public String getName() {
		String name = "ParallelRoutine";
		for (Routine r : routines) {
			name+=r.getName();
		}
		return name;
	}
}
