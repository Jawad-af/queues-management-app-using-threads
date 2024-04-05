package org.queuesmgmtapp.businesslogic;

import org.queuesmgmtapp.businesslogic.strategy.ShortestQueueStrategy;
import org.queuesmgmtapp.businesslogic.strategy.Strategy;
import org.queuesmgmtapp.businesslogic.strategy.TimeStrategy;
import org.queuesmgmtapp.constants.SelectionPolicy;
import org.queuesmgmtapp.models.Server;
import org.queuesmgmtapp.models.Task;

import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNOServers, int maxTasksPerServer) {
        this.maxNoServers = maxNOServers;
        this.maxTasksPerServer = maxTasksPerServer;
        // create server object for max no of servers
        // create thread with the object
    }

    public void changeStrategy(SelectionPolicy policy) {
        // apply strategy pattern to instatiate the strategy with the concrete
        // stratet correcspoding to policy
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        }
    }

    public void dispatchTask(Task t){
        // call the strategy addTask method
    }

    public List<Server> getServers() {
        return servers;
    }
}
