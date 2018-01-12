package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by BHS-Lab on 11/20/2017.
 */

//Makes this class a subclass of AutoAction
public class GyroTurn extends AutoAction {

    //Creates a number of instance variables which will be used in this class
    double desiredHeading;
    double power;
    double lastAngle;
    double powerMult;
    double lastSampleTime;

    //****Links this class to AutonomousOpMode
    public GyroTurn(AutonomousOpMode opmode, double desiredHeading, double power) {
        super(opmode);

        //Sets some of the instance variables in this class equal to constraints of the command in AutonomousBlue,
        this.desiredHeading = desiredHeading;
        this.power = power;

        //Sets lastAngle in this class to the current heading, and powerMult to 1
        this.lastAngle = opmode.getHeading();
        this.powerMult = 1;
    }

    @Override
    public void init() {
        //Sets the left and right power variables to opposite values
        double leftPower = power;
        double rightPower = -power;

        //Changes which drive motors have + and - values depending on where the robot wants to turn to relative to it's current position in order to choose the most efficient path.
        if ((desiredHeading - opmode.getHeading() + 360) % 360 > 180) {
            leftPower *= -1;
            rightPower *= -1;
        }

        //Runs the drive motors without encoders
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Sets the front two drive motors to float, or not cause resistance to the robot's movement, when their power is 0
        opmode.motorDriveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        opmode.motorDriveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //Sets the powers of the back two drive motors
        opmode.motorDriveLeftBack.setPower(leftPower);
        //opmode.motorDriveLeftFront.setPower(leftPower);
        opmode.motorDriveRightBack.setPower(rightPower);
        //opmode.motorDriveRightFront.setPower(rightPower);

        //Sets lastSampleTime equal to the current run time of the program in seconds
        lastSampleTime = System.nanoTime() / 1000000000.0;
    }

    @Override
    public void update() {
        //Tells the program it's done turning if it's within 3 degrees of the desired heading
        if (Math.min((desiredHeading - opmode.getHeading() + 360) % 360, (opmode.getHeading() - desiredHeading + 360) % 360) < 3) {
            done = true;
        }

        //Prints the difference between the current heading and the heading of the robot at the beginning of the command
        opmode.telemetry.addData("angle diff", Math.abs((opmode.getHeading() - lastAngle) % 360));

        //Sets the left and right power variables to opposite values
        double leftPower = power;
        double rightPower = -power;

        //****
        double angleDiff = Math.min((desiredHeading - opmode.getHeading() + 360) % 360, (opmode.getHeading() - desiredHeading + 360) % 360);
        opmode.telemetry.addData("Last angle", angleDiff);
        if (angleDiff < 30) {
            leftPower = ((Math.max(angleDiff - 15, 0) / 45) * leftPower + leftPower * .33) * powerMult;
            rightPower = ((Math.max(angleDiff - 15, 0) / 45) * rightPower + rightPower * .33) * powerMult;
        }

        //Changes which drive motors have + and - values depending on where the robot wants to turn to relative to it's current position in order to choose the most efficient path.
        if ((desiredHeading - opmode.getHeading() + 360) % 360 > 180) {
            leftPower *= -1;
            rightPower *= -1;
        }

        //Sets the powers of the back two drive motors
        opmode.motorDriveLeftBack.setPower(leftPower);
        //opmode.motorDriveLeftFront.setPower(leftPower);
        opmode.motorDriveRightBack.setPower(rightPower);
        //opmode.motorDriveRightFront.setPower(rightPower);

        //Runs the following code if more than 0.25 seconds have elapsed since lastSampleTime was last updated
        if (System.nanoTime() / 1000000000.0 - lastSampleTime > 0.25) {

            //If the robot is stuck or virtually not moving, this increases its turning speed
            if (Math.min((lastAngle - opmode.getHeading() + 360) % 360, (opmode.getHeading() - lastAngle + 360) % 360) == 0) {
                powerMult *= 1.5;
            }

            //If the robot has moved more than 5 degrees and the powerMult factor is greater than one, then the robot's turning speed is decreased because it is moving too fast to be safely accurate
            if (Math.min((lastAngle - opmode.getHeading() + 360) % 360, (opmode.getHeading() - lastAngle + 360) % 360) > 5 && powerMult > 1) {
                powerMult /= 1.5;
            }

            //Resets these variables to the current heading and the current run time of the program respectively
            lastAngle = opmode.getHeading();
            lastSampleTime = System.nanoTime() / 1000000000.0;
        }

    }

    @Override
    public void end() {
        //Rapidly brakes both wheels with a power of 0 when the command is complete instead of letting them float - done to maintain a more accurate position
        opmode.motorDriveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        opmode.motorDriveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        opmode.setDriveMotorsPower(0);
    }
}
