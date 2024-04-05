package org.queuesmgmtapp.constants;

import com.sun.tools.jconsole.JConsoleContext;

public class Constants {
    private static int NUMBER_OF_CLIENTS;
    private static int NUMBER_OF_QUEUES;
    private static int ARRIVAL_TIME;
    private static int MIN_SERVICE_TIME;
    private static int MAX_SERIVE_TIME;

    public Constants(int clientsNum, int queuesNum, int arrivalTime, int minTime, int maxTime) {
        NUMBER_OF_CLIENTS = clientsNum;
        NUMBER_OF_QUEUES = queuesNum;
        ARRIVAL_TIME = arrivalTime;
        MIN_SERVICE_TIME = minTime;
        MAX_SERIVE_TIME = maxTime;
    }

}
