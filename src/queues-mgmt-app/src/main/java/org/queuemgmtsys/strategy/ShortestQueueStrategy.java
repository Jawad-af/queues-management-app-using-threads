package org.queuemgmtsys.strategy;


import org.queuemgmtsys.models.Client;
import org.queuemgmtsys.models.Server;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addClient(List<Server> servers, Client client) throws InterruptedException {
        int min = servers.get(0).getWaitingClients().size();
        int serverLocation = 0;
        int serversSize = servers.size();
        for (int i = 0; i< serversSize; i++) {
            int serverSize = servers.get(i).getWaitingClients().size();
            if (serverSize <= min) {
                min = serverSize;
                serverLocation = i;
            }
        }
        servers.get(serverLocation).addClientToServer(client);
    }
}
