package com.palyrobotics.frc2016.routines.auto;

import com.palyrobotics.frc2016.routines.Routine;

import edu.wpi.first.wpilibj.Timer;

public class TimeoutRoutine extends Routine {
    double m_timeout;
    double m_time_start;

    public TimeoutRoutine(double timeout) {
        m_timeout = timeout;
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= m_time_start + m_timeout;
    }

    @Override
    public void update() {
    }

    @Override
    public void start() {
        m_time_start = Timer.getFPGATimestamp();
    }

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TimeoutRoutine";
	}

}
