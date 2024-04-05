package org.queuesmgmtapp.businesslogic;

import org.queuesmgmtapp.constants.SelectionPolicy;
import org.queuesmgmtapp.gui.SimulationFrame;
import org.queuesmgmtapp.models.Task;

import java.util.List;

public class SimulationManager implements Runnable {

    public int timeLimit = 100;
    public int maxProcessingTime = 10;
    public int minProcessingTime = 2;
    public int numberOfServers = 3;
    public int numberOfClients = 100;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    // entity responsible with queue management and client distribution
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;

    public SimulationManager() {
        // initialize the scheduler
        // => create and strat numberofservers threads
        // => init selection strategy => crate stratry
        // iniit freame to display simulation
        // generate numberofclients clients using generatenrandomtasks()
        // and store them to generateTasks
    }

    private void generateNRandomTasks(){
        // generate N random tasks
        // - random processing time
        // minProcessingTime < processingTime < maxProcessingTime
        // - random arrivalTime
        // sort list with respect to arrivalTime
    }

    @Override
    public void run() {
        int currentTime = 0;
        while (currentTime < timeLimit) {
            // iterate generatedTasks list and pick tasks that have teh
            // arrivalTime equal with the currentTime
            // - send taks to queue by calling the displatchtask method
            // from scheduler
            // - delete client form list
            // update UI frame
            currentTime++;
            // wait an intercal of 1 second
        }
    }

    public static void main(String[] args) {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }
}
