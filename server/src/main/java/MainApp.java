import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {
    public static void main(String[] args) {
//        binaryVersion();
        serialisableVersion();
    }

    private static void serialisableVersion() {
        try (ServerSocket sc = new ServerSocket(8085)) {
            System.out.println("Server is listening");
            try (Socket socket = sc.accept();
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                TempFileMessage cp = (TempFileMessage) ois.readObject();
                byte[] bytes = cp.getBytes();
                for (byte b : bytes) {
                    System.out.print((char) b);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void binaryVersion() {
        try (ServerSocket sc = new ServerSocket(8085)) {
            System.out.println("Server is listening");
            try (Socket socket = sc.accept();
                 BufferedInputStream bis = new BufferedInputStream(socket.getInputStream())) {
                System.out.println("Clinet is recieved");
                int n;
                while ((n = bis.read()) != -1) {
                    System.out.print((char) n);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

