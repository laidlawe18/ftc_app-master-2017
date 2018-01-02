package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/3/2017.
 */

//Sets up a number of methods that will be used by several daughter classes
public class AutoAction {

    //****
    public boolean done;

    //Creates an instance of AutonomousOpMode in every subclass of AutoAction
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

    //****
    public boolean isDone() {
        return done;
    }

}
