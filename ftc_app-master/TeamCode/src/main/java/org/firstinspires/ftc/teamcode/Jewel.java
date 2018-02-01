package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/29/2018.
 */

public class Jewel extends AutoAction {

    boolean isRed;


    //Constructor for MoveStraight that takes revs as a String representing the variable name to obtain from the opmode on init()
    public Jewel(AutonomousOpMode opmode, boolean isRed) {
        //Calls AutoAction constructor
        super(opmode);

        this.isRed = isRed;
    }


    @Override
    public void init() {
        opmode.telemetry.addData("red", opmode.getDataDouble("red"));
        if (isRed && opmode.getDataDouble("red") > 1 || !isRed && opmode.getDataDouble("red") <= 1) {
            opmode.addData("jewelRevs", .25);
            opmode.addData("negativeJewelRevs", -.25);
        } else {
            opmode.addData("jewelRevs", -.25);
            opmode.addData("negativeJewelRevs", .25);
        }
    }

    @Override
    public void update() {
        done = true;

    }

    @Override
    public void end() {
        //Calls the end() method from the superclass, AutoAction
        super.end();
        
    }
}
