package org.queuesmgmtapp.businesslogic.strategy;

import org.queuesmgmtapp.models.Server;
import org.queuesmgmtapp.models.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);

}
