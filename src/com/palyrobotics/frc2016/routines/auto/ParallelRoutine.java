package com.palyrobotics.frc2016.routines.auto;

import java.util.ArrayList;

import com.palyrobotics.frc2016.routines.Routine;

/**
 * Composite action, running all sub-actions at the same time All actions are
 * started then updated until all actions report being done.
 * @author Team 254
 * @param A List of Action objects
 */
public class ParallelRoutine extends Routine {

    private final ArrayList<Routine> routines;

    public ParallelRoutine(ArrayList<Routine> routines) throws Exception {
        this.routines = routines;
        requiredSubsystems = Routine.checkSubsystems(routines);
    }

    @Override
    public boolean isFinished() {
        boolean all_finished = true;
        for (Routine r : routines) {
            if (!r.isFinished()) {
                all_finished = false;
            }
        }
        return all_finished;
    }

    @Override
    public void update() {
        for (Routine r : routines) {
            r.update();
        }
    }

    @Override
    public void cleanup() {
        for (Routine r : routines) {
            r.cleanup();
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
		return "ParallelRoutine";
	}
}
