package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

/**
 * Created by BHS-Lab on 11/17/2017.
 */

public class BaseOpMode extends OpMode {

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

    double gyroZero;

    public static final double servoStopLeftPos1 = 0.45;
    public static final double servoStopLeftPos2 = 0.51;

    public static final double servoStopRightPos1 = 0.6;
    public static final double servoStopRightPos2 = 0.54;

    public static final double servoJewelPos1 = 0.5;
    public static final double servoJewelPos2 = 1.0;

    public static final double servoRelicPos1 = 0.5;
    public static final double servoRelicPos2 = 0.8;

    public static final double servoRelicExtendPos1 = 0.0;
    public static final double servoRelicExtendPos2 = 0.7;

    @Override
    public void init() {
        // Get drive motors from hardware map
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


        gyroZero = gyroSensor.getHeading();

        // Reverse directions of backwards motors
        motorDriveRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        motorDriveRightBack.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {

    }

    public void setDriveMotorsMode(DcMotor.RunMode mode) {
        motorDriveLeftFront.setMode(mode);
        motorDriveRightFront.setMode(mode);
        motorDriveLeftBack.setMode(mode);
        motorDriveRightBack.setMode(mode);
    }

    public void setDriveMotorsPosition(int pos) {
        motorDriveLeftFront.setTargetPosition(pos);
        motorDriveRightFront.setTargetPosition(pos);
        motorDriveLeftBack.setTargetPosition(pos);
        motorDriveRightBack.setTargetPosition(pos);
    }

    public void setDriveMotorsPower(double power) {
        motorDriveLeftFront.setPower(power);
        motorDriveRightFront.setPower(power);
        motorDriveLeftBack.setPower(power);
        motorDriveRightBack.setPower(power);
    }

    public boolean driveMotorsBusy() {
        return (motorDriveLeftFront.isBusy() || motorDriveLeftBack.isBusy() || motorDriveRightFront.isBusy() || motorDriveRightBack.isBusy());
    }

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

    public double getHeading() {
        return (gyroSensor.getHeading() - gyroZero) % 360d;
    }

}
