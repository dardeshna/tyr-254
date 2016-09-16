package com.palyrobotics.frc2016.behavior;

public class Commands {
    public enum PresetRequest {
        NONE, MANUAL
    }
    
    // Possible commands for intake
    public enum IntakeRequest {
        NONE, INTAKE, EXHAUST
    }
    
    // Commands for Grabber
    public enum GrabberRequest {
    	GRAB, RELEASE
    }
    
    // Commands for Latch
    public enum LatchRequest {
    	NONE, LOCK, UNLOCK
    }
    
    // Commands for Shooter
    public enum ShooterRequest {
    	NONE, EXTEND, RETRACT
    }
    
    // Commands for TimerDriveRoutine
    public enum TimerDriveRequest {
    	NONE, ACTIVATE
    }
    
    //Commands for EncoderDriveRoutine
    public enum EncoderDriveRequest {
    	NONE, ACTIVATE
    }
    
    public enum AutoAlignmentRequest {
    	NONE, ACTIVATE
    }

    public PresetRequest preset_request;
    
    // Subsystem requests
    public IntakeRequest intake_request;
    public GrabberRequest grabber_request;
    public LatchRequest latch_request;
    public ShooterRequest shooter_request;
    
    // Routine requests
    public TimerDriveRequest timer_drive_request;
    public EncoderDriveRequest encoder_drive_request;
    public AutoAlignmentRequest auto_alignment_request;
    
    // Allows you to cancel routine
    public boolean cancel_current_routine = false;
}
