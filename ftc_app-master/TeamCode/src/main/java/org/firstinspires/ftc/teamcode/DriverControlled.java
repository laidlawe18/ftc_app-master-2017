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

    DcMotor motorDriveLeftFront;
    DcMotor motorDriveRightFront;
    DcMotor motorDriveLeftBack;
    DcMotor motorDriveRightBack;
    double power = .5;

    @Override
    public void init() {
        motorDriveLeftFront = hardwareMap.dcMotor.get("motor_drive_left_front");
        motorDriveRightFront = hardwareMap.dcMotor.get("motor_drive_right_front");
        motorDriveLeftBack = hardwareMap.dcMotor.get("motor_drive_left_back");
        motorDriveRightBack = hardwareMap.dcMotor.get("motor_drive_right_back");
        motorDriveRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        motorDriveRightBack.setDirection(DcMotorSimple.Direction.REVERSE);
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

        motorDriveLeftFront.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.right_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x, power));
        motorDriveRightBack.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.right_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x, power));
        motorDriveLeftBack.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, -gamepad1.right_stick_x + gamepad1.right_stick_y - gamepad1.left_stick_x, power));
        motorDriveRightFront.setPower(scalePower(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, -gamepad1.right_stick_x + gamepad1.right_stick_y + gamepad1.left_stick_x, power));

        /*
        motorDriveLeftFront.setPower(gamepad1.left_stick_y);
        motorDriveLeftBack.setPower(gamepad1.left_stick_x);
        motorDriveRightFront.setPower(gamepad1.right_stick_y);
        motorDriveRightBack.setPower(gamepad1.right_stick_x);
        */
    }

    private double scalePower(double leftXStick, double xStick, double yStick, double val, double power) {
        double mag = Math.abs(xStick) + Math.abs(yStick) + Math.abs(leftXStick);
        if (mag == 0) {
            return 0;
        }
        return power * (val / mag);

    }
}
