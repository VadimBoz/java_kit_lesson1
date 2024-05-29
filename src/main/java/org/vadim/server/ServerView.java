package org.vadim.server;


public interface ServerView {
    void addTxtToTextArea(String sting);
    String getTxtFromTextArea();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}
