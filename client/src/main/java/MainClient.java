import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class MainClient {

    public static void main(String[] args) {
        bynaryClient();
//        serialisableVersion();
    }

    public static void serialisableVersion(){
        try(Socket socket = new Socket("localhost", 8085)){
            ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
            TempFileMessage tmp = new TempFileMessage(Paths.get("client/example.txt"));
            ous.writeObject(tmp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void bynaryClient(){
        try(Socket socket = new Socket("localhost", 8085)){
            byte[] bytes = {65, 66, 67, 68, 69, 70};
            socket.getOutputStream().write(bytes);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
