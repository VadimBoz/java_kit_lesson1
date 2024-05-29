package org.vadim;

import org.vadim.client.ClientGUI;
import org.vadim.server.ServerController;

public class Main {
    public static void main(String[] args) {
        ServerController server = ServerController.start();

        new ClientGUI(server);
        new ClientGUI(server);
    }
}