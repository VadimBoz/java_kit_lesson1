package org.vadim.server;

public interface Repository {
    String readTextFromFile();
    void saveTextToFile(String txt);
}
