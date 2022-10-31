package org.example.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Web {
    public enum Type{SERVER, CLIENT}

    private final Type type;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final BufferedReader consoleReader;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Web(Type tp, int port, String host) {
        type = tp;
        consoleReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            if (type == Type.CLIENT) {
                InitClient(host, port);
            } else {
                InitServer(port);
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            try {
                clientSocket.close();
                consoleReader.close();
                in.close();
                out.close();
            }
            catch (IOException ex)
            {
                System.err.println(ex.getMessage());
            }
        }

    }
    public boolean Send(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public String Read() {
        try
        {
            return in.readUTF();
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
            return "";
        }
    }

    public boolean SendObject(Object ob) {
        try
        {
            out.writeObject(ob);
            out.flush();
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    public Object ReadObject() {
        try
        {
            return in.readObject();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    private void InitClient(String host, int port) throws IOException {
        if (type != Type.CLIENT)
            System.err.println("Error in type");

        clientSocket = new Socket("localhost", port);
        out = new ObjectOutputStream(clientSocket.getOutputStream()); // почему зависит от порядка?
        in = new ObjectInputStream(clientSocket.getInputStream());
    }
    private void InitServer(int port) throws IOException {
        if (type != Type.SERVER)
            System.err.println("Error in type");

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream()); // почему зависит от порядка?
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void close() {
        try {
            clientSocket.close();
            consoleReader.close();
            in.close();
            out.close();
            if (serverSocket == null)
                serverSocket.close();
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

}
