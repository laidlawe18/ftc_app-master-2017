package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by BHS-Lab on 10/13/2017.
 */

@TeleOp(name="DriverControlled")
public class DriverControlled extends OpMode {

    // Drive motors
    DcMotor motorDriveLeftFront;
    DcMotor motorDriveRightFront;
    DcMotor motorDriveLeftBack;
    DcMotor motorDriveRightBack;
    DcMotor motorLiftLeft;
    DcMotor motorLiftRight;
    
    // Power multiplier for the drive motors
    double power = .5;

    @Override
    public void init() {
        
        // Get drive motors from hardware map
        motorDriveLeftFront = hardwareMap.dcMotor.get("motor_drive_left_front");
        motorDriveRightFront = hardwareMap.dcMotor.get("motor_drive_right_front");
        motorDriveLeftBack = hardwareMap.dcMotor.get("motor_drive_left_back");
        motorDriveRightBack = hardwareMap.dcMotor.get("motor_drive_right_back");
        motorLiftLeft = hardwareMap.dcMotor.get("motor_lift_left")
        motorLiftRight = hardwareMap.dcMotor.get("motor_lift_right")
        
        // Reverse directions of backwards motors
        motorDriveRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        motorDriveRightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        
        // Reset motor encoders and set to run using encoders
        setDriveMotorsMode(DcMotorSimple.RunMode.STOP_AND_RESET_ENCODER)
        setDriveMotorsMode(DcMotorSimple.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("y", gamepad1.right_stick_y);
        telemetry.addData("x", gamepad1.right_stick_x);
        telemetry.addData("power", power);

        if (gamepad1.y && power < 1) {
            power += .003;
        } else if (gamepad1.a && power > 0) {
            power -= .003;
        }
        double lfPower = gamepad1.right_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x;
        motorDriveLeftFront.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, lfPower, power));
        motorDriveRightBack.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.right_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x, power));
        motorDriveLeftBack.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, -gamepad1.right_stick_x + gamepad1.right_stick_y - gamepad1.left_stick_x, power));
        motorDriveRightFront.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, -gamepad1.right_stick_x + gamepad1.right_stick_y + gamepad1.left_stick_x, power));

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
        return power * (val / mag);

    }
    
    private void setDriveMotorsMode(DcMotorSimple.RunMode mode) {
        motorDriveLeftFront.setMode(mode);
        motorDriveRightFront.setMode(mode);
        motorDriveLeftBack.setMode(mode);
        motorDriveRightBack.setMode(mode);
    }
}
