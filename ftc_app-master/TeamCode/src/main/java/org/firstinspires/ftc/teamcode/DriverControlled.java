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

//Makes this file a TeleOp which is visible in the FTC Driver Station app
@TeleOp(name="DriverControlled")

//Makes this class a subclass of BaseOpMode, which means that this code will have access to the motors/servos/sensors defined in that class
public class DriverControlled extends BaseOpMode {

    //Power multiplier for the drive motors
    double power = .5;
    double powerLeftFront = 1.0;
    double powerRightFront = 1.0;
    double powerLeftBack = 1.0;
    double powerRightBack = 1.0;

    //Sets up what will be the current heading of the robot
    double lastAngle;

    //Sets up counter variables for the servos
    double servoStopCounter = 3.0;
    double servoJewelCounter = 3.0;
    double servoRelicCounter = 3.0;
    double servoRelicExtendCounter = 3.0;

    //Runs once when driver hits INIT, but before they hit PLAY
    @Override
    public void init() {

        //Calls the init() method from the superclass, BaseOpMode
        super.init();

        //Resets motor encoders and tells them to run without encoders
        setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //Runs continuously after the driver hits PLAY
    @Override
    public void loop() {

        //Displays variable values in telemetry (the phone's log), so we can track what's happening to them if needed
        telemetry.addData("y", gamepad1.right_stick_y);
        telemetry.addData("x", gamepad1.right_stick_x);
        telemetry.addData("power", power);
        telemetry.addData("servoStopLeft position", servoStopLeft.getPosition());
        telemetry.addData("servoStopRight position", servoStopRight.getPosition());

        //Sets up buttons y and a on gamepad 1 so that hitting y increases the power multiplier and hitting a decreases it
            if ((gamepad2.y || gamepad1.y) && power < 1) {
            power += .001;
        } else if ((gamepad1.a || gamepad2.a) && power > 0) {
            power -= .001;
        }

        //Displays the value of the power multipliers of the drive motors
        telemetry.addData("left front power", powerLeftFront);
        telemetry.addData("left back power", powerLeftBack);
        telemetry.addData("right front power", powerRightFront);
        telemetry.addData("right back power", powerRightBack);

        //Determines what the speed of each wheel should be based off of the joystick values and which one is greater (for the method stickMax, see below)
        double lfPower = stickMax(gamepad1.right_stick_y, gamepad2.right_stick_y) + stickMax(gamepad1.right_stick_x, gamepad2.right_stick_x) - 1.3 * stickMax(gamepad1.left_stick_x, gamepad2.left_stick_x);
        double rbPower = stickMax(gamepad1.right_stick_y, gamepad2.right_stick_y) + stickMax(gamepad1.right_stick_x, gamepad2.right_stick_x) + 1.3 * stickMax(gamepad1.left_stick_x, gamepad2.left_stick_x);
        double lbPower = stickMax(gamepad1.right_stick_y, gamepad2.right_stick_y) - stickMax(gamepad1.right_stick_x, gamepad2.right_stick_x) - 1.3 * stickMax(gamepad1.left_stick_x, gamepad2.left_stick_x);
        double rfPower = stickMax(gamepad1.right_stick_y, gamepad2.right_stick_y) - stickMax(gamepad1.right_stick_x, gamepad2.right_stick_x) + 1.3 * stickMax(gamepad1.left_stick_x, gamepad2.left_stick_x);

        //Sets gyroDiff to the difference between the last recorded heading and the current heading if the left stick of gamepad 2 isn't moving along the x-axis
        if (gamepad2.left_stick_x == 0){
            double gyroDiff = (getHeading() - lastAngle) % 360.0d;

            //Sets up a system that slows down or speeds up the turning speed of the motors depending on gyroDiffwhether their current powers aare negative or positive
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
            }
            //Does the opposite if the the difference is greater than 350 degrees
            else if (gyroDiff > 350) {
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

        //Sets the power of the drive motors depending on the output of the method scalePower (found below) based on the given parameters
        if (!gamepad2.left_bumper && !gamepad2.right_bumper) {
            motorDriveLeftFront.setPower(scalePower(gamepad2.left_stick_x, gamepad2.right_stick_x, gamepad2.right_stick_y, lfPower * powerLeftFront, power) * (gamepad2.left_trigger > 0 ? 2.5 : (gamepad2.right_trigger > 0 ? .5 : 1)));
            motorDriveRightBack.setPower(scalePower(gamepad2.left_stick_x, gamepad2.right_stick_x, gamepad2.right_stick_y, rbPower * powerRightBack, power) * (gamepad2.left_trigger > 0 ? 2.5 : (gamepad2.right_trigger > 0 ? .5 : 1)));
            motorDriveLeftBack.setPower(scalePower(gamepad2.left_stick_x, gamepad2.right_stick_x, gamepad2.right_stick_y, lbPower * powerLeftBack, power) * (gamepad2.left_trigger > 0 ? 2.5 : (gamepad2.right_trigger > 0 ? .5 : 1)));
            motorDriveRightFront.setPower(scalePower(gamepad2.left_stick_x, gamepad2.right_stick_x, gamepad2.right_stick_y, rfPower * powerRightFront, power) * (gamepad2.left_trigger > 0 ? 2.5 : (gamepad2.right_trigger > 0 ? .5 : 1)));
        }

        //Allows us to control the direction of the motors that lift the arm together with b (down) and x (up) or control them individually with the left motor controlled by left and right on the d-pad
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


        //General form for the following 4 algorithms:
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

        //Sets lastAngle equal to the current heading
        lastAngle = getHeading();


        //Using the belts, the robot sucks in the glyph is the bumper is pressed, spits them out if the trigger is pressed, and does nothing if neither is touched
        if (gamepad1.right_bumper) {
            motorBeltLeft.setPower(-1);
            motorBeltRight.setPower(-1);
        } else if (gamepad1.right_trigger > 0) {
            motorBeltLeft.setPower(1);
            motorBeltRight.setPower(1);
        } else {
            motorBeltLeft.setPower(0);
            motorBeltRight.setPower(0);
        }

        if (gamepad2.left_bumper) {
            motorDriveLeftFront.setPower(-power * 1.5);
            motorDriveLeftBack.setPower(power * .66);
            motorDriveRightFront.setPower(power * 1.5);
            motorDriveRightBack.setPower(-power * .66);
        } else if (gamepad2.right_bumper){
            motorDriveLeftFront.setPower(power * 1.5);
            motorDriveLeftBack.setPower(-power * .66);
            motorDriveRightFront.setPower(-power * 1.5);
            motorDriveRightBack.setPower(power * .66);
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
     *  Input:
     *  leftXStick: left joystick x component
     *  xStick: right joystick x component
     *  yStick: right joystick y component
     *  val: unscaled power value for the motor
     *  power: global power scale */

    //Sets up a method used above to determine the power of each drive motor
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

    //Sets up a method which returns the larger of two stick values
    private double stickMax(double a, double b) {
        if (Math.abs(a) > Math.abs(b)) {
            return a;
        }
        return b;
    }
}

