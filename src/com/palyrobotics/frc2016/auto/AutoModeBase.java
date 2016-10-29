package com.palyrobotics.frc2016.auto;

import com.palyrobotics.lib.util.routines.Routine;

public abstract class AutoModeBase {
    protected double m_update_rate = 1.0 / 50.0;
    protected boolean m_active = false;

    protected abstract void routine();
    
    public abstract String toString();

    public void run() {
        m_active = true;
        routine();

    }

    public void stop() {
        m_active = false;
    }

    public boolean isActive() {
        return m_active;
    }

    public void execute(Routine routine) {
        routine.start();
        while (isActive() && !routine.isFinished()) {
            routine.update();
            try {
                Thread.sleep((long) (m_update_rate * 1000.0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        routine.cleanup();
    }

}
