package org.vadim.server;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ServerGUI extends JFrame implements ServerView {
    private int POS_X;
    private int POS_Y;
    private int WIDTH = 400;
    private int HEIGHT  = 500;

    private static ServerView serverView;
    private final TextArea  textArea = new TextArea();;


    public ServerGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.POS_X = screenSize.width/2 - WIDTH/2;
        this.POS_Y = screenSize.height/2 - HEIGHT/2;
        setBounds(POS_X,POS_Y, WIDTH, HEIGHT);
        setTitle("Server chat");

        Font fontButton = new Font("Arial", Font.PLAIN, 20);
        setLayout(new BorderLayout());

        JButton buttonStart = new JButton("Start");
        buttonStart.setFont(fontButton);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        JButton buttonStop = new JButton("Stop");
        buttonStop.setFont(fontButton);
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(buttonStart);
        buttonsPanel.add(buttonStop);
        add(buttonsPanel, BorderLayout.SOUTH);

        textArea.setFont(fontButton);
        add(textArea);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public void startServer() {
        ServerController.serverController.startServer();
    }

    public void stopServer() {
        ServerController.serverController.stopServer();
    }

    @Override
    public void addTxtToTextArea(String sting) {
        textArea.append(sting);
    }

    @Override
    public String getTxtFromTextArea() {
        return textArea.getText();
    }
}
