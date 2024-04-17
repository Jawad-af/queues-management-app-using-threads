package org.queuemgmtsys.strategy;

import org.queuemgmtsys.models.Client;
import org.queuemgmtsys.models.Server;

import java.util.List;

public class TimeStrategy implements Strategy{
    @Override
    public void addClient(List<Server> servers, Client client) throws InterruptedException {
        int min = servers.get(0).getWaitingTime().get();
        int serverIndex = 0;
        for (int i = 0;i < servers.size(); i++) {
            int waitingTime = servers.get(i).getWaitingTime().get();
            if (waitingTime < min) {
                min = waitingTime;
                serverIndex = i;
            }
        }
        servers.get(serverIndex).addClientToServer(client);
    }
}
