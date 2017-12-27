package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutoAction;

/**
 * Created by BHS-Lab on 11/17/2017.
 */

public class MoveStraight extends AutoAction {

    public static final int REVS_MULTIPLIER = 1440;

    double revs;
    boolean almostEnd;
    double almostEndTime;
    double startAngle;
    double leftPower;
    double rightPower;

    public MoveStraight(AutonomousOpMode opmode, double revs, double power) {
        super(opmode);
        this.revs = revs;
        this.leftPower = power;
        this.rightPower = power;
        almostEnd = false;
    }

    @Override
    public void init() {
        super.init();
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);
        opmode.setDriveMotorsPosition((int) (revs * REVS_MULTIPLIER));
        startAngle = opmode.getHeading();
    }

    @Override
    public void update() {
        double powerMult = Math.min(Math.max(.2, opmode.motorDriveLeftFront.getCurrentPosition() / 50.), Math.min(Math.max(.2, (revs * REVS_MULTIPLIER - opmode.motorDriveLeftFront.getCurrentPosition()) / 50.), 1));
        opmode.motorDriveRightBack.setPower(rightPower * powerMult);
        opmode.motorDriveLeftBack.setPower(leftPower * powerMult);
        opmode.motorDriveRightFront.setPower(rightPower * powerMult);
        opmode.motorDriveLeftFront.setPower(leftPower * powerMult);
        super.update();
        opmode.telemetry.addData("Busy motors", opmode.getDriveMotorsBusy());
        opmode.telemetry.addData("almost end time", almostEndTime);
        opmode.telemetry.addData("time", System.nanoTime() / 1000000000);
        if (opmode.getDriveMotorsBusy().size() < 4 && !almostEnd) {
            almostEnd = true;
            almostEndTime = System.nanoTime() / 1000000000;
        }
        if (almostEnd && System.nanoTime() / 1000000000 - almostEndTime > 1) {
            done = true;
        }
        if (!opmode.driveMotorsBusy()) {
            done = true;
        }
        
        double adjustVal = 0.001;
        
        if ((opmode.getHeading() - startAngle) % 360 > 180 && (opmode.getHeading() - startAngle) % 360 < 359) {
            opmode.telemetry.addData("adjust", "left");
            if (leftPower > 0) {
                leftPower *= (1 - adjustVal);
                rightPower *= (1 + adjustVal);
            } else {
                leftPower *= (1 + adjustVal);
                rightPower *= (1 - adjustVal);
            }
        } else if ((opmode.getHeading() - startAngle) % 360 < 180 && (opmode.getHeading() - startAngle) % 360 > 1) {
            opmode.telemetry.addData("adjust", "right");
            if (leftPower > 0) {
                leftPower *= (1 + adjustVal);
                rightPower *= (1 - adjustVal);
            } else {
                leftPower *= (1 - adjustVal);
                rightPower *= (1 + adjustVal);
            }
        } else {
            opmode.telemetry.addData("adjust", "none");
        }
    }

    @Override
    public void end() {
        super.end();
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsPower(0);
    }
}
