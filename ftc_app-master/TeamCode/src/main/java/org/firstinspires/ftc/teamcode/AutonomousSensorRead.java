package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by BHS-Lab on 1/31/2018.
 */

@Autonomous(name="AutonomousSensorRead")
public class AutonomousSensorRead extends AutonomousOpMode {

    @Override
    public void init() {
        super.init();

        commands.add(new ReadSensorValues(this, 30));
    }
}
