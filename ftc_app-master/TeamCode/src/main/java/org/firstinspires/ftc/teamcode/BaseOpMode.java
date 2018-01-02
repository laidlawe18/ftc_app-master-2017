package org.firstinspires.ftc.teamcode;

//This imports all of the methods we need to run the program
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by BHS-Lab on 11/17/2017.
 */

//Makes this class an opmode
public class BaseOpMode extends OpMode {

    //Creates variable names for all of the motors, servos, and sensors
    DcMotor motorDriveLeftFront;
    DcMotor motorDriveRightFront;
    DcMotor motorDriveLeftBack;
    DcMotor motorDriveRightBack;
    DcMotor motorLiftLeft;
    DcMotor motorLiftRight;
    DcMotor motorBeltLeft;
    DcMotor motorBeltRight;

    Servo servoStopLeft;
    Servo servoStopRight;
    Servo servoJewel;
    Servo servoRelic;
    Servo servoRelicExtend;

    GyroSensor gyroSensor;

    //Creates a variable for the gyro sensor's current heading/direction
    double gyroZero;

    //Gives variable names to the various servo positions we use for each servo in our code
    public static final double servoStopLeftPos1 = 0.43;
    public static final double servoStopLeftPos2 = 0.53;
    public static final double servoStopRightPos1 = 0.48;
    public static final double servoStopRightPos2 = 0.3;

    public static final double servoJewelPos1 = 0.5;
    public static final double servoJewelPos2 = 1.0;

    public static final double servoRelicPos1 = 0.5;
    public static final double servoRelicPos2 = 0.8;

    public static final double servoRelicExtendPos1 = 0.1;
    public static final double servoRelicExtendPos2 = 0.85;

    @Override
    public void init() {
        //Gets the motors, servos, and sensors from the hardware map
        motorDriveLeftFront = hardwareMap.dcMotor.get("motor_drive_left_front");
        motorDriveRightFront = hardwareMap.dcMotor.get("motor_drive_right_front");
        motorDriveLeftBack = hardwareMap.dcMotor.get("motor_drive_left_back");
        motorDriveRightBack = hardwareMap.dcMotor.get("motor_drive_right_back");
        motorLiftLeft = hardwareMap.dcMotor.get("motor_lift_left");
        motorLiftRight = hardwareMap.dcMotor.get("motor_lift_right");
        motorBeltLeft = hardwareMap.dcMotor.get("motor_belt_left");
        motorBeltRight = hardwareMap.dcMotor.get("motor_belt_right");

        servoStopLeft = hardwareMap.servo.get("servo_stop_left");
        servoStopRight = hardwareMap.servo.get("servo_stop_right");
        servoJewel = hardwareMap.servo.get ("servo_jewel");
        servoRelic = hardwareMap.servo.get("servo_relic");
        servoRelicExtend = hardwareMap.servo.get("servo_relic_extend");

        gyroSensor = hardwareMap.gyroSensor.get("sensor_gyro");

        //Gets the gyro sensor's current heading/direction
        gyroZero = gyroSensor.getHeading();

        //Reverses the directions of backwards motors (those on the right side)
        motorDriveRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        motorDriveRightBack.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {

        //Puts the current motor powers into an array for later use
        double[] motorPowers = {motorDriveLeftFront.getPower(), motorDriveRightFront.getPower(), motorDriveLeftBack.getPower(), motorDriveRightBack.getPower()};

        //Displays variable values on phone log, so we can track what's happening to them if needed
        telemetry.addData("Motor powers", Arrays.toString(motorPowers));
        telemetry.addData("Angle", getHeading());
    }

    //The following methods are set up here so that they can be accessed in our autonomous program

    //Sets the encoder mode for each of the drive motors
    public void setDriveMotorsMode(DcMotor.RunMode mode) {
        motorDriveLeftFront.setMode(mode);
        motorDriveRightFront.setMode(mode);
        motorDriveLeftBack.setMode(mode);
        motorDriveRightBack.setMode(mode);
    }

    //Tells all four wheels to drive until their encoders reach the same certain position
    public void setDriveMotorsPosition(int pos) {
        motorDriveLeftFront.setTargetPosition(pos);
        motorDriveRightFront.setTargetPosition(pos);
        motorDriveLeftBack.setTargetPosition(pos);
        motorDriveRightBack.setTargetPosition(pos);
    }

    //Sets the power on each drive motor to the same certain value
    public void setDriveMotorsPower(double power) {
        motorDriveLeftFront.setPower(power);
        motorDriveRightFront.setPower(power);
        motorDriveLeftBack.setPower(power);
        motorDriveRightBack.setPower(power);
    }

    //Determines if any of the drive motors are still in the process of turning so that their encoders are in the correct position - if they are, it returns true
    public boolean driveMotorsBusy() {
        return (motorDriveLeftFront.isBusy() || motorDriveLeftBack.isBusy() || motorDriveRightFront.isBusy() || motorDriveRightBack.isBusy());
    }

    //Adds which drive motors are busy turning so that their encoders are in the correct positions to a string array
    public ArrayList<String> getDriveMotorsBusy() {
        ArrayList<String> out = new ArrayList<String>();
        if (motorDriveLeftFront.isBusy()) {
            out.add("left front");
        }
        if (motorDriveRightFront.isBusy()) {
            out.add("right front");
        }
        if (motorDriveLeftBack.isBusy()) {
            out.add("left back");
        }
        if (motorDriveRightBack.isBusy()) {
            out.add("right back");
        }
        return out;
    }

    //Returns the current direction the robot is pointing in from the gyro sensor
    public double getHeading() {
        return (gyroSensor.getHeading() - gyroZero) % 360d;
    }

}
