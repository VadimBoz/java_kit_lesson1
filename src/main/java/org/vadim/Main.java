package org.vadim;

import org.vadim.client.ClientWindow;
import org.vadim.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow server = ServerWindow.startServer();
        new ClientWindow(server);
        new ClientWindow(server);
    }
}