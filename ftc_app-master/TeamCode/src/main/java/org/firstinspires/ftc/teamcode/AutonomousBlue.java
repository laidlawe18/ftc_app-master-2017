package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by BHS-Lab on 11/20/2017.
 */

@Autonomous(name="AutonomousBlue")
public class AutonomousBlue extends AutonomousOpMode {

    @Override
    public void init() {

        //Calls the init() method from the superclass, BaseOpMode
        super.init();


        commands.add(new GetPictograph(this));
        commands.add(new MoveStraight(this, 0.8f, 0.3f));
        commands.add(new MoveStraight(this, -1.1f, 0.7f));
        commands.add(new GyroTurn(this, 0, 0.32));
        commands.add(new MoveStraight(this, -1.5, .4f));
    }

    @Override
    public void loop() {
        super.loop();
        if (this.data.containsKey("column")) {
            telemetry.addData("column", getDataString("column"));
            telemetry.addData("blue", getDataInt("blue"));
        }
    }

}
