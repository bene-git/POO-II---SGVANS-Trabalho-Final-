package Servidor;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Benedito Rodrigo
 */
public class MainServer {
    public static void main(String[] args) throws IOException {
        
        ServerSocket ss = new ServerSocket(12345);
       
        while (true) {
            new Thread(new Servidor(ss.accept())).start();
        }
    
    }
}
