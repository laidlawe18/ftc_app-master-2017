package org.firstinspires.ftc.teamcode;

//This imports all of the methods we need to run the program
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

//Sets up this file to be a teleop file
@TeleOp(name="DriverControlled")

//Allows us to easily control the robot inn the driving period - it's extends BaseOpMode, which means that this code will have access to the motors/servos/sensors defined in that class
public class DriverControlled extends BaseOpMode {

    // Power multiplier for the drive motors
    double power = .5;
    double powerLeftFront = 1.0;
    double powerRightFront = 1.0;
    double powerLeftBack = 1.0;
    double powerRightBack = 1.0;

    //
    double lastAngle;

    //Counter variables for the servos
    double servoStopCounter = 3.0;
    double servoJewelCounter = 3.0;
    double servoRelicCounter = 3.0;
    double servoRelicExtendCounter = 3.0;

    //
    ArrayList<Double> lastFiveAngles;

    //Runs once when driver hits INIT, but before they hit PLAY
    @Override
    public void init() {

        //Calls elements of the parent class
        super.init();

        // Reset motor encoders and set to run using encoders
        setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //Runs continuously after the driver hits PLAY
    @Override
    public void loop() {

        //Displays variable values on phone log, so we can track what's happening to them if needed
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
        double lfPower = stickMax(gamepad1.right_stick_y, gamepad2.right_stick_y) + stickMax(gamepad1.right_stick_x, gamepad2.right_stick_x) - 1.3 * stickMax(gamepad1.left_stick_x, gamepad2.left_stick_x);
        double rbPower = stickMax(gamepad1.right_stick_y, gamepad2.right_stick_y) + stickMax(gamepad1.right_stick_x, gamepad2.right_stick_x) + 1.3 * stickMax(gamepad1.left_stick_x, gamepad2.left_stick_x);
        double lbPower = stickMax(gamepad1.right_stick_y, gamepad2.right_stick_y) - stickMax(gamepad1.right_stick_x, gamepad2.right_stick_x) - 1.3 * stickMax(gamepad1.left_stick_x, gamepad2.left_stick_x);
        double rfPower = stickMax(gamepad1.right_stick_y, gamepad2.right_stick_y) - stickMax(gamepad1.right_stick_x, gamepad2.right_stick_x) + 1.3 * stickMax(gamepad1.left_stick_x, gamepad2.left_stick_x);
        if (gamepad2.left_stick_x == 0){
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


        motorDriveLeftFront.setPower(scalePower(gamepad2.left_stick_x, gamepad2.right_stick_x, gamepad2.right_stick_y, lfPower * powerLeftFront, power));
        motorDriveRightBack.setPower(scalePower(gamepad2.left_stick_x, gamepad2.right_stick_x, gamepad2.right_stick_y, rbPower * powerRightBack, power));
        motorDriveLeftBack.setPower(scalePower(gamepad2.left_stick_x, gamepad2.right_stick_x, gamepad2.right_stick_y, lbPower * powerLeftBack, power));
        motorDriveRightFront.setPower(scalePower(gamepad2.left_stick_x, gamepad2.right_stick_x, gamepad2.right_stick_y, rfPower * powerRightFront, power));


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


        //General Form:
        //If the button is pressed and the counter = 3, the servo changes position and the counter is set equal to 2
        //When the button is let go and the counter = 2, the counter is set equal to 1
        //If the button is pressed and the counter = 1, the servo changes to its original position and the counter is set equal to 0
        //When the button is let go and the counter = 0, the counter is set equal to 3

        //For releasing the sliders holding the conveyor belt
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

        //For lowering the arm to knock off the jewel
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

        //For extending or retracting the arm for relic
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

        //For opening and closing the claw for the relic
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
            motorBeltLeft.setPower(1);
            motorBeltRight.setPower(1);
        } else if (gamepad1.right_trigger > 0) {
            motorBeltLeft.setPower(-1);
            motorBeltRight.setPower(-1);
        } else {
            motorBeltLeft.setPower(0);
            motorBeltRight.setPower(0);
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

    //
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

    private double stickMax(double a, double b) {
        if (Math.abs(a) > Math.abs(b)) {
            return a;
        }
        return b;
    }


}

