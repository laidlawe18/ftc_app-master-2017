package org.firstinspires.ftc.teamcode;

//This imports the method we need to run the program
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by BHS-Lab on 11/20/2017.
 */

//Makes this class an autonomous program which is visible in the FTC Driver Station app
@Autonomous(name="AutonomousRed")

//Makes this class a subclass of AutonomousOpMode
public class AutonomousRed extends AutonomousOpMode {

    @Override
    public void init() {

        //Calls the init() method from the superclass, AutonomousOpMode
        super.init();

        //A list of easily editable commands with constraints which control the robot during the autonomous portion - this is done by creating an instance of each class (ie MoveStraight) in this opmode
        //The meaning of each constraint is found in the specific class the command refers to
        //A specific analysis of what these commands tell the robot to do during autonomous can be found in the Code Summary
        commands.add(new GetPictograph(this));
        commands.add(new MoveServo(this, this.servoJewel, BaseOpMode.servoJewelPos2));
        commands.add(new GetColor(this));
        commands.add(new Jewel(this, true));
        commands.add(new MoveStraight(this, "jewelRevs", 0.35f));
        commands.add(new MoveStraight(this, "negativeJewelRevs", 0.5f));
        commands.add(new MoveServo(this, this.servoJewel, BaseOpMode.servoJewelPos1));
        commands.add(new MoveStraight(this, 2.15, 0.35f));
        commands.add(new GyroTurn(this, 90, .55, motorDriveLeftFront, motorDriveRightFront));
        commands.add(new GetPositionWithLines(this, 0.13f, false));
        commands.add(new MoveStraight(this, "horizontalDisplacement", -0.8f));
        commands.add(new MoveForRelic(this, -0.25f, -.9f, -1.5f, 0.7f));
        commands.add(new GyroTurn(this, 180, .45));
        commands.add(new MoveStraight(this, -0.3, 0.6));
        commands.add(new SimultaneousAction(this, new Belts(this, .7), new MoveStraight(this, .5, 0.5f)));
        commands.add(new MoveStraight(this, -.5, 1.5));
        commands.add(new MoveStraight(this, .5, 1.5));

        initFirstCommand();
    }

}
