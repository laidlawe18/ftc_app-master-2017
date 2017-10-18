package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by BHS-Lab on 10/13/2017.
 */

@TeleOp(name="DriverControlled")
public class DriverControlled extends OpMode {

    DcMotor motorDriveLeftFront;
    DcMotor motorDriveRightFront;
    DcMotor motorDriveLeftBack;
    DcMotor motorDriveRightBack;

    @Override
    public void init() {
        motorDriveLeftFront = hardwareMap.dcMotor.get("motor_drive_left_front");
        motorDriveRightFront = hardwareMap.dcMotor.get("motor_drive_right_front");
        motorDriveLeftBack = hardwareMap.dcMotor.get("motor_drive_left_back");
        motorDriveRightBack = hardwareMap.dcMotor.get("motor_drive_right_back");
    }

    @Override
    public void loop() {

        motorDriveLeftFront.setPower(gamepad1.right_stick_y - gamepad1.right_stick_x);
        motorDriveRightBack.setPower(gamepad1.right_stick_y - gamepad1.right_stick_x);
        motorDriveLeftBack.setPower(gamepad1.right_stick_x + gamepad1.right_stick_y);
        motorDriveRightFront.setPower(gamepad1.right_stick_x + gamepad1.right_stick_y);
    }
}
