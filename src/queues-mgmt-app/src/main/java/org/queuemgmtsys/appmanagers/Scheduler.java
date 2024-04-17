package org.queuemgmtsys.appmanagers;

import org.queuemgmtsys.constants.Constants;
import org.queuemgmtsys.constants.SelectionPolicy;
import org.queuemgmtsys.models.Client;
import org.queuemgmtsys.models.Server;
import org.queuemgmtsys.strategy.ShortestQueueStrategy;
import org.queuemgmtsys.strategy.Strategy;
import org.queuemgmtsys.strategy.TimeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class Scheduler {
    private List<Server> servers;
    private Strategy strategy;
    private List<Thread> threads;

    public Scheduler() throws InterruptedException {

        this.strategy = new ShortestQueueStrategy();
        this.servers = new ArrayList<>();
        threads = new ArrayList<>();
        for (int i = 0; i < Constants.NUMBER_OF_SERVERS; i++) {
            Server server = new Server(new LinkedBlockingDeque<>());
            Thread thread = new Thread(server);
            threads.add(thread);
            thread.start();
            servers.add((Server) server);
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE_STRATEGY) {
            strategy = new ShortestQueueStrategy();
        }
        if (policy == SelectionPolicy.TIME_STRATEGY) {
            strategy = new TimeStrategy();
        }
    }

    public void dispatchClient(Client client) throws InterruptedException {
        strategy.addClient(servers, client);
    }
    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }
}
