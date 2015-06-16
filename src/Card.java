import java.util.*;

/**
 * Created by Rigen on 23.11.14.
 */

class Combination {

    public static void sort(ArrayList<Card> hand) {
        boolean flag = false;
        while (!flag) {
            int number = 0;
            for (int i = 0; i < hand.size() - 1; i++) {
                if (hand.get(i).getValue() > hand.get(i + 1).getValue()) {
                    Card card = hand.get(i + 1);
                    hand.set(i + 1, hand.get(i));
                    hand.set(i, card);
                    number++;
                }
            }
            if (number == 0)
                flag = true;
        }
    }

    public static Map<Integer, Integer> count(ArrayList<Card> hand) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Card card : hand) {
            if (map.get(card.getValue()) != null) {
                map.put(card.getValue(), map.get(card.getValue()) + 1);
            } else {
                map.put(card.getValue(), 1);
            }
        }
        return map;
    }

    /**
     * For the following methods hand should be sorted
     * and they need a map with the amount of the value of cards.
     */

    public static String isRoyalFlush(ArrayList<Card> hand) {
        int suit = hand.get(0).getSuit();
        boolean suitSame = true;
        for (Card card : hand) {
            if (card.getSuit() != suit) {
                suitSame = false;
                break;
            }
        }
        if (!suitSame) {
            return null;
        }
        if (hand.get(0).getValue() == 10 && hand.get(hand.size() - 1).getValue() == 14) {
            return hand.get(4).getValue() + "";
        } else {
            return null;
        }
    }

    public static String isStraightFlush(ArrayList<Card> hand) {
        int suit = hand.get(0).getSuit();
        boolean suitSame = true;
        for (Card card : hand) {
            if (card.getSuit() != suit) {
                suitSame = false;
                break;
            }
        }
        if (!suitSame) {
            return null;
        }
        boolean flag = true;
        for (int i = 0; i < hand.size() - 1; i++) {
            if (hand.get(i + 1).getValue() - hand.get(i).getValue() != 1) {
                flag = false;
                break;
            }
        }
        if (flag)
            return hand.get(hand.size() - 1) + "";
        else
            return null;
    }

    public static String isQuads(ArrayList<Card> hand, Map<Integer, Integer> map) {
        for (Integer i : map.values()) {
            if (i == 4) {
                if (map.get(hand.get(0).getValue()) == 4) {
                    return hand.get(0).getValue() + "";
                } else {
                    return hand.get(1).getValue() + "";
                }
            }

        }
        return null;
    }

    public static String isFullHouse(ArrayList<Card> hand, Map<Integer, Integer> map) {
        boolean three = false;
        boolean two = false;
        for (Integer i : map.values()) {
            if (i == 2) {
                two = true;
            } else {
                if (i == 3) {
                    three = true;
                }
            }
        }
        if (three && two) {
            for (Card card : hand) {
                if (map.get(card.getValue()) == 3) {
                    return card.getValue() + "";
                }
            }
        }
        return null;
    }

    public static String isFlush(ArrayList<Card> hand) {
        int suit = hand.get(0).getSuit();
        boolean suitSame = true;
        for (Card card : hand) {
            if (card.getSuit() != suit) {
                suitSame = false;
                break;
            }
        }
        if (suitSame) {
            return hand.get(4).getValue() + "";
        } else {
            return null;
        }
    }

    public static String isStraight(ArrayList<Card> hand) {
        boolean flag = true;
        for (int i = 0; i < hand.size() - 1; i++) {
            if (hand.get(i + 1).getValue() - hand.get(i).getValue() != 1) {
                flag = false;
                break;
            }
        }
        if (flag) {
            return hand.get(4).getValue() + "";
        } else {
            return null;
        }
    }

    public static String isSet(ArrayList<Card> hand, Map<Integer, Integer> map) {
        for (Integer i : map.values()) {
            if (i == 3) {
                for (Card card : hand) {
                    if (map.get(card.getValue()) == 3) {
                        return card.getValue() + "";
                    }
                }
            }
        }
        return null;
    }

    public static String isTwoPairs(ArrayList<Card> hand, Map<Integer, Integer> map) {
        int k = 0;
        for (Integer i : map.values()) {
            if (i == 2)
                k++;
        }
        if (k == 2) {
            int first = 0;
            int second = 0;
            for (Card card : hand) {
                if (map.get(card.getValue()) == 2) {
                    if (first != 0) {
                        first = card.getValue();
                    } else {
                        if (card.getValue() != first) {
                            second = card.getValue();
                            if (first > second) {
                                return first + "";
                            } else {
                                return second + "";
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String isOnePairs(ArrayList<Card> hand, Map<Integer, Integer> map) {
        for (Integer i : map.values()) {
            if (i == 2) {
                for (Card card : hand) {
                    if (map.get(card.getValue()) == 2) {
                        return card.getValue() + "";
                    }
                }
            }
        }
        return null;
    }
}

public class Card {
    private int suit;
    private int value;
    private static ArrayList<Card> deck = new ArrayList<>();

    static {
        for (int i = 0; i <= 3; i++) {
            for (int j = 2; j <= 14; j++) {
                deck.add(new Card(i, j));
            }
        }
    }

    public Card() {
    }

    public Card(int suit, int value) {

        this.suit = suit;
        this.value = value;
    }

    public Card(String s) {
        this.suit = Integer.parseInt(s.substring(0, 1));
        this.value = Integer.parseInt(s.substring(1));
    }


    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ArrayList<Card> getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        String result = "";
        if (value < 11)
            result += value;
        else {
            switch (value) {
                case 11:
                    result += "J";
                    break;
                case 12:
                    result += "Q";
                    break;
                case 13:
                    result += "K";
                    break;
                case 14:
                    result += "T";
                    break;
            }
        }
        switch (suit) {
            case 0:
                result += "♥";
                break;
            case 1:
                result += "♦";
                break;
            case 2:
                result += "♣";
                break;
            case 3:
                result += "♠";
                break;
        }
        return result;
    }

    public static String rateHand(ArrayList<Card> hand) {
        ArrayList<Card> handCopy = (ArrayList<Card>) hand.clone();
        Combination.sort(handCopy);
        Map<Integer, Integer> map = Combination.count(handCopy);
        String s = Combination.isRoyalFlush(handCopy);
        if (s != null)
            return "9 " + s;
        s = Combination.isStraightFlush(handCopy);
        if (s != null)
            return "8 " + s;
        s = Combination.isQuads(handCopy, map);
        if (s != null)
            return "7 " + s;
        s = Combination.isFullHouse(handCopy, map);
        if (s != null)
            return "6 " + s;
        s = Combination.isFlush(handCopy);
        if (s != null)
            return "5 " + s;
        s = Combination.isStraight(handCopy);
        if (s != null)
            return "4 " + s;
        s = Combination.isSet(handCopy, map);
        if (s != null)
            return "3 " + s;
        s = Combination.isTwoPairs(handCopy, map);
        if (s != null)
            return "2 " + s;
        s = Combination.isOnePairs(handCopy, map);
        if (s != null)
            return "1 " + s;
        String handMaxValue = handCopy.get(handCopy.size() - 1) + "";
        return "0 " + handMaxValue;
    }

    public static void main(String[] args) {
        ArrayList<Card> hand1 = new ArrayList<>();
        ArrayList<Card> hand2 = new ArrayList<>();


        hand1.add(new Card("09"));
        hand1.add(new Card("19"));
        hand1.add(new Card("04"));
        hand1.add(new Card("014"));
        hand1.add(new Card("113"));


        hand2.add(new Card("07"));
        hand2.add(new Card("17"));
        hand2.add(new Card("28"));
        hand2.add(new Card("110"));
        hand2.add(new Card("25"));

        ArrayList<String> results = new ArrayList<>();
        results.add(Card.rateHand(hand1));
        results.add(Card.rateHand(hand2));
        ArrayList<String> resultsCopy = (ArrayList<String>) results.clone();
        Collections.sort(resultsCopy);

    }
}
