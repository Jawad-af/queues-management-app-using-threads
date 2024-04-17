package org.queuemgmtsys.appmanagers;

import org.queuemgmtsys.constants.Constants;
import org.queuemgmtsys.constants.SelectionPolicy;
import org.queuemgmtsys.global.Globals;
import org.queuemgmtsys.models.Client;
import org.queuemgmtsys.models.Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static org.queuemgmtsys.global.Globals.*;
import static org.queuemgmtsys.gui.SimulationGUI.outputTextArea;

public class SimulationManager implements Runnable {
    private Scheduler scheduler;
    private CopyOnWriteArrayList<Client> generatedClients;
    double avgWaitingTime = 0;

    public SimulationManager() throws InterruptedException {
        generatedClients = new CopyOnWriteArrayList<>();
        generateRandomClients();
        this.scheduler = new Scheduler();
    }

    public int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private void generateRandomClients(){
        for (int i = 0; i < Constants.NUMBER_OF_CLIENTS; i++) {
            Client client = new Client(i+1, getRandomNumber(Constants.MIN_ARRIVAL_TIME, Constants.MAX_ARRIVAL_TIME + 1),
                    getRandomNumber(Constants.MIN_SERVICE_TIME, Constants.MAX_SERVICE_TIME + 1));
            generatedClients.add(client);
            Collections.sort(generatedClients, Comparator.comparing(Client::getArrivalTime));
        }

        System.out.println("Generated clients: ");
        int size = generatedClients.size();
        for (int i = 0; i < size; i++) {
            System.out.print(generatedClients.get(i).toString() + "; ");
            if((i+1) % 10 == 0)
                System.out.println();
        }
        System.out.println("\n\n");
    }

    public void writeReport(int currentTime) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            stringBuilder.append("\nCurrent time: " + currentTime + "\n" +
                    "Waiting Clients:");

            if (generatedClients.size() > 10) {
                for (int i = 0; i < 10; i++) {
                    stringBuilder.append("  " + generatedClients.get(i).toString());
                }
                stringBuilder.append(" ... " + (generatedClients.size() - 10) + " more.");
            }else {
                for (Client client : generatedClients) {
                    stringBuilder.append(client.toString() + "   ");
                }
            }
            stringBuilder.append("\n");

            for (int i = 0; i < Constants.NUMBER_OF_SERVERS; i++) {
                List<Server> servers = scheduler.getServers();
                Server server = servers.get(i);
                int serverClientsNumber = server.getWaitingClients().size();
                int serverWaitingTime = server.getWaitingTime().get();
                stringBuilder.append((i+1) + ". Server " + server.getId() + ": ");
                if (server.getCurrentClient() != null) {
                    stringBuilder.append(server.getCurrentClient()).append("\n");
                } else {
                    stringBuilder.append("closed.\n");
                }
            }
            stringBuilder.append("Avg waiting time: " + avgWaitingTime);
            fileWriter.write(stringBuilder.toString());
            outputTextArea.append(stringBuilder.toString());
        }
    }

    public boolean isGreatDifference(int difference) {
        List<Server> servers = scheduler.getServers();
        int diff = 0;
        for (int i = 0; i < servers.size() - 1; i++) {
            diff = Math.abs(servers.get(i).getWaitingTime().get() - servers.get(i + 1).getWaitingTime().get());
        }
        if(diff >= difference)
            return true;
        return false;
    }

    @Override
    public void run() {
        if(file.length() > 0)
            file.delete();
        file = new File("Output.txt");
        currentTime = 0;
        while (!exit) {
            List<Server> servers = scheduler.getServers();
            checkDifference();
            addClients();
            try {
                Thread.sleep(1000);
                writeReport(currentTime);
                avgWaitingTime = Math.max(calculateAverageWaitingTime(servers), avgWaitingTime);
                currentTime++;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (currentTime >= Constants.SIMULATION_TIME) {
                Globals.exit = true;
            }
        }
        System.out.println("AvgWT: " + avgWaitingTime);
    }

    public double calculateAverageWaitingTime(List<Server> servers) {
        int totalWaitingTime = 0;
        int totalServers = servers.size();
        for (Server server : servers) {
            totalWaitingTime += server.getAverageWaitingTime();
        }
        return (double) totalWaitingTime / totalServers;
    }


    private void addClients() {
        for (Client client : generatedClients) {
            if (client.getArrivalTime() == currentTime) {
                try {
                    scheduler.dispatchClient(client);
                    generatedClients.remove(client);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // THIS METHOD CHECKS IN THE RUNTIME THE DIFFERENCE BETWEEN THE WAITING TIME OF THE
    // ACTIVE THREADS IN THE POOL, IF IT IS GREATER THAN 10 IT WILL CHANGE THE STRATEGY
    // TO TIME_STRATEGY FOR 5 SECONDS AND THEN IT WILL RETURN TO THE SHORTEST_QUEUE_STRATEGY
    // IT SEEMS TO BE THE BEST APPROACH AFTER PERFORMING A NUMBER OF TESTS ON DIFFERENT INPUTS.
    private void checkDifference() {
        if(isGreatDifference(10)){
            scheduler.changeStrategy(SelectionPolicy.TIME_STRATEGY);
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    scheduler.changeStrategy(SelectionPolicy.SHORTEST_QUEUE_STRATEGY);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public CopyOnWriteArrayList<Client> getGeneratedClients() {
        return generatedClients;
    }

    public void setGeneratedClients(CopyOnWriteArrayList<Client> generatedClients) {
        this.generatedClients = generatedClients;
    }
}