import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by Rigen on 30.11.14.
 */
public class RoomThread implements Runnable {
    Thread thread;
    ArrayList<Player> playersList = new ArrayList<>();
    static final int ROUNDS = 3;
    static ArrayList<Card> deck = (ArrayList<Card>) Card.getDeck().clone();
    static int bet = 5;
    static int bank = 0;

    public RoomThread(ArrayList<Player> playersList) {
        this.playersList = playersList;
        thread = new Thread(this);
        this.thread.start();
    }

        @Override
    public void run() {

        try {
            writeToAllPlayers(Strings.START);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ROUNDS; i++) {
            try {
                switch (i) {
                    case 0:
                        writeToAllPlayers(Strings.FIRST_ROUND_START);
                        for (int k = 0; k < 3; k++) {
                            giveCardToAllPlayers();
                        }
                        break;
                    case 1:
                        writeToAllPlayers(Strings.SECOND_ROUND_START);
                        writeToAllPlayers(bank + "");
                        giveCardToAllPlayers();
                        break;
                    case 2:
                        writeToAllPlayers(Strings.THIRD_ROUND_START);
                        writeToAllPlayers(bank + "");
                        giveCardToAllPlayers();
                        break;
                }
                writeToAllPlayers(Strings.EXCHANGE);
                changeCards();
                tradeWithAllPlayers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            sendResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendResult() throws IOException {
        writeToAllPlayers(Strings.OPEN_HAND);
        ArrayList<String> results = new ArrayList<>();
        ArrayList<Player> playersListCopy = (ArrayList<Player>) playersList.clone();
        for (Player player : playersListCopy) {
            String string = player.getFromClient();
            ArrayList<Card> hand = new ArrayList<>();

            StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
            while (stringTokenizer.hasMoreTokens()) {
                string = stringTokenizer.nextToken();
                Card card = new Card(string);
                hand.add(card);
            }
            String s = "Player #" + (Integer.parseInt(player.name) + 1) + ": ";
            for (Card card : hand) {
                s += card.toString();
                if (hand.indexOf(card) == hand.size() - 1) {
                    s += ". ";
                } else {
                    s += ", ";
                }
            }
            writeToAllPlayers(s);
            results.add(Card.rateHand(hand));
        }
        writeToAllPlayers(Strings.ANNOUNCEMENT);
        ArrayList<String> resultsCopy = (ArrayList<String>) results.clone();
        Collections.sort(resultsCopy);
        ArrayList<Player> winners = new ArrayList<>();
        String s = resultsCopy.get(results.size() - 1);
        for (int i = resultsCopy.size() - 1; i > 0; i--) {
            if (resultsCopy.get(i).equals(s)) {
                winners.add(playersListCopy.get(results.indexOf(resultsCopy.get(i))));
            }
        }
        playersListCopy.removeAll(winners);
        for (Player player : winners) {
            player.writeToClient(Strings.WIN);
            player.writeToClient(bank / winners.size() + "");
        }
        for (Player player : playersListCopy) {
            player.writeToClient(Strings.LOSE);
        }
    }

    public void writeToAllPlayers(String string) throws IOException {
        for (Player player : playersList) {
            player.writeToClient(string);
        }
    }

    public void giveCardToAllPlayers() throws IOException {
        for (Player player : playersList) {
            giveCard(player);
        }
    }

    public static void giveCard(Player player) throws IOException {
        Random r1 = new Random();
        int cardIndex = r1.nextInt(deck.size() - 1);
        Card card = deck.get(cardIndex);
        player.writeToClient(card.getSuit() + "" + card.getValue());
        deck.remove(cardIndex);
    }

    public void changeCards() throws IOException {
        for (Player player : playersList) {
            String string = player.getFromClient();
            if (!string.equals(Strings.EXCHANGE_NULL)) {
                StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
                ArrayList<Card> cardList = new ArrayList<>();
                while (stringTokenizer.hasMoreTokens()) {
                    string = stringTokenizer.nextToken();
                    Card card = new Card(string);
                    cardList.add(card);
                }
                for (int i = 0; i < cardList.size(); i++) {
                    giveCard(player);
                }
                for (Card card : cardList) {
                    deck.add(card);
                }
            }
        }
    }

    public void tradeWithAllPlayers() throws IOException {
        int i = playersList.size();
        int n = 1;
        int roundBet = Integer.MAX_VALUE;
        while (i != 0) {
            i = playersList.size();
            for (Player player : playersList) {
                if (n == 1) {
                    player.writeToClient(Strings.TRADE);
                    player.writeToClient(Strings.BET_LAST + bet + ".");
                }
                boolean flag = false;
                while (!flag) {
                    if (player.bet < roundBet) {
                        int playerBet = Integer.parseInt(player.getFromClient());
                        if (playerBet >= bet) {
                            bet = playerBet;
                            player.bet = playerBet;
                            player.writeToClient(Strings.BET_OK);
                            flag = true;
                        } else {
                            player.writeToClient(Strings.BET_UNCORRECT);
                            player.writeToClient(Strings.BET_LAST + bet + ".");
                        }
                    } else {
                        flag = true;
                    }
                }
            }
            roundBet = bet;
            for (Player player : playersList) {
                if (player.bet == bet) {
                    i--;
                } else {
                    player.writeToClient(Strings.BET_AGAIN);
                    player.writeToClient(Strings.BET_LAST + bet + ".");
                }
            }
            n++;
        }
        bank += bet * playersList.size();
    }
}