package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/3/2018.
 */

//Makes this class a subclass of MoveStraight
public class MoveForRelic extends MoveStraight {

    //Creates variables for left, center, and right, which relate to the directions from the pictograph
    double l;
    double c;
    double r;

    //This constructor is called when an instance of this class is created (aka in AutonomousRed or in AutonomousBlue)
    public MoveForRelic(AutonomousOpMode opmode, double l, double c, double r, double power) {

        //Calls AutoAction constructor
        super(opmode, 0, power);

        //Sets the variables in this class equal to constraints in the autonomous command
        this.l = l;
        this.c = c;
        this.r = r;
    }

    @Override
    public void init() {

        //Sets the instance variable of revs equal to a certain amount depending on what is in the column in telemetry
        if (opmode.getDataString("column").equals("LEFT")) {
            this.revs = this.l;
        } else if (opmode.getDataString("column").equals("CENTER")) {
            this.revs = this.c;
        } else {
            this.revs = this.r;
        }

        //Calls the init() method from the superclass, AutoAction
        super.init();
    }

}
