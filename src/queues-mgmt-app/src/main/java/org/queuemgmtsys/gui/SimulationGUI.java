package org.queuemgmtsys.gui;

import org.queuemgmtsys.appmanagers.SimulationManager;
import org.queuemgmtsys.constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulationGUI {

    public static JTextArea outputTextArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame inputFrame = new JFrame("Simulation GUI");
            inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            inputFrame.setSize(400, 200);

            JPanel inputPanel = new JPanel(new GridLayout(6, 2));

            JLabel clientsLabel = new JLabel("Number of clients:");
            JTextField clientsField = new JTextField();
            JLabel serversLabel = new JLabel("Number of servers:");
            JTextField serversField = new JTextField();
            JLabel simulationLabel = new JLabel("Simulation time:");
            JTextField simulationField = new JTextField();
            JLabel arrivalLabel = new JLabel("Arrival time bounds:");
            JTextField arrivalField = new JTextField();
            JLabel serviceLabel = new JLabel("Service time bounds:");
            JTextField serviceField = new JTextField();

            inputPanel.add(clientsLabel);
            inputPanel.add(clientsField);
            inputPanel.add(serversLabel);
            inputPanel.add(serversField);
            inputPanel.add(simulationLabel);
            inputPanel.add(simulationField);
            inputPanel.add(arrivalLabel);
            inputPanel.add(arrivalField);
            inputPanel.add(serviceLabel);
            inputPanel.add(serviceField);

            JButton startButton = new JButton("Start Simulation");

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int clients = Integer.parseInt(clientsField.getText());
                        int servers = Integer.parseInt(serversField.getText());
                        int simulation = Integer.parseInt(simulationField.getText());
                        String[] arrivalBounds = arrivalField.getText().split(" ");
                        int arrivalMin = Integer.parseInt(arrivalBounds[0]);
                        int arrivalMax = Integer.parseInt(arrivalBounds[1]);
                        String[] serviceBounds = serviceField.getText().split(" ");
                        int serviceMin = Integer.parseInt(serviceBounds[0]);
                        int serviceMax = Integer.parseInt(serviceBounds[1]);

                        if (clients < 0 || clients > 1500) {
                            JOptionPane.showMessageDialog(inputFrame, "Invalid clients number");
                            return;
                        }

                        if (servers < 0 || servers > 100) {
                            JOptionPane.showMessageDialog(inputFrame, "Invalid servers number");
                            return;
                        }

                        if (simulation < 0) {
                            JOptionPane.showMessageDialog(inputFrame, "Number of simulation must be positive ");
                            return;
                        }

                        if (arrivalMin < 0) {
                            JOptionPane.showMessageDialog(inputFrame, "Number of arrival min must be positive");
                            return;
                        } else if (arrivalMin > arrivalMax) {
                            JOptionPane.showMessageDialog(inputFrame, "Check if min arrival time is not greater than max arrival time");
                            return;
                        } else if (arrivalMax > simulation) {
                            JOptionPane.showMessageDialog(inputFrame, "Arrival max is greater than the simulation time.");
                            return;
                        }

                        if (serviceMin < 0) {
                            JOptionPane.showMessageDialog(inputFrame, "Number of serviceMin min must be positive");
                            return;
                        } else if (serviceMin > serviceMax) {
                            JOptionPane.showMessageDialog(inputFrame, "ServiceMin time is greater than serviceMax time");
                            return;
                        } else if (serviceMax > simulation) {
                            JOptionPane.showMessageDialog(inputFrame, "ServiceMax is greater than the simulation time.");
                            return;
                        }

                        Constants constants = new Constants(clients, servers, simulation, new int[]{arrivalMin, arrivalMax}, new int[]{serviceMin, serviceMax});

                        SimulationManager simulationManager;
                        try {
                            simulationManager = new SimulationManager();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                            return;
                        }

                        outputTextArea = new JTextArea();
                        outputTextArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(outputTextArea);

                        JFrame frame = new JFrame("Simulation Output");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.add(scrollPane, BorderLayout.CENTER);
                        frame.setSize(600, 400);
                        frame.setVisible(true);

                        LocalDateTime currentDateTime = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String formattedDateTime = currentDateTime.format(formatter);

                        outputTextArea.append(
                                "Date: " + formattedDateTime + "\n" +
                                "Clients: " + Constants.NUMBER_OF_CLIENTS + "\n" +
                                "servers: " + Constants.NUMBER_OF_SERVERS + "\n" +
                                "simulation: " + Constants.SIMULATION_TIME + "\n" +
                                "arrival time bounds: [" + Constants.MIN_ARRIVAL_TIME + ", " + Constants.MAX_ARRIVAL_TIME + "]" + "\n" +
                                "service time bounds: [" + Constants.MIN_SERVICE_TIME + ", " + Constants.MAX_SERVICE_TIME + "]" + "\n\n"
                        );

                        Thread simulationsThread = new Thread(simulationManager);
                        simulationsThread.start();

                        inputFrame.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(inputFrame, "Please enter valid integer values for input fields.");
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(inputFrame, "Please enter arrival and service time bounds separated by a space.");
                    }
                }
            });

            inputFrame.add(inputPanel, BorderLayout.CENTER);
            inputFrame.add(startButton, BorderLayout.SOUTH);
            inputFrame.setVisible(true);
        });
    }
}
