package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/10/2018.
 */

//Makes this class a subclass of AutoAction
public class Belts extends AutoAction {

    //Creates instance variables for an amount of time and the current time (respectively)
    double seconds;
    double startTime;

    //This constructor is called when an instance of this class is created (aka in AutonomousRed or in AutonomousBlue)
    public Belts(AutonomousOpMode opmode, double seconds) {
        //Calls AutoAction constructor
        super(opmode);

        //Sets seconds equal to the constraint found in the autonomous command
        this.seconds = seconds;
    }

    @Override
    public void init() {
        //Calls the init() method from the superclass, AutoAction
        super.init();

        //Sets the timer to the current time that the program has been running for and the belt motors' powers to 1
        startTime = System.nanoTime() / 1000000000.0;
        opmode.motorBeltLeft.setPower(1);
        opmode.motorBeltRight.setPower(1);
    }

    @Override
    public void update() {
        //Calls the update() method from the superclass, AutoAction
        super.update();

        //Continues setting the belt motors' powers to 1 until 1 second has passed - it then determines the program done
        opmode.motorBeltLeft.setPower(1);
        opmode.motorBeltRight.setPower(1);
        if (System.nanoTime() / 1000000000.0 > startTime + 1) {
            done = true;
        }
    }

    @Override
    public void end() {
        //Calls the end() method from the superclass, AutoAction
        super.end();

        //Sets the powers of the belt motors to 0
        opmode.motorBeltLeft.setPower(0);
        opmode.motorBeltRight.setPower(0);
    }
}
