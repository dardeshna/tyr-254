package com.palyrobotics.lib.util.routines;


/**
 * Composite action, running all sub-actions at the same time All actions are
 * started then updated until all actions report being done.
 * @author dardeshna
 * @param A list of routines
 */
public class ParallelRoutine extends Routine {

    private final Routine[] routines;

    public ParallelRoutine(Routine... routines) {
        this.routines = routines;
        requiredSubsystems = Routine.subsystems(this.routines);
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
    public void cleanup() {
    	
    }

    @Override
    public void start() {
        for (Routine r : routines) {
        	r.start();
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
		return "ParallelRoutine";
	}
}
