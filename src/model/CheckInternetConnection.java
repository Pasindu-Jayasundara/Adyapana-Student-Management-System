package model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CheckInternetConnection {

    public static boolean isInternetAvailable() {
        // Create a socket object and set the timeout to 3 seconds
        Socket socket = new Socket();
        int timeout = 3000;

        try {
            // Try connecting to a well-known, fast and reliable host (Google DNS)
            InetSocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
            socket.connect(socketAddress, timeout);
            socket.close();
            return true;
        } catch (IOException e) {
            // An exception occurred, indicating that the internet connection is not available
            return false;
        }
    }
    
}
