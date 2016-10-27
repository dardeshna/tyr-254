package com.palyrobotics.frc2016.behavior;

import com.palyrobotics.frc2016.routines.Routine;
import com.team254.lib.util.StateHolder;
import com.team254.lib.util.Tappable;

public class RoutineManager implements Tappable {

	public boolean isZero(double val) {
		return val == 0 || (val < 0.001 && val > -0.001);
	}

	private Routine m_cur_routine = null;
	//    private ManualRoutine m_manual_routine = new ManualRoutine();

	public RoutineManager() {

	}

	private void setNewRoutine(Routine new_routine) {
		// Cancel if new routine diff from current routine
		boolean needs_cancel = (new_routine != m_cur_routine) && (m_cur_routine != null);

		boolean needs_start = (new_routine != m_cur_routine) && (new_routine != null);
		// Cancel old routine
		if (needs_cancel) {
			m_cur_routine.cancel();
			// Reset all setpoints
		}
		// Start next routine
		if (needs_start) {
			new_routine.start();
		}
		m_cur_routine = new_routine;

	}

	public Routine getCurrentRoutine() {
		return m_cur_routine;
	}
	
	public void reset() {
		if(m_cur_routine != null) {
			m_cur_routine.cancel();
		}
		
		setNewRoutine(null);
		
	}
	
	public void submitRoutine(Routine routine) {
		
		if (routine.getName() != m_cur_routine.getName()) {
			setNewRoutine(routine);
		}
		
	}
	
	public void update() {
		
		if (m_cur_routine != null) {
			m_cur_routine.update();
			if (m_cur_routine.isFinished()) {
				m_cur_routine.cleanup();
				setNewRoutine(null);
			}
		}
		
	}

	@Override
	public void getState(StateHolder states) {
		states.put("mode", m_cur_routine != null ? m_cur_routine.getName() : "---");
	}

	@Override
	public String getName() {
		return "behaviors";
	}
}