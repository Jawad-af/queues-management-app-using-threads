package org.queuemgmtsys.constants;

public class Constants {
    public static int NUMBER_OF_CLIENTS;
    public static int NUMBER_OF_SERVERS;
    public static int SIMULATION_TIME;
    public static int MIN_ARRIVAL_TIME;
    public static int MAX_ARRIVAL_TIME;
    public static int MIN_SERVICE_TIME;
    public static int MAX_SERVICE_TIME;

    public Constants(int clientsNum, int queuesNum, int simulation, int[] arrivalTime, int[] serviceTime) {
        this.NUMBER_OF_CLIENTS = clientsNum;
        this.NUMBER_OF_SERVERS = queuesNum;
        this.SIMULATION_TIME = simulation;
        this.MIN_ARRIVAL_TIME = arrivalTime[0];
        this.MAX_ARRIVAL_TIME = arrivalTime[1];
        this.MIN_SERVICE_TIME = serviceTime[0];
        this.MAX_SERVICE_TIME = serviceTime[1];
    }

}
