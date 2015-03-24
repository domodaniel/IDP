//authors: Daniel Uyematsu & Ronan Konishi
// you're welcome in advance 4 the extensive commenting :P
package org.usfirst.frc.team687.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends IterativeRobot {
	DoubleSolenoid lsol, rsol;
	Joystick joy;
    public void robotInit() {
    	lsol = new DoubleSolenoid(0,1);
    	rsol = new DoubleSolenoid(2,3);
    	joy = new Joystick(1);
    	compress.start();
    }
    public void autonomousPeriodic() {
    }
    public void teleopInit()	{
    }
    boolean lsolCheck = false;			//lsol status recorder
    boolean rsolCheck = false;			//rsol status recorder
    int armMode = 1;								//variable where we can tell what mode the driver set
//  1 (Flappy Bird Mode) moves both pistons @ same time with both true or false (flapping wings :P)
//  2 (3 Stooges Mode) moves both pistons @ same time but with opposite boolean values (like slapping somebody)
//  3 (Wing Slap Mode) moves one piston @ a time independently of the other piston
    public void teleopPeriodic() {
        if(joy.getRawButton(7)){       //mode changer
        	armMode = 1;	//set armMode to Flappy Bird Mode
        } else if(joy.getRawButton(9)){	
        	armMode = 2;						//set armMode to 3 Stooges Mode
        } else if(joy.getRawButton(11)){
        	armMode = 3;		//set armMode to Wing Slap Mode
        }
        if(armMode == 1){						//if in Flappy Bird Mode
        	if(lsolCheck != rsolCheck){				//if arms not oriented the same 4 Flappy Bird Mode
        		if(lsolCheck){					//if left arm is out
        			lsol.set(DoubleSolenoid.Value.kReverse); //move lsol back in
        			lsolCheck = false;			// and record the movement
        		} else{						// if left arm is in
        			lsol.set(DoubleSolenoid.Value.kForward); // move in
        			lsolCheck = true;			//and record
        		}
        	}
	        if(joy.getRawButton(3)){
		    		lsol.set(DoubleSolenoid.Value.kForward); //move both solenoids out
		    		lsolCheck = true;			// record that both are out
		    		rsol.set(DoubleSolenoid.Value.kForward); 
		    		rsolCheck = true;
	        }
	        if(joy.getRawButton(4)){
	        		lsol.set(DoubleSolenoid.Value.kReverse); //move both in
	        		lsolCheck = false;			// and record both
	        		rsol.set(DoubleSolenoid.Value.kReverse);
	        		rsolCheck = false;
	        }
        } else if(armMode == 2){				//if in 3 Stooges Mode
        	if(lsolCheck == rsolCheck){			//if both the same(we need it 2b different 4 this mode)
	        	if(lsolCheck){			//if both are out
					lsol.set(DoubleSolenoid.Value.kReverse);//put left 1 in
					lsolCheck = false;			// and record
				} else {					// if both are in
					lsol.set(DoubleSolenoid.Value.kForward); //move left 1 out
					lsolCheck = true;			// and record
				}
        	}
        	if(joy.getRawButton(3)){
        			lsol.set(DoubleSolenoid.Value.kReverse); // move left in
        			lsolCheck = false;			// and record
        			rsol.set(DoubleSolenoid.Value.kForward); // and right out
        			rsolCheck = true;
        	}
        	if(joy.getRawButton(4)){			// if left is in, therefore right is out,
        			lsol.set(DoubleSolenoid.Value.kForward); // move left out
        			lsolCheck = true;			// and record
        			rsol.set(DoubleSolenoid.Value.kReverse); // move right in
        			rsolCheck = false;			// and record
        	}
        } else if(armMode == 3){	//if in Wing Attack Mode
        	if(joy.getRawButton(3)){
        		lsol.set(DoubleSolenoid.Value.kReverse); //move left in independent of other
        		lsolCheck = false;			// and record
        	}
        	else if(joy.getRawButton(5)){
        		lsol.set(DoubleSolenoid.Value.kForward); //move left out
        		lsolCheck = true;			//and record
        	}
        	if(joy.getRawButton(4)){
        		rsol.set(DoubleSolenoid.Value.kReverse); //move right in
        		rsolCheck = false;			//and record
        	}
        	else if(joy.getRawButton(6)){
        			rsol.set(DoubleSolenoid.Value.kForward); // move right out
        			rsolCheck = true;			//and record
        	}
        }
        SmartDashboard.putBoolean("rsol",rsolCheck);
        SmartDashboard.putBoolean("lsol", lsolCheck);
    }
    public void testPeriodic() {
    }
}
