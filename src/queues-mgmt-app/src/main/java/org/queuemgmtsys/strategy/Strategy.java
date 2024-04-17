package org.queuemgmtsys.strategy;


import org.queuemgmtsys.models.Client;
import org.queuemgmtsys.models.Server;

import java.util.List;

public interface Strategy {
    public void addClient(List<Server> servers, Client client) throws InterruptedException;

}
