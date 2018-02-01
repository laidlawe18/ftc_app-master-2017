package org.firstinspires.ftc.teamcode;

//This imports the method we need to run the program
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.AutoAction;

/**
 * Created by BHS-Lab on 11/17/2017.
 */

//Makes this class a subclass of AutoAction
public class MoveStraight extends AutoAction {

    //Sets up a public variable to convert from the encoder's measurement to revolutions (1440 encoder units = 1 revolution)
    public static final int REVS_MULTIPLIER = 1440;

    //Creates a number of instance variables which will be used in this class
    double revs;
    String revsName;
    boolean almostEnd;
    double almostEndTime;
    double startAngle;
    double leftPower;
    double rightPower;

    //The following two constructors are called when an instance of this class is created (aka in AutonomousRed or in AutonomousBlue)
    //Constructor for MoveStraight that takes revs as a double
    public MoveStraight(AutonomousOpMode opmode, double revs, double power) {
        //Calls AutoAction constructor
        super(opmode);

        //Sets some of the variables in this class equal to constraints of the autonomous command and sets almostEnd to false as the command isn't near completion
        this.revs = revs;
        this.leftPower = power;
        this.rightPower = power;
        almostEnd = false;
    }
    
    //Constructor for MoveStraight that takes revs as a String representing the variable name to obtain from the opmode on init()
    public MoveStraight(AutonomousOpMode opmode, String revs, double power) {
        //Calls AutoAction constructor
        super(opmode);

        //Sets some of the variables in this class equal to constraints of the command in AutonomousBlue and sets almostEnd to false as the command isn't near completion
        this.revsName = revs;
        this.leftPower = power;
        this.rightPower = power;
        almostEnd = false;
    }

    @Override
    public void init() {

        //Calls the init() method from the superclass, AutoAction
        super.init();
        
        // If this action was created with a variable name for revs, use it to get the variable from opmode now
        if (revsName != null) {
            revs = opmode.getDataDouble(revsName);
        }
        
        //Resets the encoders and has them run to the position determined by revs
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);
        opmode.setDriveMotorsPosition((int) (revs * REVS_MULTIPLIER));

        //Gets the current heading of the robot and sets it equal to startAngle - this is used later
        startAngle = opmode.getHeading();
    }

    @Override
    public void update() {

        //Creates a variable that can give the robot gradual acceleration or deceleration depending on how complete with it's movement it is, allowing it to move more precisely
        double powerMult = Math.min(Math.max(.2, opmode.motorDriveLeftFront.getCurrentPosition() / 50.), Math.min(Math.max(.2, (revs * REVS_MULTIPLIER - opmode.motorDriveLeftFront.getCurrentPosition()) / 50.), 1));

        //Sets the power of each motor based off the given variables in the command and the acceleration factor
        opmode.motorDriveRightBack.setPower(rightPower * powerMult);
        opmode.motorDriveLeftBack.setPower(leftPower * powerMult);
        opmode.motorDriveRightFront.setPower(rightPower * powerMult);
        opmode.motorDriveLeftFront.setPower(leftPower * powerMult);

        //Calls the update() method from the superclass, AutoAction
        super.update();

        //Uses a method from BaseOpMode to tell if any of the drive motors are still trying to reach their intended destinations
        opmode.telemetry.addData("Busy motors", opmode.getDriveMotorsBusy());

        //Prints almostEndTime in telemetry once it has a value
        opmode.telemetry.addData("almost end time", almostEndTime);

        //Determines how much time has passed since the command started
        opmode.telemetry.addData("time", System.nanoTime() / 1000000000);

        //Sets almostEnd to true if any drive motors are no longer busy and sets almostEndTime equal to the current run time of the program in seconds
        if (opmode.getDriveMotorsBusy().size() < 4 && !almostEnd) {
            almostEnd = true;
            almostEndTime = System.nanoTime() / 1000000000;
        }

        //Tells the program it's done moving straight if more than a second has passed since almostEnd was set to true - this is meant to prevent the robot from getting stuck in an infinite loop if one wheel gets stuck
        if (almostEnd && System.nanoTime() / 1000000000 - almostEndTime > 1) {
            done = true;
        }

        //Or tells the program it's done moving straight if the drive motors have stopped
        if (!opmode.driveMotorsBusy()) {
            done = true;
        }

        //Sets up a variable for adjusting the robot's heading
        double adjustVal = 0.001;

        //If the robot is tilted right compared to its startAngle, readjusts the robot with the adjustVal until the current heading matches the original heading
        if ((opmode.getHeading() - startAngle) % 360 > 180 && (opmode.getHeading() - startAngle) % 360 < 359) {
            opmode.telemetry.addData("adjust", "left");
            if (leftPower > 0) {
                leftPower *= (1 - adjustVal);
                rightPower *= (1 + adjustVal);
            } else {
                leftPower *= (1 + adjustVal);
                rightPower *= (1 - adjustVal);
            }
        }

        //If the robot is tilted right compared to its startAngle, readjusts the robot with the adjustVal until the current heading matches the original heading
        else if ((opmode.getHeading() - startAngle) % 360 < 180 && (opmode.getHeading() - startAngle) % 360 > 1) {
            opmode.telemetry.addData("adjust", "right");
            if (leftPower > 0) {
                leftPower *= (1 + adjustVal);
                rightPower *= (1 - adjustVal);
            } else {
                leftPower *= (1 - adjustVal);
                rightPower *= (1 + adjustVal);
            }
        }

        //Makes no adjustments to the robot if it's aligned with it's original heading
        else {
            opmode.telemetry.addData("adjust", "none");
        }
    }

    @Override
    public void end() {
        //Calls the end() method from the superclass, AutoAction
        super.end();

        //Resets the drive motor encoders and sets their power to 0
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opmode.setDriveMotorsPower(0);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
