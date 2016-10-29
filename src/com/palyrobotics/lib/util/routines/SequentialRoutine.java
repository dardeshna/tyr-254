package com.palyrobotics.lib.util.routines;

/**
 * Composite action, running all sub-actions at sequentially
 * @author dardeshna
 * @param A list of routines
 */
public class SequentialRoutine extends Routine {

    private final Routine[] routines;
    private int currentRoutine = 0;

    public SequentialRoutine(Routine... routines) {
        this.routines = routines;
        requiredSubsystems = Routine.subsystems(routines);
    }

    @Override
    public boolean isFinished() {
        return currentRoutine == routines.length-1 && getCurrentRoutine().isFinished();
    }

    @Override
    public void update() {
        getCurrentRoutine().update();
        if (getCurrentRoutine().isFinished()) {
        	getCurrentRoutine().cleanup();
        	if (!isFinished())
        		currentRoutine++;
        		getCurrentRoutine().start();
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
		return "SequentialRoutine";
	}
	
	private Routine getCurrentRoutine() {
		return routines[currentRoutine];
	}
}
