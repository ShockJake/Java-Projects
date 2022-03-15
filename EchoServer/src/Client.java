import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static Socket socket = null;
    private static volatile boolean isActive = true;

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println(
                "Write ip adress to which you want to connect (by defalt local host is used -Testing mode-)\n --- Write 0 if you want to use a local host");
        String ipString;

        // Getting ip
        ipString = sc.next();
        if (ipString != "0") {
            ip = InetAddress.getByName(ipString);
        }
        System.out.println("Write port: ");

        // Getting port
        int port = sc.nextInt();

        // Creating socket and connecting to a server
        socket = new Socket(ip, port);
        System.out.println("Connected to: " + ip + ":" + port);
        OutputStream os = socket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String sLine;

        // Separate thread to print incoming message
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                if (isActive) {
                    byte[] bRes = new byte[200];
                    InputStream is;
                    int l;
                    try {
                        is = socket.getInputStream();
                        while (true) {
                            l = is.read(bRes);
                            System.out.print(new String(bRes, 0, l));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();

        // Reading data and sending it to the server
        while ((sLine = br.readLine()) != null) {
            sLine += "\n";
            if (sLine.equals("!quit\n")) {
                isActive = false;
                sc.close();
                br.close();
                socket.close();
            }
            os.write(sLine.getBytes());
            System.out.print("Sent <-- " + sLine);
        }

        // Closing streams
        sc.close();
        br.close();
        socket.close();
    }
}
