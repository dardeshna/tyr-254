package com.palyrobotics.lib.util.routines;

/**
 * Composite Routine, running all sub-routines at the same time
 * All actions are started then updated until all actions report being done.
 * 
 * @author dardeshna, niharmitra
 */
public class DoUntilRoutine extends Routine {

	private final Routine primaryRoutine;
    private final Routine[] routines;

    public DoUntilRoutine(Routine primaryRoutine, Routine... routines) {
        this.routines = routines;
        this.primaryRoutine = primaryRoutine;
        required = Routine.required(Routine.required(routines, true), primaryRoutine.required, false);
    }

    @Override
    public boolean isFinished() {
        return primaryRoutine.isFinished();
    }

    @Override
    public void update() {
    	primaryRoutine.update();
    	if (primaryRoutine.isFinished()) {
        	primaryRoutine.cleanup();
        }
        for (Routine r : routines) {
        	r.update();
        	if (primaryRoutine.isFinished()) {
            	r.cleanup();
            }
        }
    }

    @Override
    public void cleanup() {
    	
    }

    @Override
    public void start() {
    	primaryRoutine.start();
        for (Routine r : routines) {
        	r.start();
        }
    }

	@Override
	public void cancel() {
		primaryRoutine.cancel();
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
