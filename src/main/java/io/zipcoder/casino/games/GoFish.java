package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.player.Player;

import java.util.*;

import static io.zipcoder.casino.Card.getNewDeck;

public class GoFish extends CardGame {
    Map<Player, List<Card>> playerHandMap = new HashMap<>();
    List<Card> humanPlayerCard = new ArrayList<>();
    List<Card> pcPlayerCard = new ArrayList<>();
    Player currentPlayer;
    Player opponentPlayer;
    Player pcPlayer=new Player("PC",0);
    Player humanPlayer;
    int humanPlayerBooks=0, pcPlayerBooks=0;
    int booksCompleted=humanPlayerBooks+pcPlayerBooks;
    List<Card> remainingDeck=new ArrayList<>();
    List<Card> currentPlayerCards = new ArrayList<>();

    private Map<Player, List<Card>> playersHand() {
        playerHandMap.put(humanPlayer, humanPlayerCard);
        playerHandMap.put(pcPlayer, pcPlayerCard);
        return playerHandMap;
    }

    public GoFish(List<Card> currentDeck) {
        super(currentDeck);
    }

    @Override
    void dealCards(Integer numOfCards) {
        currentDeck = getNewDeck();
        Random random = new Random();

        for (int i = 0; i < numOfCards; i++) {
            Card card1 = currentDeck.get(random.nextInt(currentDeck.size()));
            pcPlayerCard.add(card1);
            currentDeck.remove(card1);

            Card card2 = currentDeck.get(random.nextInt(currentDeck.size()));
            humanPlayerCard.add(card2);
            currentDeck.remove(card2);

        }
        remainingDeck = currentDeck;
    }

    @Override
    public void getWinner() {
        if (humanPlayerBooks > pcPlayerBooks) {
            System.out.println(humanPlayerBooks);
            System.out.println("Congratulations, The winner is " + humanPlayer.getName());
        } else {
            System.out.println(pcPlayerBooks);
            System.out.println("Congratulations, The winner is pcPlayer");
        }
    }

    public Boolean isHumanBookComplete() {
        Boolean completed=false;
        Map<Card.Rank, Integer> rankMap = new HashMap<>();
        for(Card card : humanPlayerCard) {
            if(null != card) {
                if (rankMap.containsKey(card.getRank())) {
                    rankMap.put(card.getRank(), rankMap.get(card.getRank()) + 1);
                } else {
                    rankMap.put(card.getRank(), 1);
                }
            }
        }

        Set<Card.Rank> rankSet = rankMap.keySet();
        for(Card.Rank rank : rankSet) {
            if(rankMap.get(rank) == 4) {
                humanPlayerBooks++;
                completed =true;

            }
            else{

            } completed =false;
        }
       return false;
    }

