package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by BHS-Lab on 1/8/2018.
 */

public class GetPositionWithLines extends AutoAction {

    // Constant for the diameter of the wheels in inches
    public static final double WHEEL_DIAMETER = 4.0;
    
    // Drive motor power
    double power;
    
    // Variable for what state the action is in:
    // 1: moving forward to calibrate the light sensor to the black tiles
    // 2: moving forward till first line
    // 3: moving forward till second line
    int state;
    
    // variables for the calibration of the light sensor; number of readings taken and current average value for the black tiles
    int readings;
    double blackVal;
    
    // variables for ending the program even if one motor gets stuck and is still busy
    boolean almostEnd;
    double almostEndTime;
    
    // encoder values to be set for the first and second lines of the Cryptobox Zone
    int firstLine;
    int secondLine;

    public GetPositionWithLines(AutonomousOpMode opmode, double power) {
        // Call AutoAction constructor
        super(opmode);
        
        // Set power property
        this.power = power;
    }

    @Override
    public void init() {
        // Call AutoAction init()
        super.init();
        
        // Set the drive motors to run for .2 revolutions
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);
        opmode.setDriveMotorsPosition((int) (-.25 * MoveStraight.REVS_MULTIPLIER));
        opmode.setDriveMotorsPower(power);
        
        // initiate variables for state and calibration
        state = 1;
        readings = 0;
        blackVal = 0;
    }

    @Override
    public void update() {
        opmode.telemetry.addData("pellow1", blackVal);
        // do different things based on state variable
        if (state == 1) {
            // catch when the first drive motor stops being busy and start a one second countdown for the motors to stop
            if (opmode.getDriveMotorsBusy().size() < 4 && !almostEnd) {
                almostEnd = true;
                almostEndTime = System.nanoTime() / 1000000000;
            }
    
            // Tells the program it's done moving straight if more than a second has passed since almostEnd was 
            // set to true - this is meant to prevent the robot from getting stuck in an infinite loop if one wheel gets stuck
            if (almostEnd && System.nanoTime() / 1000000000 - almostEndTime > 1 || !opmode.driveMotorsBusy()) {
                state = 2;
                opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                // keeps the motors moving so that they move towards the first line of the Cryptobox Zone
                opmode.setDriveMotorsPower(power);
            } else {
                // averages the current light sensor reading into the black value
                blackVal = (blackVal * readings + opmode.getLightVal()) / (readings + 1);
            }
        }
        else if (state == 2) {
            // if the light sensor value is greater by at least .05 than the black value, record the encoder position as the first line
            if (opmode.getLightVal() < blackVal - 0.02) {
                firstLine = opmode.motorDriveLeftFront.getCurrentPosition();
                state = 3;
            }
        // same thing for second line, but it also must be at least a twentieth of a revolution beyond the first line to ensure the robot isn't still seeing the first line
        }  else if (state == 3) {
            if (opmode.getLightVal() < blackVal - 0.02 && Math.abs(opmode.motorDriveLeftFront.getCurrentPosition()) > Math.abs(firstLine) + MoveStraight.REVS_MULTIPLIER / 5) {
                secondLine = opmode.motorDriveLeftFront.getCurrentPosition();
                done = true;
            }
        }
    }

    @Override
    public void end() {
        //Calls the end() method from the superclass, AutoAction
        super.end();

        //Resets the drive motor encoders and sets their power to 0
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsPower(0);
        
        //Uses the encoder positions from when it crossed the first and second line of the Cryptobox Zone
        //to calculate the distance in inches between the lines
        double dist = ((double) (Math.abs(secondLine) - Math.abs(firstLine))) / MoveStraight.REVS_MULTIPLIER * WHEEL_DIAMETER * Math.PI;
        
        //Uses that distance to calculate the depth (distance from the wall) and horizontal displacement of the robot
        //from the center of the cryptobox
        double depth = 24 * (dist / 32);
        double horizontalDisplacement = dist / 2;
        
        // Adds depth and horizontalDisplacement as variables to the opmode
        opmode.addData("depth", -depth / (WHEEL_DIAMETER * Math.PI) + 0.3);
        opmode.addData("horizontalDisplacement", horizontalDisplacement / (WHEEL_DIAMETER * Math.PI));
    }
}
