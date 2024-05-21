package org.vadim.server;

import org.vadim.Main;
import org.vadim.client.ClientWindow;
import org.vadim.client.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ServerWindow extends JFrame {
    private int POS_X;
    private int POS_Y;
    private int WIDTH = 400;
    private int HEIGHT  = 500;
    private boolean isServerWorking = false;
    private ArrayList<User> connectedUser = new ArrayList<>();
    private  ArrayList<User> allUser = new ArrayList<>();
    private static ServerWindow serverWindow;
    private TextArea textArea;
    private final ArrayList<ClientWindow> clientWindows = new ArrayList<>();


    {
        allUser.add(new User("Serge", "123312wec", "192.168.0.57"));
        allUser.add(new User("Olga", "1233wec12", "192.168.0.7"));
        allUser.add(new User("Svetlana", "1233wcsv12", "192.168.0.34"));
        allUser.add(new User("Maxim", "123svs312", "192.168.0.36"));
        allUser.add(new User("Vladimir", "1sdv23312", "192.168.0.43"));
        allUser.add(new User("Ivan", "12sdv3312", "192.168.0.12"));
        allUser.add(new User("Natalia", "12sdv3312", "192.168.0.66"));
        allUser.add(new User("Sofia", "12sdv3312", "192.168.0.67"));
        allUser.add(new User("Alexandr", "12sdv3312", "192.168.0.68"));
        allUser.add(new User("Mikel", "12sdv3312", "192.168.0.69"));
        allUser.add(new User("Alex", "12sdv5b3312", "192.168.0.70"));
    }


    private ServerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.POS_X = screenSize.width/2 - WIDTH/2;
        this.POS_Y = screenSize.height/2 - HEIGHT/2;
        setBounds(POS_X,POS_Y, WIDTH, HEIGHT);
        setTitle("Server chat");
        textArea = new TextArea();
        Font fontButton = new Font("Arial", Font.PLAIN, 20);
        setLayout(new BorderLayout());

        JButton buttonStart = new JButton("Start");
        buttonStart.setFont(fontButton);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking) {
                    System.out.println("Server is already running");
                    textArea.append("Server is already running\n");

                } else {
                    System.out.println("Server starting");
                    textArea.append("Server starting\n");
                    textArea.append(readTextFromFile());
                    isServerWorking = true;
                }
            }
        });

        JButton buttonStop = new JButton("Stop");
        buttonStop.setFont(fontButton);
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if (isServerWorking) {
                        isServerWorking = false;
                        System.out.println("Server stopped");
                        textArea.append("Server stopped\n");

                    } else {
                        System.out.println("Server close");
                        saveTextToFile(textArea.getText());
                        System.exit(0);
                    }
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


    public static ServerWindow startServer() {
        if (serverWindow == null) return new ServerWindow();
        else return serverWindow;
    }


    public User getClient() {
        User curUser;
        do {
            curUser = allUser.get((int) (Math.random() * 11));
        } while (connectedUser.contains(curUser));

        return curUser;
    }


    public void addTxtToTeaxtArea(String txt, User user) {
        textArea.append(txt);
        for (ClientWindow curClientWindow : clientWindows) {
            if (curClientWindow.getCurUser().equals(user)) continue;
            curClientWindow.addToTextArea(txt);
        }
    }

    public void connectNewClient(User user, ClientWindow clientWindow) {
        if (!connectedUser.contains(user) && allUser.contains(user)) {
            connectedUser.add(user);
            clientWindows.add(clientWindow);
            textArea.append("connecting" + user.getName()+ "\n");
        } else if (connectedUser.contains(user)) {
            textArea.append("connecting with " + user.getName() + " already established\n");
        } else  {
            textArea.append("wrong user " + user.getName() + " ID " + user.getId() + "\n");
        }

    }


    private void saveTextToFile(String txt) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("chat.log"))){
            bw.write(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String readTextFromFile()  {
        StringBuilder str = new StringBuilder();
        File f = new File("chat.log");
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader("chat.log"))) {
                while (br.ready()) {
                    str.append(br.readLine());
                    str.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return str.toString();
    }

}
