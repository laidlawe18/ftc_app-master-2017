package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/10/2018.
 */

//Makes this class a subclass of AutoAction
public class Belts extends AutoAction {

    double seconds;
    double startTime;

    public Belts(AutonomousOpMode opmode, double seconds) {
        super(opmode);
        this.seconds = seconds;
    }

    @Override
    public void init() {
        super.init();
        startTime = System.nanoTime() / 1000000000.0;
        opmode.motorBeltLeft.setPower(1);
        opmode.motorBeltRight.setPower(1);
    }

    @Override
    public void update() {
        super.update();
        if (System.nanoTime() / 1000000000.0 > startTime + 1) {
            done = true;
        }
    }

    @Override
    public void end() {
        super.end();
        opmode.motorBeltLeft.setPower(0);
        opmode.motorBeltRight.setPower(0);
    }
}
