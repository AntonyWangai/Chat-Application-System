import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatClientGUI extends JFrame {
    private static final String SERVER_ADDRESS = "address of server"; // Replace with actual server IP address
    private static final int SERVER_PORT = 12345;
    private String nickname;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private JTextArea messageArea;
    private JTextField inputField;

    public ChatClientGUI() {
        setTitle("Chat Client");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClientGUI().start());
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            nickname = JOptionPane.showInputDialog(this, "Enter your nickname:");
            if (nickname == null || nickname.trim().isEmpty()) {
                System.exit(0);
            }
            out.println(nickname + " has joined the chat");

            new Thread(new ReceiveMessagesHandler()).start();
        } catch (UnknownHostException e) {
            showError("Unknown host: " + SERVER_ADDRESS);
        } catch (IOException e) {
            showError("I/O error: Could not connect to server");
        }
    }

    private void sendMessage(String message) {
        if (message.startsWith("/nick ")) {
            changeNickname(message);
        } else if (message.equals("/exit")) {
            exitChat();
        } else {
            out.println(nickname + ": " + message);
        }
    }

    private void changeNickname(String message) {
        String[] parts = message.split(" ", 2);
        if (parts.length == 2 && !parts[1].trim().isEmpty()) {
            String newNickname = parts[1].trim();
            out.println(nickname + " changed nickname to " + newNickname);
            nickname = newNickname;
        } else {
            showMessage("Invalid nickname. Use /nick <new nickname>");
        }
    }

    private void exitChat() {
        out.println(nickname + " has left the chat");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void showMessage(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class ReceiveMessagesHandler implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    showMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
