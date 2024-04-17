package org.queuemgmtsys.models;

import org.queuemgmtsys.global.Globals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private String id;
    private BlockingQueue<Client> queue;
    private Client currentClient;
    private AtomicInteger waitingTime;
    private AtomicInteger totalWaitingTime = new AtomicInteger();
    private AtomicInteger totalClientsServed = new AtomicInteger();

    public Server(BlockingQueue<Client> queue) {
        this.id = generateId();
        this.queue = queue;
        this.waitingTime = new AtomicInteger();
    }

    private String generateId() {
        return "SER" + new Random().nextInt(0, 100);
    }

    @Override
    public void run() {
        while (!Globals.exit){
            processUpcomingClient();
        }
    }

    private void processUpcomingClient() {
        try {
            currentClient = queue.take();
//            Thread.sleep(1000);
            int currentServiceTime = currentClient.getServiceTime().get();
            int clientWaitingTime = currentClient.getServiceTime().get();
            while (currentServiceTime > 0) {
                Thread.sleep(1000);
                currentServiceTime--;
                currentClient.setServiceTime(currentServiceTime);
                this.waitingTime.decrementAndGet();
                totalWaitingTime.incrementAndGet();
                totalClientsServed.incrementAndGet();
            }
            currentClient = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getAverageWaitingTime() {
        if (totalClientsServed.get() == 0) {
            return 0;
        }
        return (double) totalWaitingTime.get() / totalClientsServed.get();
    }

    public void addClientToServer(Client client) throws InterruptedException {
        queue.put(client);
        setWaitingTime(getWaitingTime().get() + client.getServiceTime().get());
    }

    public synchronized Client getCurrentClient() {
        return currentClient;
    }

    public List<Client> getWaitingClients() {
        List<Client> clients = new ArrayList<>();
        for (Client client : queue) {
            clients.add(client);
        }
        return clients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime.set(waitingTime);
    }

}
