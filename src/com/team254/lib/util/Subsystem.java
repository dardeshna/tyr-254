package com.team254.lib.util;

import com.palyrobotics.frc2016.Dashboard;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public abstract class Subsystem implements Tappable {
	
	protected Dashboard mDashboard = Dashboard.getInstance();
    protected String name;
    
	public enum SubsystemState {
		CONTROLLER, OPEN, IDLE
	}
	public SubsystemState state = SubsystemState.IDLE;

    public Subsystem(String name) {
        this.name = name;
        SystemManager.getInstance().add(this);
    }

    public String getName() {
        return name;
    }

    public abstract void reloadConstants();
}
