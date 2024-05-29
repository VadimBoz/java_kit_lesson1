package org.vadim.client;

public interface ClientView {
    void showMessage(String message);
    void sendMessageToServer();
    void disconnectFromServer();
    void connectToServer();
    Client getClientData();
}
