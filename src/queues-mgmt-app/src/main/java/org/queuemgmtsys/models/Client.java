package org.queuemgmtsys.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private int id = 0;
    private int arrivalTime;
    private AtomicInteger serviceTime;

    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = new AtomicInteger();
        this.serviceTime.set(serviceTime);
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public synchronized AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime.set(serviceTime);
    }

    @Override
    public String toString() {
        return "[" +
                " " + this.id +
                "  " + arrivalTime +
                "  " + serviceTime +
                " ]";
    }
}
