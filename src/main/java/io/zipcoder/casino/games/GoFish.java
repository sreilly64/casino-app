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
    Player pcPlayer = new Player("PC", 0);
    Player humanPlayer;
    int humanPlayerBooks = 0, pcPlayerBooks = 0;
    int booksCompleted = humanPlayerBooks + pcPlayerBooks;
    List<Card> remainingDeck = new ArrayList<>();
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

            pcPlayerCard.add(currentDeck.get(0));
            currentDeck.remove(currentDeck.get(0));

            humanPlayerCard.add(currentDeck.get(0));
            currentDeck.remove(currentDeck.get(0));
        }
        this.remainingDeck = currentDeck;
    }

    @Override
    public void getWinner() {
        if (humanPlayerBooks > pcPlayerBooks) {
            System.out.println("Your  Score : " + humanPlayerBooks);
            System.out.println("pcPlayer  Score : " + pcPlayerBooks);
            System.out.println("Congratulations, The winner is : " + humanPlayer.getName());
        } else {
            System.out.println("pcPlayer  Score : " + pcPlayerBooks);
            System.out.println("Your  Score : " + humanPlayerBooks);
            System.out.println(" The winner is pcPlayer, Nice Play !!!");
        }
    }

    public Boolean isHumanBookComplete() {

        Map<Card.Rank, Integer> rankMap = new HashMap<>();
        for (Card card : humanPlayerCard) {
            if (null != card) {
                if (rankMap.containsKey(card.getRank())) {
                    Integer value = rankMap.get(card.getRank());
                    rankMap.put(card.getRank(), value+1);
                } else {
                    rankMap.put(card.getRank(), 1);
                }
            }
        }

        Set<Card.Rank> rankSet = rankMap.keySet();
        Iterator<Card.Rank> iterator = rankSet.iterator();
        while(iterator.hasNext()){
            Card.Rank rank = iterator.next();
            if (rankMap.get(rank) == 4) {
                Iterator<Card> removeIterator = humanPlayerCard.iterator();
                while(removeIterator.hasNext()){
                    Card next = removeIterator.next();
                    if(next.getRank().equals(rank)){
                        removeIterator.remove();
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Boolean isPcBookComplete() {

        Map<Card.Rank, Integer> rankMap = new HashMap<>();
        for (Card card : pcPlayerCard) {
            if (null != card) {
                if (rankMap.containsKey(card.getRank())) {
                    rankMap.put(card.getRank(), rankMap.get(card.getRank()) + 1);
                } else {
                    rankMap.put(card.getRank(), 1);
                }
            }
        }

        Set<Card.Rank> rankSet = rankMap.keySet();
        for (Card.Rank rank : rankSet) {
            if (rankMap.get(rank) == 4) {
                Iterator<Card> removeIterator = pcPlayerCard.iterator();
                while(removeIterator.hasNext()){
                    Card next = removeIterator.next();
                    if(next.getRank().equals(rank)){
                        removeIterator.remove();
                    }
                }
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

    private void letsPlayGoFish(Player hPlayer, Player pPlayer) {
        this.opponentPlayer = (pPlayer.getName().equals("PC") ? pcPlayer : humanPlayer);
        Card card;
        if (!hPlayer.getName().equals("PC")) {
            currentPlayerCards = humanPlayerCard;
        } else {
            currentPlayerCards = pcPlayerCard;
        }

        Card cardAsked = createRandomCard(hPlayer);
        boolean flag;
        do {
            if (null != cardAsked && askCard(opponentPlayer, cardAsked.rank)) {
                flag = true;
                if (!hPlayer.getName().equals("PC")) {
                    if (isHumanBookComplete()) {
                        humanPlayerBooks++;
                    }
                } else {
                    if (isPcBookComplete()) {
                        pcPlayerBooks++;
                    }
                }

            } else {
                flag = false;
                break;
            }
            cardAsked = createRandomCard(hPlayer);
        } while (flag);

        Card drawnCard = drawTopCard();
        if(null != drawnCard) {
            do {
                if (!hPlayer.getName().equals("PC")) {
                    humanPlayerCard.add(drawnCard);
                    currentPlayerCards = humanPlayerCard;
                    if (isHumanBookComplete()) {
                        humanPlayerBooks++;
                    }
                } else {
                    pcPlayerCard.add(drawnCard);
                    currentPlayerCards = pcPlayerCard;
                    if (isPcBookComplete()) {
                        pcPlayerBooks++;
                    }
                }
                remainingDeck.remove(drawnCard);

            } while (null != cardAsked && cardAsked.equals(drawnCard));
        }

        this.opponentPlayer = hPlayer;
        this.currentPlayer = nextTurn(hPlayer);

    }

    private void takeAllSameCards(Player currentPlayer, Player opponentPlayer, Card.Rank rank) {
        List<Card> opponentPlayerCards = playerHandMap.get(opponentPlayer);
        List<Card> currentPlayerCards = playerHandMap.get(currentPlayer);

        Iterator<Card> iterator = opponentPlayerCards.iterator();
        while(iterator.hasNext()){
            Card card = iterator.next();
            if(rank.equals(card.getRank())){
                currentPlayerCards.add(card);
                iterator.remove();
            }
        }
    }

    @Override
    public void startGame(Player player) {
        this.humanPlayer = player;
        this.currentPlayer = player;
        dealCards(7);
        playersHand();
        this.opponentPlayer = pcPlayer;

        while (booksCompleted < 13 || (remainingDeck.size() != 0 && (pcPlayerCard.size() != 0 || humanPlayerCard.size() != 0))) {
            letsPlayGoFish(currentPlayer, opponentPlayer);
            booksCompleted = this.humanPlayerBooks + this.pcPlayerBooks;

            if(pcPlayerCard.size() == 0 && humanPlayerCard.size()!= 0){
                takeCardsFromDeck(pcPlayer);
            }
            else if(humanPlayerCard.size() == 0 && pcPlayerCard.size()!= 0){
                takeCardsFromDeck(humanPlayer);
            }
        }

        getWinner();
    }

    private void takeCardsFromDeck(Player player) {
        if(!player.getName().equals("PC")){
            if(remainingDeck.size()>=7) {
                for (int i = 0; i < 7; i++) {
                    humanPlayerCard.add(remainingDeck.get(0));
                    remainingDeck.remove(0);
                }
            }
        }
        else {
            if(remainingDeck.size()>=7) {
                for (int i = 0; i < 7; i++) {
                    pcPlayerCard.add(remainingDeck.get(0));
                    remainingDeck.remove(0);
                }
            }
        }
    }

    private Boolean askCard(Player opponentplayer, Card.Rank rank) {
        Set<Map.Entry<Player, List<Card>>> setOfCards = playerHandMap.entrySet();

        for (Map.Entry<Player, List<Card>> entry : setOfCards)
            if (entry.getKey().getName().equals(opponentplayer.getName())) {
                List<Card> cardList = entry.getValue();
                for(Card card : cardList){
                    if (card.getRank().equals(rank)) {
                        takeAllSameCards(currentPlayer, opponentPlayer, rank);
                        return true;
                    }
                }
            }
        return false;
    }

    private Player nextTurn(Player currentPlayer) {
        if (currentPlayer.getName().equals("PC")) {
            currentPlayer = humanPlayer;
        } else {
            currentPlayer = pcPlayer;
        }
        return currentPlayer;
    }

    private Card createRandomCard(Player currentPlayer) {
        Card card = null;
        Random random = new Random();
        if(!currentPlayer.getName().equals("PC")) {
            //card  = humanPlayerCard.get(0);
            if(humanPlayerCard.size() > 0) {
                card = humanPlayerCard.get(random.nextInt(humanPlayerCard.size()));
            }
        } else{
            //card  = pcPlayerCard.get(0);
            if(pcPlayerCard.size() > 0) {
                card = pcPlayerCard.get(random.nextInt(pcPlayerCard.size()));
            }
        }
        return card;
    }
}
