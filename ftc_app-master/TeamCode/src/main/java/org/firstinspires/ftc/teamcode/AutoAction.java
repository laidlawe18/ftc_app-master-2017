package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/3/2017.
 */

//Sets up a number of methods that will be used by several daughter classes
public class AutoAction {

    //Used to tell the robot when to stop
    public boolean done;
    public AutonomousOpMode opmode;

    public AutoAction(AutonomousOpMode opmode) {
        this.opmode = opmode;
    }

    //Runs once when command line brings autonomous to this class
    public void init() {

    }

    //Constantly loops to see if the statements are true
    public void update() {

    }

    //Runs once the rest of the motors have moved the appropriate distances
    public void end() {

    }

    //Used to tell the robot that it's done
    public boolean isDone() {
        return done;
    }

}
