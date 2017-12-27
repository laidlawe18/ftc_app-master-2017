package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by BHS-Lab on 11/20/2017.
 */

public class GyroTurn extends AutoAction {

    double desiredHeading;
    double power;
    double lastAngle;
    double powerMult;
    double lastSampleTime;

    public GyroTurn(AutonomousOpMode opmode, double desiredHeading, double power) {
        super(opmode);
        this.desiredHeading = desiredHeading;
        this.power = power;
        this.lastAngle = opmode.getHeading();
        this.powerMult = 1;
    }

    @Override
    public void init() {
        double leftPower = power;
        double rightPower = -power;
        if ((desiredHeading - opmode.getHeading() + 360) % 360 > 180) {
            leftPower *= -1;
            rightPower *= -1;
        }

        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opmode.motorDriveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        opmode.motorDriveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        opmode.motorDriveLeftBack.setPower(leftPower);
        //opmode.motorDriveLeftFront.setPower(leftPower);
        opmode.motorDriveRightBack.setPower(rightPower);
        //opmode.motorDriveRightFront.setPower(rightPower);
        lastSampleTime = System.nanoTime() / 1000000000.0;
    }

    @Override
    public void update() {
        if (Math.min((desiredHeading - opmode.getHeading() + 360) % 360, (opmode.getHeading() - desiredHeading + 360) % 360) < 3) {
            done = true;
        }


        opmode.telemetry.addData("angle diff", Math.abs((opmode.getHeading() - lastAngle) % 360));

        double leftPower = power;
        double rightPower = -power;
        double angleDiff = Math.min((desiredHeading - opmode.getHeading() + 360) % 360, (opmode.getHeading() - desiredHeading + 360) % 360);
        opmode.telemetry.addData("Last angle", angleDiff);
        if (angleDiff < 30) {
            leftPower = ((Math.max(angleDiff - 10, 0) / 30) * leftPower + leftPower * .33) * powerMult;
            rightPower = ((Math.max(angleDiff - 10, 0) / 30) * rightPower + rightPower * .33) * powerMult;
        }
        if ((desiredHeading - opmode.getHeading() + 360) % 360 > 180) {
            leftPower *= -1;
            rightPower *= -1;
        }

        opmode.motorDriveLeftBack.setPower(leftPower);
        //opmode.motorDriveLeftFront.setPower(leftPower);
        opmode.motorDriveRightBack.setPower(rightPower);
        //opmode.motorDriveRightFront.setPower(rightPower);
        if (System.nanoTime() / 1000000000.0 - lastSampleTime > 0.25) {
            if (Math.min((lastAngle - opmode.getHeading() + 360) % 360, (opmode.getHeading() - lastAngle + 360) % 360) == 0) {
                powerMult *= 1.5;
            }

            if (Math.min((lastAngle - opmode.getHeading() + 360) % 360, (opmode.getHeading() - lastAngle + 360) % 360) > 5 && powerMult > 1) {
                powerMult /= 1.5;
            }
            lastAngle = opmode.getHeading();
            lastSampleTime = System.nanoTime() / 1000000000.0;
        }

    }

    @Override
    public void end() {
        opmode.motorDriveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        opmode.motorDriveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        opmode.setDriveMotorsPower(0);
    }
}
