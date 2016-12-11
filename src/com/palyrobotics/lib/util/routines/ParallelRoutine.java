package com.palyrobotics.lib.util.routines;

/**
 * Composite Routine, running all sub-routines at the same time
 * All actions are started then updated until all actions report being done.
 * 
 * @author dardeshna, niharmitra
 */
public class ParallelRoutine extends Routine {

    private final Routine[] routines;
    private boolean cleanUpWhenDone = true;
    
    public ParallelRoutine(Routine... routines) {
        this.routines = routines;
        required = Routine.required(this.routines, false);
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
	        	if (r.isFinished() && cleanUpWhenDone) {
	            	r.cleanup();
	            }
        	}
        }
    }

    @Override
    public void cleanup() {
    	if (!cleanUpWhenDone) {
    		for (Routine r : routines) {
    			r.cleanup();
    		}
    	}
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
		String name = "ParallelRoutine";
		for (Routine r : routines) {
			name+=r.getName();
		}
		return name;

	}
	
	public void cleanUpAtEnd() {
		cleanUpWhenDone = false;
	}
}
