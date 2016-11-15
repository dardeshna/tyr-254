package com.palyrobotics.frc2016.auto;

import com.palyrobotics.frc2016.behavior.RoutineManager;

public class AutoModeExecuter {
    private AutoMode autoMode;
    private Thread thread = null;
    private RoutineManager routineManager;

    public AutoModeExecuter(RoutineManager routineManager) {
    	this.routineManager = routineManager;
    }
    
    public void setAutoMode(AutoMode autoMode) {
        this.autoMode = autoMode;
    }

    /**
     * Starts the thread and begins the AutoMode
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (autoMode != null) {
                    	autoMode.setRoutineManager(routineManager);
                        autoMode.run();
                    }
                }
            });
            thread.start();
        }

    }

    /**
     * Terminates the AutoMode if it is not done already and kills the thread
     */
    public void stop() {
        if (autoMode != null) {
            autoMode.stop();
        }
        thread = null;
    }

}
