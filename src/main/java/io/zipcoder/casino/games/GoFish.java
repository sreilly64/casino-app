package io.zipcoder.casino.games;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.CasinoDriver;
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

    public GoFish(List<Card> currentDeck) {
        super(currentDeck);
        this.humanPlayer = new Player("DEFAULT_PLAYER", 0);
        this.pcPlayer = new Player("PC", 0);
        this.currentPlayer = humanPlayer;
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

    Map<Player, List<Card>> playersHand() {
        playerHandMap.put(humanPlayer, humanPlayerCard);
        playerHandMap.put(pcPlayer, pcPlayerCard);
        return playerHandMap;
    }

    @Override
    public void startGame(Player player) {
        this.humanPlayer = player;
        this.currentPlayer = player;
        System.out.println("Hello " + humanPlayer.getName() + "!!");
        dealCards(7);
        playersHand();
        this.opponentPlayer = pcPlayer;


        while (booksCompleted < 13 || (remainingDeck.size() != 0 && (pcPlayerCard.size() != 0 || humanPlayerCard.size() != 0))) {
            letsPlayGoFish(currentPlayer, opponentPlayer);
            booksCompleted = this.humanPlayerBooks + this.pcPlayerBooks;

            if (pcPlayerCard != null && pcPlayerCard.size() == 0 && humanPlayerCard != null && humanPlayerCard.size() != 0) {
                System.out.println("PC Player ran out of cards, collect cards from deck ");
                takeCardsFromDeck(pcPlayer);
            } else if (pcPlayerCard != null && humanPlayerCard.size() == 0 && humanPlayerCard != null && pcPlayerCard.size() != 0) {
                System.out.println("You ran out of cards, collect cards from deck ");
                takeCardsFromDeck(humanPlayer);
            }
        }

        getWinner();
    }

    void letsPlayGoFish(Player hPlayer, Player pPlayer) {
        this.opponentPlayer = (pPlayer.getName().equals("PC") ? pcPlayer : humanPlayer);

        if (!hPlayer.getName().equals("PC")) {
            currentPlayerCards = humanPlayerCard;
        } else {
            currentPlayerCards = pcPlayerCard;
        }

        Card cardAsked = checkWithOpponent(hPlayer);

        goFish(hPlayer, cardAsked);

        this.opponentPlayer = hPlayer;
        this.currentPlayer = nextTurn(hPlayer);
        System.out.println("It's your turn now " + currentPlayer.getName());
    }


    /*
   @param - currentPlayer
   currentPlayer chooses a random card from his hand
*/
    Card selectCardFromHand(Player currentPlayer) {
        Card card = null;
        Random random = new Random();
        if (!currentPlayer.getName().equals("PC")) {

            if (humanPlayerCard.size() > 0) {
                System.out.println("The cards in you hand are : ");
                System.out.print(this.currentPlayerCards);
                System.out.println();
                System.out.println("Do you want to exit the game, Enter Y or N ");

                String quitString = new Scanner(System.in).next();
                if ("Y".equalsIgnoreCase(quitString)) {
                    quitGame();
                }
                System.out.println("Choose a card to ask the opponent");
                card = getUserSelectedCard(card);

                //return card;
            }
        } else {
            if (pcPlayerCard.size() > 0) {
                card = pcPlayerCard.get(random.nextInt(pcPlayerCard.size()));
            }
        }

        return card;
    }

    // User selects the card to ask
    private Card getUserSelectedCard(Card card) {
        Scanner scanner = new Scanner(System.in);
        int retryCount = 0;
        do {
            if (retryCount > 0) {
                System.out.println("Oops!! Choose a card from your hand");
            }

            System.out.println("Select the rank of the card ");
            String rankChose = scanner.next();

            System.out.println("Select the suite of the card ");
            String suiteChose = scanner.next();

            if (rankChose != null && suiteChose != null && rankChose.length() > 0 && suiteChose.length() > 0) {
                if (!Card.lookupRank(rankChose.toUpperCase())) {
                    System.out.println("Please enter a valid rank");
                    continue;
                }
                Card.Rank rank = Card.Rank.valueOf(rankChose.toUpperCase());

                if (!Card.lookupSuit(suiteChose.toUpperCase())) {
                    System.out.println("Please enter a valid suit");
                    continue;

                }

                Card.Suit suit = Card.Suit.valueOf(suiteChose.toUpperCase());
                card = new Card(suit, rank);
                retryCount++;
            }

        } while (!currentPlayerCards.contains(card));
        return card;
    }

    // Card is being asked to collect from the opponent
    Boolean askCard(Player opponentPlayer, Card.Rank rank) {
        Set<Map.Entry<Player, List<Card>>> setOfCards = playerHandMap.entrySet();

        for (Map.Entry<Player, List<Card>> entry : setOfCards)
            if (entry.getKey().getName().equals(opponentPlayer.getName())) {
                List<Card> cardList = entry.getValue();
                for (Card card : cardList) {
                    if (card.getRank().equals(rank)) {
                        System.out.println("The card " + card + " is available with user " + opponentPlayer.getName());
                        System.out.println("Fantastic!!, Grab all the cards from the user");
                        takeAllSameCards(currentPlayer, opponentPlayer, rank);
                        return true;
                    }
                }
            }
        return false;
    }

    //Check if the opponent has the card asked.
    private Card checkWithOpponent(Player hPlayer) {
        Card cardAsked = selectCardFromHand(hPlayer);
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

                System.out.println("Go Fish!!!");
                flag = false;
                break;
            }
            cardAsked = selectCardFromHand(hPlayer);
        } while (flag);

        return cardAsked;
    }

    //go Fish if the opponent does not have the card
    private void goFish(Player hPlayer, Card cardAsked) {

        Card drawnCard = drawTopCard();

        if (null != drawnCard) {
            boolean toContinue = false;
            do {
                if (!hPlayer.getName().equals("PC")) {
                    System.out.println(" The card drawn from the pond is " + drawnCard);
                    humanPlayerCard.add(drawnCard);
                    currentPlayerCards = humanPlayerCard;
                    if (isHumanBookComplete()) {
                        System.out.println("Awesome , you completed a book!");
                        humanPlayerBooks++;
                    }
                } else {
                    pcPlayerCard.add(drawnCard);
                    currentPlayerCards = pcPlayerCard;
                    if (isPcBookComplete()) {
                        System.out.println("Awesome , PC completed a book!");
                        pcPlayerBooks++;
                    }
                }

                if (remainingDeck.size() > 0) {
                    remainingDeck.remove(drawnCard);
                } else {
                    break;
                }

                if (null != cardAsked && cardAsked.getRank().equals(drawnCard.getRank())) {
                    drawnCard = drawTopCard();
                    toContinue = true;
                } else {
                    toContinue = false;
                }

            } while (null != cardAsked && toContinue);
        }

    }


    //All the cards of same rank are fished.
    List<Card> takeAllSameCards(Player currentPlayer, Player opponentPlayer, Card.Rank rank) {
        List<Card> opponentPlayerCards = playerHandMap.get(opponentPlayer);
        List<Card> currentPlayerCards = playerHandMap.get(currentPlayer);

        Iterator<Card> iterator = opponentPlayerCards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (rank.equals(card.getRank())) {
                currentPlayerCards.add(card);
                iterator.remove();
            }
        }
        return opponentPlayerCards;
    }

    Boolean isHumanBookComplete() {

        Map<Card.Rank, Integer> rankMap = new HashMap<>();
        for (Card card : humanPlayerCard) {
            if (null != card) {
                if (rankMap.containsKey(card.getRank())) {
                    Integer value = rankMap.get(card.getRank());
                    rankMap.put(card.getRank(), value + 1);
                } else {
                    rankMap.put(card.getRank(), 1);
                }
            }
        }

        Set<Card.Rank> rankSet = rankMap.keySet();
        Iterator<Card.Rank> iterator = rankSet.iterator();
        while (iterator.hasNext()) {
            Card.Rank rank = iterator.next();
            if (rankMap.get(rank) == 4) {
                System.out.println("The book is completed with the rank : " + rank);
                Iterator<Card> removeIterator = humanPlayerCard.iterator();
                while (removeIterator.hasNext()) {
                    Card next = removeIterator.next();
                    if (next.getRank().equals(rank)) {
                        removeIterator.remove();
                    }
                }
                return true;
            }
        }
        return false;
    }

    Boolean isPcBookComplete() {

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
                System.out.println("The book is completed with the rank : " + rank);
                Iterator<Card> removeIterator = pcPlayerCard.iterator();
                while (removeIterator.hasNext()) {
                    Card next = removeIterator.next();
                    if (next.getRank().equals(rank)) {
                        removeIterator.remove();
                    }
                }
                return true;
            }
        }
        return false;
    }

    Player nextTurn(Player currentPlayer) {
        if (currentPlayer.getName().equals("PC")) {
            currentPlayer = humanPlayer;
        } else {
            currentPlayer = pcPlayer;
        }
        return currentPlayer;
    }

    void takeCardsFromDeck(Player player) {
        if (!player.getName().equals("PC")) {
            if (remainingDeck.size() >= 7) {
                for (int i = 0; i < 7; i++) {
                    humanPlayerCard.add(remainingDeck.get(0));
                    remainingDeck.remove(0);
                }
            }
        } else {
            if (remainingDeck.size() >= 7) {
                for (int i = 0; i < 7; i++) {
                    pcPlayerCard.add(remainingDeck.get(0));
                    remainingDeck.remove(0);
                }
            }
        }
    }

    @Override
    public void getWinner() {
        System.out.println("Game Over!!!");
        if (humanPlayerBooks > pcPlayerBooks) {
            System.out.println("Your Score : " + humanPlayerBooks);
            System.out.println("pcPlayer Score : " + pcPlayerBooks);
            System.out.println("Congratulations, The winner is : " + humanPlayer.getName());
        } else {
            System.out.println("pcPlayer Score : " + pcPlayerBooks);
            System.out.println("Your Score : " + humanPlayerBooks);
            System.out.println("The winner is pcPlayer, Nice Play !!!");
        }
    }

    @Override
    public void resetGame() {
        startGame(humanPlayer);
    }

    @Override
    public void quitGame() {
        CasinoDriver casinoDriver = new CasinoDriver();
    }

    @Override
    public String getGameName() {
        return "Go Fish";
    }

}
