package org.vadim.client;

import org.vadim.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow extends JFrame {
    private final String CLIENT_IP = "192.168.0.3";
    private Font font = new Font("Arial", Font.PLAIN, 20);
    private TextArea textArea;
    private JButton clientLoginButton;
    private final ClientWindow currentClientWindow;
    private User curUser;
    private boolean connectToSer = false;


    public ClientWindow(ServerWindow serverWindow) {
        this.curUser = serverWindow.getClient();
        currentClientWindow = this;

        setBounds(serverWindow.getX()+30,serverWindow.getY()+ 30, serverWindow.getWidth() + 70, serverWindow.getHeight());
        setTitle("Client chat");

        setLayout(new BorderLayout());

        JPanel authorizationPanelFields = new JPanel();
        authorizationPanelFields.setLayout(new GridLayout(2,4, 5, 5));

        JPanel authorizationPanel = new JPanel();
        authorizationPanel.setLayout(new GridLayout(2,1, 2,3));



        JLabel textId = new JLabel("IP Client");
        textId.setFont(font);
        textId.setHorizontalAlignment(4);
        authorizationPanelFields.add(textId);

        TextField ipTextField = new TextField(curUser.getIp());
        ipTextField.setFont(font);
        authorizationPanelFields.add(ipTextField);

        JLabel textName = new JLabel("Name");
        textName.setFont(font);
        textName.setHorizontalAlignment(4);
        authorizationPanelFields.add(textName);

        TextField clientNameTextField = new TextField(curUser.getName());
        clientNameTextField.setFont(font);
        authorizationPanelFields.add(clientNameTextField);

        JLabel textID = new JLabel("ID");
        textID.setFont(font);
        textID.setHorizontalAlignment(4);
        authorizationPanelFields.add(textID);

        TextField clientIdTextField = new TextField(String.valueOf(curUser.getId()));
        clientIdTextField.setFont(font);
        authorizationPanelFields.add(clientIdTextField);

        JLabel textPass = new JLabel("Password");
        textPass.setFont(font);
        textPass.setHorizontalAlignment(4);
        authorizationPanelFields.add(textPass);

        JPasswordField clientPassTextField = new JPasswordField(curUser.getPassword());
        clientPassTextField.setFont(font);
        authorizationPanelFields.add(clientPassTextField);

        authorizationPanel.add(authorizationPanelFields);

        JPanel buttonLoginPanel = new JPanel();
        buttonLoginPanel.setSize(new Dimension(30, 10));

        JButton clientLoginButton = new JButton("Login");
        clientLoginButton.setFont(font);
        buttonLoginPanel.add(clientLoginButton);
        authorizationPanel.add(buttonLoginPanel);


        add(authorizationPanel, BorderLayout.NORTH);

        this.textArea = new TextArea();
        textArea.setFont(font);
        add(textArea);
        setVisible(true);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout());
        JTextField messageTextField  = new JTextField(22);
        messageTextField.setFont(font);

        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.setFont(font);
        messagePanel.add(messageTextField);
        messagePanel.add(sendMessageButton);
        add(messagePanel, BorderLayout.SOUTH);


        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String txt = messageTextField.getText();
               if(connectToSer && (txt != null | !txt.equals("")) ) {
                   textArea.append(curUser.getName()+ ": "+ txt + "\n");
                   serverWindow.addTxtToTeaxtArea(curUser.getName()+ ": "+ txt + "\n", curUser);
                   messageTextField.setText("");
               }


            }
        });

        clientLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User(clientNameTextField.getText(), clientPassTextField.getText(),
                        ipTextField.getText(), Integer.parseInt(clientIdTextField.getText()));
                serverWindow.connectNewClient(user, currentClientWindow);
                connectToSer = true;
            }
        });
    }

    public void addToTextArea(String txt) {
        textArea.append(txt);
    }

    public User getCurUser() {
        return curUser;
    }
}
