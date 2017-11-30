package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by BHS-Lab on 11/20/2017.
 */

public class GyroTurn extends AutoAction {

    double desiredHeading;
    double power;

    public GyroTurn(AutonomousOpMode opmode, double desiredHeading, double power) {
        super(opmode);
        this.desiredHeading = desiredHeading;
        this.power = power;

    }

    @Override
    public void init() {
        double leftPower = power;
        double rightPower = -power;
        if ((desiredHeading - opmode.getHeading() + 360) % 360 > 180) {
            leftPower *= -1;
            rightPower *= -1;
        }

        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opmode.motorDriveLeftBack.setPower(leftPower);
        opmode.motorDriveLeftFront.setPower(leftPower);
        opmode.motorDriveRightBack.setPower(rightPower);
        opmode.motorDriveRightFront.setPower(rightPower);
    }

    @Override
    public void update() {
        if (Math.min((desiredHeading - opmode.getHeading() + 360) % 360, 360 - ((desiredHeading - opmode.getHeading() + 360) % 360)) < 10) {
            done = true;
        }

        double leftPower = power;
        double rightPower = -power;
        if ((desiredHeading - opmode.getHeading() + 360) % 360 > 180) {
            leftPower *= -1;
            rightPower *= -1;
        }

        opmode.motorDriveLeftBack.setPower(leftPower);
        opmode.motorDriveLeftFront.setPower(leftPower);
        opmode.motorDriveRightBack.setPower(rightPower);
        opmode.motorDriveRightFront.setPower(rightPower);
    }

    @Override
    public void end() {
        opmode.setDriveMotorsPower(0);
    }
}
