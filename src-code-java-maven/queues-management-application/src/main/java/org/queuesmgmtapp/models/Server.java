package org.queuesmgmtapp.models;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server(){
        //init queue and watiing period
    }

    public void addTask(Task newTask) {
        //add task to queue
        // increment the waiting period
    }

    @Override
    public void run() {
        while (true) {
            // take next task from queue
            // stop the thread for a time equal with the task's processing time
            // devremetn the waiting period
        }
    }

    public Task[] getTasks(){
        return new Task[2]; // to be implemented
    }
}