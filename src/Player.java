import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Rigen on 05.11.14.
 */



public class Player {
    Socket socket;
    String name;
    BufferedReader br;
    PrintWriter pw;
    int bet = 0;
//    RoomThread room;

    public Player(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.name = name;
//        this.room = room;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream());
    }

    public void writeToClient(String data) throws IOException {
        pw.println(data);
        pw.flush();
    }

    public String getFromClient() throws IOException {
        return br.readLine();
    }

    public String getFromServer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void getCard(){

    }
}
