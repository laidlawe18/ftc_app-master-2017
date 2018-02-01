package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/31/2018.
 */

public class ReadSensorValues extends AutoAction {

    double secs;
    double startTime;

    public ReadSensorValues (AutonomousOpMode opmode, double secs) {
        super(opmode);
        this.secs = secs;
    }

    @Override
    public void init() {
        startTime = System.nanoTime() / 1000000000d;
    }

    @Override
    public void update() {
        opmode.telemetry.addData("color", "(" + opmode.colorSensor.red() + ", " + opmode.colorSensor.green() + ", " + opmode.colorSensor.blue() + ")");
        opmode.telemetry.addData("light", opmode.getLightVal());
        opmode.telemetry.addData("gyro", opmode.getHeading());
        if (System.nanoTime() / 1000000000d > startTime + secs) {
            done = true;
        }
    }
}
