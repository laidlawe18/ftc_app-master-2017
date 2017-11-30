package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutoAction;

/**
 * Created by BHS-Lab on 11/17/2017.
 */

public class MoveStraight extends AutoAction {

    public static final int REVS_MULTIPLIER = 1440;

    double revs;
    double power;

    public MoveStraight(AutonomousOpMode opmode, double revs, double power) {
        super(opmode);
        this.revs = revs;
        this.power = power;
    }

    @Override
    public void init() {
        super.init();
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);
        opmode.setDriveMotorsPosition((int) (revs * REVS_MULTIPLIER));
        opmode.setDriveMotorsPower(0.5f);
    }

    @Override
    public void update() {
        super.update();
        opmode.telemetry.addData("Busy motors", opmode.getDriveMotorsBusy());
        if (!opmode.driveMotorsBusy()) {
            done = true;
        }
    }

    @Override
    public void end() {
        super.end();
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsPower(0);
    }
}
