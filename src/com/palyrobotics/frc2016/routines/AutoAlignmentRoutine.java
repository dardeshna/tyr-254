package com.palyrobotics.frc2016.routines;

import com.palyrobotics.frc2016.HardwareAdaptor;
import com.palyrobotics.frc2016.subsystems.Drive;
import com.palyrobotics.lib.util.routines.Routine;
import com.team254.lib.util.DriveSignal;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutoAlignmentRoutine extends Routine {
	/* Start = Start of the routine
	 * Set_Angle = wait for vision, then set angle
	 * Aligning = waiting while robot turns
	 * Done = no goal spotted, or finished iterations
	 */
	private enum AutoAlignStates {
		START, SET_ANGLE, ALIGNING, DONE
	}
	private Drive drive = HardwareAdaptor.kDrive;


	public AutoAlignStates m_state = AutoAlignStates.START;
	private NetworkTable table = NetworkTable.getTable("visiondata");
	// Threshold angle for which we will turn
	private final double m_min_angle = 3;
	
	// Number of iterations for successive auto alignments
	private final int m_default_iterations = 2;
	private int m_iterations = m_default_iterations;

	// Timer used for waiting period for camera stabilization
	private Timer m_timer = new Timer();
	private final double m_wait_time = 1.5; 
	
	
	public AutoAlignmentRoutine() {
		requires(HardwareAdaptor.kDrive);
	}
	
	/**
	 * Changes number of successive alignments
	 */
	public void setIterations(int iterations) {
		this.m_iterations = iterations;
	}

	@Override
	public void update() {
		AutoAlignStates new_state = m_state;
		switch(m_state) {
		case START:
			if(m_iterations > 0) {
				m_timer.reset();
				m_timer.start();
				drive.reset();
				System.out.println("Started auto align " + m_state);
				new_state = AutoAlignStates.SET_ANGLE;
			} else {
				new_state = AutoAlignStates.DONE;
			}
			break;
		case SET_ANGLE:
			// Wait for m_wait_time before reading vision data (latency)
			if(m_timer.get() < m_wait_time) {
				break;
			}
			// If angle turnpoint has been set, then set this routine to waiting for alignment
			// Check for no goal, then already aligned, otherwise set setpoint
			double skewAngle = table.getNumber("skewangle", 10000)/2;
			if(skewAngle == 10000/2) {
				System.out.println(skewAngle);
				System.out.println("No goal detected");
				m_iterations = 0;
				new_state = AutoAlignStates.DONE;				
			}
			else if(Math.abs(skewAngle) <= m_min_angle) {
				System.out.println("Already aligned");
			} else {
//				skewAngle = (skewAngle >=0) ? skewAngle-2:skewAngle+2;
				drive.turnAngle(skewAngle, 0.5);
				new_state = AutoAlignStates.ALIGNING;
			}
			break;
		case ALIGNING:
//			System.out.println("aligning, waiting on controller");
			// If finished turning, start next sequence or finish
			if(drive.controllerOnTarget()) {
				System.out.println("Drive controller reached target");
				m_iterations--;
				if(m_iterations > 0) {
					System.out.println("Starting new iteration");
					new_state = AutoAlignStates.START;
				} else {
					System.out.println("Finished auto aligning");
					new_state = AutoAlignStates.DONE;
				}
			}
			break;
		default:
			break;
		}
		if(m_state != new_state) {
			m_state = new_state;
		}
	}

	@Override
	public void cancel() {
		m_state = AutoAlignStates.DONE;
		cleanup();
	}
	
	@Override
	public void cleanup() {
		drive.setOpenLoop(DriveSignal.NEUTRAL);
		drive.reset();
	}

	@Override
	public void start() {
		m_timer.reset();
		m_timer.start();
		if(m_iterations < 1) {
			m_iterations = m_default_iterations;
		}
	}

	@Override
	public boolean isFinished() {
		return m_state == AutoAlignStates.DONE && m_iterations == 0;
	}

	@Override
	public String getName() {
		return "Auto Alignment Routine";
	}

}
