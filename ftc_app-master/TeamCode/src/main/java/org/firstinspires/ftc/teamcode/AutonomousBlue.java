package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by BHS-Lab on 11/20/2017.
 */

@Autonomous(name="AutonomousBlue")
public class AutonomousBlue extends AutonomousOpMode {

    @Override
    public void init() {
        super.init();
        commands.add(new MoveStraight(this, 2.0f, 0.5f));
        commands.add(new GyroTurn(this, 180, 0.3));
        commands.add(new MoveStraight(this, 2.0f, 0.5f));
    }

    @Override
    public void loop() {
        super.loop();
    }

}
