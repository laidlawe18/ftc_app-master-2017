package org.firstinspires.ftc.teamcode;

/**
 * Created by BHS-Lab on 1/13/2018.
 */

public class SimultaneousAction extends AutoAction {

    AutoAction action1;
    AutoAction action2;
    boolean action1Ended;
    boolean action2Ended;

    public SimultaneousAction(AutonomousOpMode opmode, AutoAction action1, AutoAction action2) {
        super(opmode);
        this.action1 = action1;
        this.action2 = action2;
        action1Ended = false;
        action2Ended = false;
    }

    @Override
    public void init() {
        super.init();
        action1.init();
        action2.init();
    }

    @Override
    public void update() {
        super.update();
        if (action1.isDone() && !action1Ended) {
            action1.end();
            action1Ended = true;
        } else {
            action1.update();
        }
        if (action2.isDone() && !action2Ended) {
            action2.end();
            action2Ended = true;
        }
        if (action1Ended && action2Ended) {
            done = true;
        } else {
            action2.update();
        }
    }
}
