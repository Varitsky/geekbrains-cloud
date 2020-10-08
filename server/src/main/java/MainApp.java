import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        deleteFileNamed("test.txt");

/** полезные методы:
 *
 * newFile.exists()
 * newFile.isDirectory()
 *
 *  следующие закомментированные строки не нужны
 */

//        File file = new File("server/test");
//        file.mkdirs(); // создание директорий
//        File testFile = new File("server/test/test.txt"); // создание определенного файла
//        try {
//            testFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Вывод списка файлов из адреса/path "file"
//        String[] files = file.list();
//        for (String s: files){
//            System.out.println(s);
//        }




//        serialisableVersion(); // РАБОТАЕТ
        binaryVersion();
    }

    private static void serialisableVersion() {
        try (ServerSocket sc = new ServerSocket(8085)) {
            System.out.println("Server is listening");
            try (Socket socket = sc.accept();
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                TempFileMessage cp = (TempFileMessage) ois.readObject();            // указываем Джаве что полученный набор байт является клоссом TempFileMessage

                PrintWriter pw = new PrintWriter("server/test.txt");   // создаем новый txt file

                byte[] bytes = cp.getBytes();
                for (byte b : bytes) {
                    pw.print((char) b);                     //пишем в новый txt
                    System.out.print((char) b);             // пишем в консоль
                }
                pw.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        listOfFiles("server");
    }

    /** полезные методы:
     *
     *  Для передачи файлов по очереди, ожидание завершения потока передачи предыдущего файла
     *  если вдруг передавать файлы МногоПоточно (thread)
     *
     * PipedInputStream in = null;
     * PipedOutputStream out = null;
     *
     * try {
     *     in = new PipedInputStream();
     *     out = new PipedOutputStream();
     *
     *     out.connect(in);
     *
     *     for (int i = 0; i < 100; i++){          (тут передаем числа от 1, до 100)
     *         out.write(i);                        (но в дальнейшем, если пригодится, тут будут файлы)
     *     }
     *
     *     int x;
     *     while ((x= in.read()) != -1) {
     *         System.out.println(x + " ");
     *     }
     *
     *     in.close();
     *     out.close();
     * } catch (IO exception) e{
     *     e.printStackTrace();
     * } finally {
     *     in.close();
     *     out.close();
     * }
     *
     */


    /**
     *  Массив файлов инпут
     *  ArrayList<InputStream> ali = new ArrayList<>();   //создаем массив файлов
     *  ali.add( new FileInputStream ("test1.txt");
     *  ali.add( new FileInputStream ("test2.txt");
     *  ali.add( new FileInputStream ("test3.txt");
     *
     *
     *
     * SequenceUnputStream in = new SequenceInputStream(Collections.enumerations(all));  //пихаем целый массив как один объект
     * int x;
     *
     * while ((x = in.read()) != -1) {
     *     sout ((Char) x)
     * }
     * in.close();
     */
    public static void binaryVersion(){
        try (ServerSocket sc = new ServerSocket(8085)) {
            System.out.println("Server is listening");
            try (Socket socket = sc.accept();
                 BufferedInputStream bis = new BufferedInputStream(socket.getInputStream())) {
                System.out.println("Client is recieved");

                byte[] arr = new byte[512];     //считывание за раз по 512 байт для ускорения получения файла
                PrintWriter pw = new PrintWriter("server/test.txt");   // создаем новый txt file

                int n;
                while ((n = bis.read()) != -1) {
                    System.out.print((char) n);   // побайтовое чтение
                    System.out.print(new String(arr, 0, n)); // чтение кучкой байтов НЕ РАБОТАЕТ
                    pw.print((char) n);                     //пишем в новый txt
                }
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listOfFiles("server");
    }

    /**
     * удаление файла по имени
     */

    public static void deleteFileNamed(String name) {
        String fileName = name;
        try {
            boolean result = Files.deleteIfExists(Paths.get(fileName));
            if (result) {
                System.out.println("Файл удалён");
            } else {
                System.out.println("не было такого файла");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Получение списка файлов директории
     */
    public static void listOfFiles(String name) {
        System.out.println("\n" + "Список файлов в папке " + name +":");
        File file = new File(name);
        // Вывод списка файлов из адреса/path "file" - name из конструктора
        String[] files = file.list();
        for (String s: files){
            System.out.println(s);
        }
    }
}

