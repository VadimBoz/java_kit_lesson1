package org.vadim.server;

import org.vadim.client.Client;
import org.vadim.client.ClientView;

import java.io.*;
import java.util.ArrayList;

public class ServerController {

    private ArrayList<Client> connectedClients = new ArrayList<>();
    private final ArrayList<Client> allClient = new ArrayList<>();
    private  ArrayList<ClientView> connectedClientViews = new ArrayList<>();
    private boolean serverWorking = false;
    private static ServerView serverView;
    public static ServerController serverController;
    private final Repository repository;

    {
        allClient.add(new Client("Serge", "123312wec", "192.168.0.57"));
        allClient.add(new Client("Olga", "1233wec12", "192.168.0.7"));
        allClient.add(new Client("Svetlana", "1233wcsv12", "192.168.0.34"));
        allClient.add(new Client("Maxim", "123svs312", "192.168.0.36"));
        allClient.add(new Client("Vladimir", "1sdv23312", "192.168.0.43"));
        allClient.add(new Client("Ivan", "12sdv3312", "192.168.0.12"));
        allClient.add(new Client("Natalia", "12sdv3312", "192.168.0.66"));
        allClient.add(new Client("Sofia", "12sdv3312", "192.168.0.67"));
        allClient.add(new Client("Alexandr", "12sdv3312", "192.168.0.68"));
        allClient.add(new Client("Mikel", "12sdv3312", "192.168.0.69"));
        allClient.add(new Client("Alex", "12sdv5b3312", "192.168.0.70"));
    }


    private ServerController() {
            serverView = new ServerGUI();
            repository = new DataBase();
    }


    public static ServerController start() {
        if (serverController == null) {
            serverController = new ServerController();
        }
        return serverController;
    }


    public void startServer() {
        if (serverWorking) {
            System.out.println("Server is already running");
            serverView.addTxtToTextArea("Server is already running\n");

        } else {
            System.out.println("Server starting");
            serverView.addTxtToTextArea("Server starting\n");
            serverView.addTxtToTextArea(repository.readTextFromFile());
            serverWorking = true;
        }

    }


    public void stopServer() {
        if (serverWorking) {
            System.out.println("Server stopping...");
            sendMessageToClients("Server stopped\n");
            for (ClientView curClientView : connectedClientViews) {
                serverView.addTxtToTextArea(curClientView.getClientData().getName() + " disconnected\n");
                 curClientView.disconnectFromServer();
            }
            connectedClientViews = new ArrayList<>();
            connectedClients = new ArrayList<>();
            serverWorking = false;

        } else {
            System.out.println("Server close");
            repository.saveTextToFile(serverView.getTxtFromTextArea());
            System.exit(0);
        }
    }


    public void connectNewClient(Client client, ClientView clientView) {
        if (!connectedClients.contains(client) && allClient.contains(client)) {
            connectedClients.add(client);
            connectedClientViews.add(clientView);
            sendMessageToClients("connecting " + client.getName()+ "\n");
        } else if (connectedClients.contains(client)) {
            clientView.showMessage("connecting with " + client.getName() + " already established\n");
        } else  {
            clientView.showMessage("wrong user " + client.getName() + " ID " + client.getId() + "\n");
        }

    }


    public void disconnectClient(ClientView clientView, Client client) {
        if (connectedClientViews.contains(clientView) && connectedClients.contains(client)) {
            clientView.showMessage("You have been disconnected from the server");
            sendMessageToClients(clientView.getClientData().getName() + " has been disconnecting \n");
            connectedClientViews.remove(clientView);
            connectedClients.remove(client);
        } else {
            clientView.showMessage("You have been already disconnected from the server");
        }

    }


    public void sendMessageToClients(String txt) {
        for (ClientView curClientView: connectedClientViews) {
            curClientView.showMessage(txt);
        }
        serverView.addTxtToTextArea(txt);
    }



    public boolean isServerWorking() {
        return serverWorking;
    }


    public Client getNewClient() {
        Client newClient;
        do {
            newClient = allClient.get((int) (Math.random() * 11));
        } while (connectedClients.contains(newClient));
        return newClient;
    }


    public ServerView getServerView() {
        return serverView;
    }



}
