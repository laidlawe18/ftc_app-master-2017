package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by BHS-Lab on 1/8/2018.
 */

public class GetPositionWithLines extends AutoAction {

    double power;
    int state;
    int readings;
    double blackVal;

    public GetPositionWithLines(AutonomousOpMode opmode, double power) {
        super(opmode);
        this.power = power;
    }

    @Override
    public void init() {
        super.init();
        opmode.setDriveMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opmode.setDriveMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);
        opmode.setDriveMotorsPosition((int) (.2 * MoveStraight.REVS_MULTIPLIER));
        state = 1;
        readings = 0;
    }

    @Override
    public void update() {
        if (state == 1) {
            blackVal = (blackVal * readings + opmode.getLightVal()) / (readings + 1);
        }
    }

    @Override
    public void end() {

    }
}
