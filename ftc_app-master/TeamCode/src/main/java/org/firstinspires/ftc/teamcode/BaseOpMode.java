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

    //****Creates a variable for the gyrosensor's current
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

        //
        double[] motorPowers = {motorDriveLeftFront.getPower(), motorDriveRightFront.getPower(), motorDriveLeftBack.getPower(), motorDriveRightBack.getPower()};

        //Displays variable values on phone log, so we can track what's happening to them if needed
        telemetry.addData("Motor powers", Arrays.toString(motorPowers));
        telemetry.addData("Angle", getHeading());
    }

    //The following methods are set up here so that they can be accessed in our autonomous program

    //Sets the encoder mode for each of the wheel motors
    public void setDriveMotorsMode(DcMotor.RunMode mode) {
        motorDriveLeftFront.setMode(mode);
        motorDriveRightFront.setMode(mode);
        motorDriveLeftBack.setMode(mode);
        motorDriveRightBack.setMode(mode);
    }

    //Tells all four wheels to drive forward until their encoders reach a certain position
    public void setDriveMotorsPosition(int pos) {
        motorDriveLeftFront.setTargetPosition(pos);
        motorDriveRightFront.setTargetPosition(pos);
        motorDriveLeftBack.setTargetPosition(pos);
        motorDriveRightBack.setTargetPosition(pos);
    }

    //Sets the power on each wheel motor to the same certain value
    public void setDriveMotorsPower(double power) {
        motorDriveLeftFront.setPower(power);
        motorDriveRightFront.setPower(power);
        motorDriveLeftBack.setPower(power);
        motorDriveRightBack.setPower(power);
    }

    //****Returns which drive motors are still in the process of moving to their projected position
    public boolean driveMotorsBusy() {
        return (motorDriveLeftFront.isBusy() || motorDriveLeftBack.isBusy() || motorDriveRightFront.isBusy() || motorDriveRightBack.isBusy());
    }

    //****Adds which motors are busy to an array
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

    //returns the current gyro heading
    public double getHeading() {
        return (gyroSensor.getHeading() - gyroZero) % 360d;
    }

}
