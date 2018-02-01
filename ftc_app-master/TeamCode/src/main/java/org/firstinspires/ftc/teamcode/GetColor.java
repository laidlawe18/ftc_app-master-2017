package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/29/2018.
 */

public class GetColor extends AutoAction {

    int numReadings;
    double redVal;
    double greenVal;
    double blueVal;

    public GetColor(AutonomousOpMode opmode) {
        super(opmode);
    }

    @Override
    public void init() {
        super.init();

        numReadings = 0;
        redVal = 0;
        greenVal = 0;
        blueVal = 0;
    }

    @Override
    public void update() {
        super.update();

        redVal = (redVal * numReadings + opmode.colorSensor.red()) / (numReadings + 1);
        greenVal = (greenVal * numReadings + opmode.colorSensor.green()) / (numReadings + 1);
        blueVal = (blueVal * numReadings + opmode.colorSensor.blue()) / (numReadings + 1);
        numReadings++;
        if (numReadings >= 20) {
            done = true;
        }
    }

    @Override
    public void end() {
        super.end();

        opmode.addData("red", redVal);
        opmode.addData("green", greenVal);
        opmode.addData("blue", blueVal);
    }
}
