package org.vadim.client;

import org.vadim.server.ServerController;
import org.vadim.server.ServerGUI;
import org.vadim.server.ServerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ClientView {
    private final String CLIENT_IP = "192.168.0.3";
    private Font font = new Font("Arial", Font.PLAIN, 20);
    private final TextArea textArea;
    private Client client;
//    private boolean connectToSer = false;
    private final TextField ipTextField;
    private final TextField clientNameTextField;
    private final TextField clientIdTextField;
    private final JPasswordField clientPassTextField;
    private final JTextField messageTextField;
    private final ClientController clientController;
    private final JPanel authorizationPanel;
    private ServerController serverController;




    public ClientGUI(ServerController serverController) {
        this.clientController  = new ClientController(serverController);
        this.clientController.setClientView(this);
        this.client = clientController.getClient();
        this.serverController = clientController.getServerController();

        ServerView curServerView = serverController.getServerView();
        setBounds(curServerView.getX()+40, curServerView.getY()+ 40, curServerView.getWidth() + 70, curServerView.getHeight());
        setTitle("Client chat");

        setLayout(new BorderLayout());

        JPanel authorizationPanelFields = new JPanel();
        authorizationPanelFields.setLayout(new GridLayout(2,4, 5, 5));

        authorizationPanel = new JPanel();
        authorizationPanel.setLayout(new GridLayout(2,1, 2,3));

        JLabel textId = new JLabel("IP Client");
        textId.setFont(font);
        textId.setHorizontalAlignment(4);
        authorizationPanelFields.add(textId);

        this.ipTextField = new TextField(client.getIp());
        this.ipTextField.setFont(font);
        authorizationPanelFields.add(this.ipTextField);

        JLabel textName = new JLabel("Name");
        textName.setFont(font);
        textName.setHorizontalAlignment(4);
        authorizationPanelFields.add(textName);

        this.clientNameTextField = new TextField(client.getName());
        this.clientNameTextField.setFont(font);
        authorizationPanelFields.add(this.clientNameTextField);

        JLabel textID = new JLabel("ID");
        textID.setFont(font);
        textID.setHorizontalAlignment(4);
        authorizationPanelFields.add(textID);

        this.clientIdTextField = new TextField(String.valueOf(client.getId()));
        this.clientIdTextField.setFont(font);
        authorizationPanelFields.add(this.clientIdTextField);

        JLabel textPass = new JLabel("Password");
        textPass.setFont(font);
        textPass.setHorizontalAlignment(4);
        authorizationPanelFields.add(textPass);

        this.clientPassTextField = new JPasswordField(client.getPassword());
        this.clientPassTextField.setFont(font);
        authorizationPanelFields.add(this.clientPassTextField);

        authorizationPanel.add(authorizationPanelFields);

        JPanel buttonLoginPanel = new JPanel();
        buttonLoginPanel.setSize(new Dimension(30, 10));

        JButton clientLoginButton = new JButton("Login");
        clientLoginButton.setFont(font);
        buttonLoginPanel.add(clientLoginButton);
        authorizationPanel.add(buttonLoginPanel);


        add(authorizationPanel, BorderLayout.NORTH);

        this.textArea = new TextArea();
        this.textArea.setFont(font);
        add(this.textArea);
        this.textArea.setEditable(false);
        setVisible(true);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout());
        this.messageTextField  = new JTextField(22);
        this.messageTextField.setFont(font);

        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.setFont(font);
        messagePanel.add(this.messageTextField);
        messagePanel.add(sendMessageButton);
        add(messagePanel, BorderLayout.SOUTH);


        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToServer();
            }
        });

        clientLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });
    }


    @Override
    public void sendMessageToServer() {
        String txt = messageTextField.getText();
        if(clientController.isConnectingToServer() && (txt != null && !txt.equals("")) ) {
            clientController.sendMessageToServer(client.getName()+ ": "+ txt + "\n");
            messageTextField.setText("");
        }
    }


    @Override
    public void showMessage(String message) {
        textArea.append(message);
    }


    @Override
    public void disconnectFromServer()  {
        if (clientController.isConnectingToServer()) {
            textArea.append(client.getName() + " is disconnecting from server \n");
            clientController.disconnectClient();
            authorizationPanel.setVisible(true);
        } else {
            this.textArea.append("The server is unavailable\n");
        }
    }

    public void connectToServer() {
        Client newClient = new Client(clientNameTextField.getText(), String.valueOf(clientPassTextField.getPassword()),
                ipTextField.getText(), Integer.parseInt(clientIdTextField.getText()));

        if (clientController.connectToServer(newClient)) {
            authorizationPanel.setVisible(false);
            client = newClient;
        } else {
            showMessage("Client not found");
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectFromServer();
        }
        super.processWindowEvent(e);
    }

    @Override
    public Client getClientData() {
        return client;
    }

    public ClientController getClientController() {
        return clientController;
    }
}
