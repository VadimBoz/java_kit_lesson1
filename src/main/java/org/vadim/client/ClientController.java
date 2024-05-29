package org.vadim.client;

import org.vadim.server.ServerController;

public class ClientController {
      private Client client;
      private ClientView clientView;
      private ServerController serverController;
      private boolean connectingToServer = false;


    public ClientController(ServerController serverController) {
        this.serverController = serverController;
        this.client = serverController.getNewClient();
    }


    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

    public void sendMessageToServer(String txt) {
        serverController.sendMessageToClients(txt);
    }


    public void sendMessageToGUI(String txt) {
        clientView.showMessage(txt);
    }



    public boolean connectToServer(Client curclient) {
        if (serverController.isServerWorking()) {
            serverController.connectNewClient(curclient, clientView);
            connectingToServer = true;
            clientView.showMessage("The connection to the server is established\n");
            return true;
        } else {
            clientView.showMessage("The server is not running yet\n");
            return false;
        }
    }


    public void disconnectClient() {
//        serverController.disconnectClient(clientView, client);
        connectingToServer = false;
    }

    public ServerController getServerController() {
        return serverController;
    }

    public Client getClient() {
        return this.client;
    }



}
