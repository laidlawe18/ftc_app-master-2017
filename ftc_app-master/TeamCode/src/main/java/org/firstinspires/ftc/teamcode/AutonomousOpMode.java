package org.firstinspires.ftc.teamcode;

//This imports all of the methods we need to run the program
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
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

    //Sets up variables for the current command within the subclasses of AutoAction and a hashmap for the different variables that will be in each command
    AutoAction currCommand;
    HashMap<String, Object> data;

    @Override
    public void init() {
        //Calls the init() method from the superclass, BaseOpMode
        super.init();

        //Helps set up the list of commmands by forming maps and lists of data and commands
        data = new HashMap<String, Object>();
        commands = new LinkedList<AutoAction>();
    }

    public void initFirstCommand() {
        if (!commands.isEmpty()) {
            currCommand = commands.poll();
            currCommand.init();
        }
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

        //Grabs the name of the current command's class
        telemetry.addData("command", currCommand.getClass().getCanonicalName());

        //If the current command is done, it sends the program to the end method of that class, and then sets the current command to null - this will loop back to where the next command will be initialized if there is another command
        if (currCommand.isDone()) {
            currCommand.end();
            currCommand = null;
        } else {
            currCommand.update();
        }
    }

    //Sets up the various types of data that can be in the command
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

    /*public double evaluateExpression(String str) {
        ArrayList<String> strings = new ArrayList<String>();
        String curr = str.substring(0, 1);
        for (int i = 1; i < str.length(); i++) {
            if (str.substring(i, i + 1).matches("[+*-]"))
        }
    }*/

}
