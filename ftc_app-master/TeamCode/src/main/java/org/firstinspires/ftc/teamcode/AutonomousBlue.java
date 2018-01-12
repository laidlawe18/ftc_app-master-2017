package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by BHS-Lab on 11/20/2017.
 */

//Makes this class an autonomous program which is visible in the FTC Driver Station app
@Autonomous(name="AutonomousBlue")

//Makes this class a subclass of AutonomousOpMode
public class AutonomousBlue extends AutonomousOpMode {

    @Override
    public void init() {

        //Calls the init() method from the superclass, AutonomousOpMode
        super.init();

        //A list of easily editable commands which control the robot during the autonomous portion
        //The command MoveStraight contains the constraints (
        commands.add(new GetPictograph(this));
        //commands.add(new MoveStraight(this, 0.8f, 0.3f));
        //commands.add(new MoveStraight(this, -1.1f, 0.7f));
        //commands.add(new GyroTurn(this, 0, 0.32));
        commands.add(new MoveStraight(this, -2.2, 1.2f));
        commands.add(new GyroTurn(this, 295, .3));
        commands.add(new MoveStraight(this, -2.3, 1.5f));
        commands.add(new GyroTurn(this, 182, .3));
        commands.add(new GetPositionWithLines(this, -0.08f));
        commands.add(new MoveStraight(this, "horizontalDisplacement", -0.8f));
        commands.add(new MoveForRelic(this, -0.4f, -.95f, -1.5f, 1.2f));
        commands.add(new GyroTurn(this, 270, .3));
        commands.add(new MoveStraight(this, "depth", 1f));
        commands.add(new Belts(this, .7));
    }

    @Override
    public void loop() {

        //Calls the loop() method from the superclass, AutonomousOpMode
        super.loop();

        //****
        if (this.data.containsKey("column")) {
            telemetry.addData("column", getDataString("column"));
            //telemetry.addData("blue", getDataInt("blue"));
        }
    }

}
