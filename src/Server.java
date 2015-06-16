import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static final int PORT = 1234;
    static int i = 0;
    static ArrayList<Player> playersList = new ArrayList<>();
    static int testLimit = 2;

    public static void main(String[] args) throws IOException {

        System.out.println("Started server on " + PORT);
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Waiting for connection... ");
        Player player = null;
        Socket s = ss.accept();
        while (true) {
            while (i != testLimit) {
                player = new Player(s, String.valueOf(i));
                playersList.add(player);
                i++;
                System.out.println("Player #" + i + " connected.");
                if (i == testLimit) {
                    new RoomThread(playersList);
                    i = 0;
                    playersList = new ArrayList<>();;
                }
                s = ss.accept();
            }

        }
    }
}