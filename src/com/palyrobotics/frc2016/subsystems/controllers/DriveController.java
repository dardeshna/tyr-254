package com.palyrobotics.frc2016.subsystems.controllers;

import com.team254.lib.util.DriveSignal;
import com.team254.lib.util.Position;

public interface DriveController {
	DriveSignal update(Position pose);

	Position getCurrentSetpoint();

	public boolean onTarget();

}