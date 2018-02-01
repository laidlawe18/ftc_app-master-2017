package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by BHS-Lab on 1/29/2018.
 */

public class MoveServo extends AutoAction {

    Servo servo;
    double pos;

    double startTime;

    public MoveServo(AutonomousOpMode opmode, Servo servo, double pos) {
        super(opmode);
        this.servo = servo;
        this.pos = pos;
    }

    @Override
    public void init() {
        super.init();
        servo.setPosition(pos);
        startTime = System.nanoTime() / 1000000000d;
    }

    @Override
    public void update() {
        super.update();
        if (System.nanoTime() / 1000000000d > startTime + 0.7) {
            done = true;
        }
    }
}
