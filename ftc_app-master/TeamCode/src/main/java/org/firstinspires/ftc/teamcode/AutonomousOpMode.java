package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by BHS-Lab on 11/17/2017.
 */

//Makes this class a subclass of BaseOpMode
public class AutonomousOpMode extends BaseOpMode {

    //Creates a list or queue of commands which the autonomous will execute consecutively
    Queue<AutoAction> commands;

    //****
    AutoAction currCommand;
    HashMap<String, Object> data;

    @Override
    public void init() {
        //Calls the init() method from the superclass, BaseOpMode
        super.init();

        //****
        data = new HashMap<String, Object>();
        commands = new LinkedList<AutoAction>();
    }

    @Override
    public void loop() {
        //Calls the loop() method from the superclass, BaseOpMode
        super.loop();

        //Stops the loop if there are no more commands left
        if (commands.isEmpty() && currCommand == null) {
            stop();
            return;
        }

        //If the current command is null, it resets the current command to next command in the list and goes to the init method of the class that that command corresponds with
        if (currCommand == null) {
            currCommand = commands.poll();
            currCommand.init();
        }

        //Grabs the namee of the current command's class
        telemetry.addData("command", currCommand.getClass().getCanonicalName());

        //If the current command is done, it sends the program to the end method of that class, and then sets the current command to null - this will loop back to where the next command will be initialized if there is another command
        if (currCommand.isDone()) {
            currCommand.end();
            currCommand = null;
        } else {
            currCommand.update();
        }
    }

    //****
    public void addData(String id, Object obj) {
        this.data.put(id, obj);
    }

    public int getDataInt(String id) {
        return (int) this.data.get(id);
    }

    public double getDataDouble(String id) {
        return (double) this.data.get(id);
    }

    public String getDataString(String id) {
        return (String) this.data.get(id);
    }

    public Object getData(String id) {
        return this.data.get(id);
    }

}