    public Boolean isPcBookComplete() {

        Map<Card.Rank, Integer> rankMap = new HashMap<>();
        for(Card card : pcPlayerCard) {
            if(null != card) {
                if (rankMap.containsKey(card.getRank())) {
                    rankMap.put(card.getRank(), rankMap.get(card.getRank()) + 1);
                } else {
                    rankMap.put(card.getRank(), 1);
                }
            }
        }

        Set<Card.Rank> rankSet = rankMap.keySet();
        for(Card.Rank rank : rankSet) {
            if(rankMap.get(rank) == 4) {
                pcPlayerBooks++;
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetGame() {
        startGame(humanPlayer);
    }

    @Override
    public void quitGame() {
        getWinner();
    }

    @Override
    public String getGameName() {
        return "Go Fish";
    }

    private void letsPlayGoFish(Player currentPlayer, Player opponentPlayer){
        opponentPlayer = ( opponentPlayer.getName().equals("PC") ? pcPlayer : humanPlayer);
        Card card;
        if(!currentPlayer.getName().equals("PC")){
            currentPlayerCards=humanPlayerCard;
        }else{
            currentPlayerCards=pcPlayerCard;
        }

        do {
            card = createRandomCard(currentPlayer);

            if (askCard(opponentPlayer, card.rank)) {
                if(!currentPlayer.getName().equals("PC")){
                    humanPlayerCard.add(card);
                    pcPlayerCard.remove(card);

                    if (isHumanBookComplete()) {
                        humanPlayerBooks++;
                    }
                }
                else {
                    pcPlayerCard.add(card);
                    humanPlayerCard.remove(card);
                    if (isPcBookComplete()) {
                        pcPlayerBooks++;
                    }
                }

            } else{
                break;
            }

        } while(askCard(opponentPlayer, card.rank));

        Card drawnCard =drawTopCard();
        do{
            if(!currentPlayer.getName().equals("PC")){
                humanPlayerCard.add(drawnCard);
                currentPlayerCards=humanPlayerCard;
                if (isHumanBookComplete()) {
                    humanPlayerBooks++;
                }
            }
            else {
                pcPlayerCard.add(drawnCard);
                currentPlayerCards=pcPlayerCard;
                if (isPcBookComplete()) {
                    pcPlayerBooks++;
                }
            }
            remainingDeck.remove(drawnCard);
            drawnCard = drawTopCard();
        } while(currentPlayerCards.contains(drawnCard));

        this.opponentPlayer = currentPlayer;
        this.currentPlayer = nextTurn(currentPlayer);

    }

    @Override
    public void startGame(Player player) {
        this.humanPlayer=player;
        this.currentPlayer=player;
        dealCards(7);
        playersHand();
        opponentPlayer = pcPlayer;
        while((remainingDeck.size() > 0)) {
            letsPlayGoFish( currentPlayer, opponentPlayer);
            booksCompleted=this.humanPlayerBooks + this.pcPlayerBooks;
        }
        getWinner();
    }

    private Boolean askCard(Player player, Card.Rank rank) {
        Set<Map.Entry<Player, List<Card>>> setOfCards = playerHandMap.entrySet();
        for (Map.Entry<Player, List<Card>> entry : setOfCards)
            if (entry.getKey().getName().equals(player.getName())) {
                if (entry.getValue().contains(rank)) {
                    return true;
                }
            }
        return false;
    }

    private Player nextTurn(Player currentPlayer) {
        if (currentPlayer.getName().equals("PC")){
            currentPlayer = humanPlayer;
        }
        else {
            currentPlayer = pcPlayer;
        }
        return currentPlayer;
    }

    private Card createRandomCard(Player currentPlayer) {
        Card card;
        //Random random = new Random();
        //String s = selectRandomCardRank();
//        card = getNewDeck().get(0);
//        return card;
      Random random = new Random();
        card = currentPlayerCards.get(random.nextInt(currentPlayerCards.size()));
        return card;
    }

    private static String selectRandomCardRank() {
        String cardRank;
        Random random = new Random();
        int r = 1 + random.nextInt(13);
        switch (r) {
            case 1:
                cardRank = "1";
                break;
            case 2:
                cardRank = "2";
                break;
            case 3:
                cardRank = "3";
                break;
            case 4:
                cardRank = "4";
                break;
            case 5:
                cardRank = "5";
                break;
            case 6:
                cardRank = "6";
                break;
            case 7:
                cardRank = "7";
                break;
            case 8:
                cardRank = "8";
                break;
            case 9:
                cardRank = "9";
                break;
            case 10:
                cardRank = "10";
                break;
            case 11:
                cardRank = "11";
                break;
            case 12:
                cardRank = "12";
                break;
            default:
                cardRank = "13";
                break;
        }
        return cardRank;
    }

    public static void main(String[] args) {
        System.out.println("Go Fish");
        List<Card> newDeck = new ArrayList<>();
        newDeck=getNewDeck();
        GoFish go = new GoFish(newDeck);
        Player humanPlayer=new Player("Joe",20);
        go.startGame(humanPlayer);
    }
}
