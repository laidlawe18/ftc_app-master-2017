package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

/**
 * Created by BHS-Lab on 10/13/2017.
 */

@TeleOp(name="DriverControlled")
public class DriverControlled extends BaseOpMode {

    // Power multiplier for the drive motors
    double power = .5;

    double powerLeftFront = 1.0;
    double powerRightFront = 1.0;
    double powerLeftBack = 1.0;
    double powerRightBack = 1.0;

    double lastAngle;

    //Counter variables for the servos
    double servoStopCounter = 3.0;
    double servoJewelCounter = 3.0;
    double servoRelicCounter = 3.0;
    double servoRelicExtendCounter = 3.0;

    // Starting angle of the gyro sensor to use as a baseline
    double gyroZero;


    ArrayList<Double> lastFiveAngles;

    @Override
    public void init() {
        super.init();
        // Reset motor encoders and set to run using encoders
        setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("y", gamepad1.right_stick_y);
        telemetry.addData("x", gamepad1.right_stick_x);
        telemetry.addData("power", power);
        telemetry.addData("servoStopLeft position", servoStopLeft.getPosition());
        telemetry.addData("servoStopRight position", servoStopRight.getPosition());

        if (gamepad1.y && power < 1) {
            power += .003;
        } else if (gamepad1.a && power > 0) {
            power -= .003;
        }

        telemetry.addData("left front power", powerLeftFront);
        telemetry.addData("left back power", powerLeftBack);
        telemetry.addData("right front power", powerRightFront);
        telemetry.addData("right back power", powerRightBack);
        double lfPower = gamepad1.right_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x;
        double rbPower = gamepad1.right_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x;
        double lbPower = -gamepad1.right_stick_x + gamepad1.right_stick_y - gamepad1.left_stick_x;
        double rfPower = -gamepad1.right_stick_x + gamepad1.right_stick_y + gamepad1.left_stick_x;
        if (gamepad1.left_stick_x == 0){
            double gyroDiff = (getHeading() - lastAngle) % 360.0d;
            if (gyroDiff < 10) {
                if (lfPower < 0) {
                    powerLeftFront *= Math.pow(1.01, gyroDiff);
                } else if (lfPower > 0) {
                    powerLeftFront *= Math.pow(.99, gyroDiff);
                }
                if (lbPower < 0) {
                    powerLeftBack *= Math.pow(1.01, gyroDiff);
                } else if (lbPower > 0) {
                    powerLeftBack *= Math.pow(.99, gyroDiff);
                }
                if (rfPower < 0) {
                    powerRightFront *= Math.pow(.99, gyroDiff);
                } else if (rfPower > 0) {
                    powerRightFront *= Math.pow(1.01, gyroDiff);
                }
                if (rbPower < 0) {
                    powerRightBack *= Math.pow(.99, gyroDiff);
                } else if (rbPower > 0) {
                    powerRightBack *= Math.pow(1.01, gyroDiff);
                }
            } else if (gyroDiff > 350) {
                if (lfPower < 0) {
                    powerLeftFront *= Math.pow(.99, 360 - gyroDiff);
                } else if (lfPower > 0) {
                    powerLeftFront *= Math.pow(1.01, 360 - gyroDiff);
                }
                if (lbPower < 0) {
                    powerLeftBack *= Math.pow(.99, 360 - gyroDiff);
                } else if (lbPower > 0) {
                    powerLeftBack *= Math.pow(1.01, 360 - gyroDiff);
                }
                if (rfPower < 0) {
                    powerRightFront *= Math.pow(1.01, 360 - gyroDiff);
                } else if (rfPower > 0) {
                    powerRightFront *= Math.pow(.99, 360 - gyroDiff);
                }
                if (rbPower < 0) {
                    powerRightBack *= Math.pow(1.01, 360 - gyroDiff);
                } else if (rbPower > 0) {
                    powerRightBack *= Math.pow(.99, 360 - gyroDiff);
                }
            }
        }

        motorDriveLeftFront.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, lfPower * powerLeftFront, power));
        //motorDriveRightBack.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, rbPower * powerRightBack, power));
        //motorDriveLeftBack.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, lbPower * powerLeftBack, power));
        motorDriveRightFront.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, rfPower * powerRightFront, power));

        if (gamepad1.b) {
            motorLiftLeft.setPower(-1);
            motorLiftRight.setPower(-1);
        } else if (gamepad1.x) {
            motorLiftLeft.setPower(1);
            motorLiftRight.setPower(1);
        } else {
            if (gamepad1.dpad_left) {
                motorLiftLeft.setPower(-1);
            } else if (gamepad1.dpad_right) {
                motorLiftLeft.setPower(1);
            } else {
                motorLiftLeft.setPower(0);
            }
            if (gamepad1.dpad_down) {
                motorLiftRight.setPower(-1);
            } else if (gamepad1.dpad_up) {
                motorLiftRight.setPower(1);
            } else {
                motorLiftRight.setPower(0);
            }
        }


        if (gamepad1.start && servoStopCounter == 3.0){
            servoStopCounter = 2.0;
            servoStopLeft.setPosition(servoStopLeftPos1);
            servoStopRight.setPosition(servoStopRightPos1);
        }
        if (!gamepad1.start && servoStopCounter == 2.0){
            servoStopCounter = 1.0;
        }
        if (gamepad1.start && servoStopCounter == 1.0){
            servoStopCounter = 0.0;
            servoStopLeft.setPosition(servoStopLeftPos2);
            servoStopRight.setPosition(servoStopRightPos2);
        }
        if (!gamepad1.start && servoStopCounter == 0.0){
            servoStopCounter = 3.0;
        }


        if (gamepad1.back && servoJewelCounter == 3.0) {
            servoJewel.setPosition(servoJewelPos1);
            servoJewelCounter = 2.0;
        }
        if (!gamepad1.back && servoJewelCounter == 2.0){
            servoJewelCounter = 1.0;
        }
        if (gamepad1.back && servoJewelCounter == 1.0){
            servoJewelCounter = 0.0;
            servoJewel.setPosition (servoJewelPos2);
        }
        if (!gamepad1.back && servoJewelCounter == 0.0 ){
            servoJewelCounter = 3.0;
        }

        if (gamepad1.left_bumper && servoRelicExtendCounter == 3.0) {
            servoRelicExtend.setPosition(servoRelicExtendPos1);
            servoRelicExtendCounter = 2.0;
        }
        if (!gamepad1.left_bumper && servoRelicExtendCounter == 2.0) {
            servoRelicExtendCounter = 1.0;
        }
        if (gamepad1.left_bumper && servoRelicExtendCounter == 1.0) {
            servoRelicExtendCounter = 0.0;
            servoRelicExtend.setPosition (servoRelicExtendPos2);
        }
        if (!gamepad1.left_bumper && servoRelicExtendCounter == 0.0) {
            servoRelicExtendCounter = 3.0;
        }

        if (gamepad1.left_trigger > 0 && servoRelicCounter == 3.0) {
            servoRelic.setPosition(servoRelicPos1);
            servoRelicCounter = 2.0;
        }
        if (gamepad1.left_trigger < .01 && servoRelicCounter == 2.0){
            servoRelicCounter = 1.0;
        }
        if (gamepad1.left_trigger > 0 && servoRelicCounter == 1.0){
            servoRelicCounter = 0.0;
            servoRelic.setPosition (servoRelicPos2);
        }
        if (gamepad1.left_trigger < .01 && servoRelicCounter == 0.0 ){
            servoRelicCounter = 3.0;
        }



        lastAngle = getHeading();

        if (gamepad1.right_bumper) {
            motorDriveLeftBack.setPower(1);
            motorDriveRightBack.setPower(-1);
        } else if (gamepad1.right_trigger > 0) {
            motorDriveLeftBack.setPower(-1);
            motorDriveRightBack.setPower(1);
        } else {
            motorDriveLeftBack.setPower(0);
            motorDriveRightBack.setPower(0);
        }
        /*
        * Debugging code for checking which motor and what direction is being influenced by each control
        motorDriveLeftFront.setPower(gamepad1.left_stick_y);
        motorDriveLeftBack.setPower(gamepad1.left_stick_x);
        motorDriveRightFront.setPower(gamepad1.right_stick_y);
        motorDriveRightBack.setPower(gamepad1.right_stick_x);
        */
    }
    
    
    /**
     * Input: 
     *  leftXStick: left joystick x component
     *  xStick: right joystick x component
     *  yStick: right joystick y component
     *  val: unscaled power value for the motor
     *  power: global power scale
     *
     */
    private double scalePower(double leftXStick, double xStick, double yStick, double val, double power) {
        double mag = Math.abs(xStick) + Math.abs(yStick) + Math.abs(leftXStick);
        if (mag == 0) {
            return 0;
        }
        if (mag < 1) {
            return val * power;
        }
        return power * (val / mag);

    }


}

