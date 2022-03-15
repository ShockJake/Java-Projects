import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static ServerSocket ss;

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Write port: ");
        // Getting port to future server
        int port = sc.nextInt();
        sc.close();
        // Creating server socket
        ss = new ServerSocket(port);
        System.out.println("Opened server with: " + InetAddress.getLocalHost() + ":" + port);

        // Main loop
        while (true) {
            // Accepting connection
            Socket s = ss.accept();

            // Getting input and output streams
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            int b;

            // Reading data byte-by-byte
            while ((b = is.read()) != -1) {
                System.out.print((char) b);
                os.write(b);
            }
            s.close();
            break;
        }
    }
}
