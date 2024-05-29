package org.vadim;

import org.vadim.client.ClientController;
import org.vadim.client.ClientGUI;
import org.vadim.server.ServerController;
import org.vadim.server.ServerGUI;
import org.vadim.server.ServerView;

public class Main {
    public static void main(String[] args) {
        ServerController server = ServerController.start();

        new ClientGUI(server);
        new ClientGUI(server);
    }
}