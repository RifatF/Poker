import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {
    static final String HOST = "127.0.0.1";
    static final int PORT = 1234;
    static BufferedReader br;
    static PrintWriter pw;
    static ArrayList<Card> hand = new ArrayList<>();
    static int bank = 100;
    static boolean flagContinue = false;
    static int allPlayersBank = 0;
    static int bet = 0;


    public static void main(String[] args)
            throws IOException, InterruptedException {

        System.out.println("Connecting to server on " + HOST + ":" + PORT);
        Socket s = new Socket(HOST, PORT);
        System.out.println("Connection established!");
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream());

        for (int i = 0; i < 3; i++) {
            getCards(i);
            System.out.println(br.readLine());
            exchangeCards();
            bet();
        }
        showResult();

    }

    public static void showResult() throws IOException {
        String s = "";
        for (Card card : hand) {
            s+= card.getSuit() + "" + card.getValue() + " ";
        }
        pw.println(s);
        pw.flush();

        String string = br.readLine();;

        do {
            System.out.println(string);
            string = br.readLine();
        } while (!string.equals(Strings.ANNOUNCEMENT));

        string = br.readLine();
        System.out.println(string);
        if (string.equals(Strings.WIN)){
            string = br.readLine();
            int i = (int) Double.parseDouble(string);
            bank += i;
        }
        System.out.println("Ваш счёт: " + bank + ".");
    }

    public static void bet() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        while (!flag) {
            String string = br.readLine();
            if (string.equals(Strings.TRADE)) {
                System.out.println(string);
                System.out.println(br.readLine());
                boolean flag2 = false;
                while (!flag2) {
                    String s = scanner.nextLine();
                    if (s.matches("\\d{1,3}") && Integer.parseInt(s) < bank) {
                        bet = Integer.parseInt(s);
                        pw.println(bet);
                        pw.flush();
                        flag2 = true;
                    } else {
                        System.out.println(Strings.BET_REWRITE);
                    }
                }
            } else {
                if (string.equals(Strings.BET_OK)) {
                    System.out.println(string);
                    continue;
                } else {
                    if ((string.equals(Strings.BET_UNCORRECT)
                        || string.equals(Strings.BET_AGAIN))) {
                        System.out.println(string);
                        System.out.println(br.readLine());
                        boolean flag2 = false;
                        while (!flag2) {
                            String s = scanner.nextLine();
                            if (s.matches("\\d{1,3}") && Integer.parseInt(s) < bank) {
                                bet = Integer.parseInt(s);
                                pw.println(bet);
                                pw.flush();
                                flag2 = true;
                            } else {
                                System.out.println(Strings.BET_REWRITE);
                            }
                        }
                    } else {
                        if (string.matches(Strings.REGEX)) {
                            System.out.println(string);
                            Client.bank -= Client.bet;
                            allPlayersBank = Integer.parseInt(br.readLine());
                            flag = true;
                        } else {
                            if(string.matches(Strings.OPEN_HAND)) {
                                System.out.println(string);

                                Client.bank -= Client.bet;
                                flag = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void getCard(String s) {
        Card card = new Card(s);
        hand.add(card);
    }

    public static void getCards(int i) throws IOException, InterruptedException {
        boolean flag = false;
        while (!flag) {
            String string = br.readLine();
            if (string.matches("\\d{2,3}")) {
                Client.getCard(string);
            } else {
                System.out.println(string);
            }
            if (hand.size() == i + 3) {
                System.out.println(showPlayerStatus());
                flag = true;
            }
        }
    }

    public static void removeCards(String s) {
        StringTokenizer stringTokenizer = new StringTokenizer(s, " ");
        ArrayList<Card> cardList = (ArrayList<Card>) hand.clone();
        String string = "";
        while (stringTokenizer.hasMoreTokens()) {
            int i = Integer.parseInt(stringTokenizer.nextToken());
            Card card = hand.get(i - 1);
            cardList.remove(card);
            string = string + card.getSuit() + card.getValue() + " ";
        }
        hand = cardList;
        pw.println(string);
        pw.flush();
    }

    public static String showPlayerStatus() {
        String s = "";
        s += "Счёт: " + bank + ". ";
        s += "Банк: " + allPlayersBank + ". ";
        int i = 1;
        s += "Рука: ";
        for (Card card : hand) {
            s += i + ") " + card.toString();
            if (hand.indexOf(card) == hand.size() - 1) {
                s += ". ";
            } else {
                s += ", ";
            }
            i++;
        }
        return s;
    }

    public static void exchangeCards() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        while (!flag) {
            String s = scanner.nextLine();
            if(!s.matches("0 *")) {
                if (s.matches("([1-5]\\s+){0,4}([1-5]\\s*)")) {
                    flag = true;
                    removeCards(s);
                    int i = 0;
                    int length = s.length() == 1 ? 1 : (s.length() / 2 + 1);
                    while (i < length) {
                        boolean flag2 = false;
                        while (!flag2) {
                            String string = br.readLine();
                            if (string.matches("\\d{2,3}")) {
                                Client.getCard(string);
                                i++;
                                flag2 = true;
                                flag = true;
                            } else {
                                System.out.println(string);
                            }
                        }
                    }
                    System.out.println(showPlayerStatus());
                } else {
                    System.out.println(Strings.EXCHANGE_UNCORRECT);
                }
            } else {
                flag = true;
                pw.println(Strings.EXCHANGE_NULL);
                pw.flush();
            }
        }
    }
}