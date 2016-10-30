package com.palyrobotics.lib.util.routines;


/**
 * Composite action, running all sub-routines in sequence
 * 
 * @author dardeshna, niharmitra
 */
public class SequentialRoutine extends Routine {

    private final Routine[] routines;
    private int currentRoutine = 0;
    private boolean isDone;

    public SequentialRoutine(Routine... routines) {
        this.routines = routines;
        requiredSubsystems = Routine.subsystems(routines);
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }

    @Override
    public void update() {
        getCurrentRoutine().update();
        if (getCurrentRoutine().isFinished()) {
        	getCurrentRoutine().cleanup();
        	if (currentRoutine == routines.length-1) {
        		isDone = true;
        	}
        	else {
        		currentRoutine++;
        		getCurrentRoutine().start();
        	}
        }
    }

    @Override
    public void cleanup() {
    }

    @Override
    public void start() {
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
	
	private Routine getCurrentRoutine() {
		return routines[currentRoutine];
	}
}
