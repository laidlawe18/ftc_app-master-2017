package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by BHS-Lab on 11/20/2017.
 */

@Autonomous(name="AutonomousBlue")
public class AutonomousBlue extends AutonomousOpMode {

    @Override
    public void init() {

        //Calls the init() method from the superclass, AutonomousOpMode
        super.init();

        //A list of easily editable commands which control the robot during the autonomous portion
        //The command MoveStraight contains the constraints (
        commands.add(new GetPictograph(this));
        commands.add(new MoveStraight(this, 0.8f, 0.3f));
        commands.add(new MoveStraight(this, -1.1f, 0.7f));
        commands.add(new GyroTurn(this, 0, 0.32));
        commands.add(new MoveStraight(this, -2, .5f));
        commands.add(new MoveStraight(this, .5f, .4f));
        commands.add(new MoveStraight(this, -1.0f, .7f));
        commands.add(new GyroTurn(this, 270, .32));
        commands.add(new MoveStraight(this, -1.5, .7f));
    }

    @Override
    public void loop() {

        //Calls the loop() method from the superclass, AutonomousOpMode
        super.loop();
        if (this.data.containsKey("column")) {
            telemetry.addData("column", getDataString("column"));
            //telemetry.addData("blue", getDataInt("blue"));
        }
    }

}
