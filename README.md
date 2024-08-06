# Chat-Application-System

## Overview

This project consists of a simple chat server and a chat client with a graphical user interface (GUI). The chat server handles incoming connections and broadcasts messages to all connected clients. The chat client connects to the server, allowing users to send and receive messages.

## Server

### `ChatServer.java`

The `ChatServer` class is a basic server that listens for client connections on port `12345`. It handles multiple clients concurrently, broadcasting messages received from any client to all connected clients.

#### Features
- Accepts multiple client connections.
- Broadcasts received messages to all clients.
- Handles client disconnections gracefully.

### Running the Server
1. Compile the server code:
    ```sh
    javac ChatServer.java
    ```
2. Run the server:
    ```sh
    java ChatServer
    ```

## Client

### `ChatClientGUI.java`

The `ChatClientGUI` class is a Swing-based GUI application that connects to the chat server. Users can send and receive messages, change their nickname, and exit the chat.

#### Features
- User-friendly GUI for sending and receiving messages.
- Allows users to change their nickname with the `/nick <new nickname>` command.
- Users can leave the chat using the `/exit` command.

### Running the Client
1. Compile the client code:
    ```sh
    javac ChatClientGUI.java
    ```
2. Run the client:
    ```sh
    java ChatClientGUI
    ```
3. Enter the server IP address and port (`12345`), and provide a nickname when prompted.

## Configuration

- **Server Address**: Update the `SERVER_ADDRESS` constant in `ChatClientGUI.java` with the IP address of the machine running the `ChatServer`.
- **Port Number**: The default port for both server and client is `12345`. Change it in both files if needed.

## Dependencies

- Java Development Kit (JDK) 8 or later.

## License

This project is licensed under the MIT License.
