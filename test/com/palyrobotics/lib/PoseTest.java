package com.palyrobotics.lib;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.team254.lib.util.Position;
import com.team254.lib.util.Position.RelativePoseGenerator;

public class PoseTest {

	@Test
	public void testZeroRelativePose() {
		Position base_pose = new Position(0,0,0,0,0,0);
		RelativePoseGenerator rel_pose = base_pose.new RelativePoseGenerator();
		Position new_pose = new Position(10,11,1,2,5,6);
		Position diff_pose = rel_pose.get(new_pose);
		assertTrue("Poses should be the same with a zero base", diff_pose.equals(new_pose));
	}
	
	@Test
	public void testNonZeroRelativePose() {
		Position base_pose = new Position(1,2,3,4,5,6);
		RelativePoseGenerator rel_pose = base_pose.new RelativePoseGenerator();
		Position new_pose = new Position(11,12,13,14,15,16);
		Position diff_pose = rel_pose.get(new_pose);
		Position expected_pose = new Position(10,10,13,14,10,16); // uses new pose velocity
		assertTrue("Poses should be the same with a non zero base", diff_pose.equals(expected_pose));
		
	}
}
