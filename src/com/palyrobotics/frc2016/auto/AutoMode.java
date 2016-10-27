package com.palyrobotics.frc2016.auto;

import com.palyrobotics.frc2016.HardwareAdaptor;
import com.palyrobotics.frc2016.routines.auto.TimeoutRoutine;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.palyrobotics.frc2016.subsystems.Intake;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public abstract class AutoMode extends AutoModeBase {

    protected Drive drive = HardwareAdaptor.kDrive;
    protected PowerDistributionPanel pdp = HardwareAdaptor.kPDP;
    protected Intake intake = HardwareAdaptor.kIntake;

    public void waitTime(double seconds) throws AutoModeEndedException {
        updateRoutine(new TimeoutRoutine(seconds));
    }    
}
