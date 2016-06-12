import java.util.*;

public class Model extends java.util.Observable
{
	private int dealCounter = 1; // increments each time a new hand is dealt
	public static final int CLUBS = 1;
	public static final int DIAMONDS = 2;
	public static final int HEARTS = 3;
	public static final int SPADES = 4;

	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 14;

	public static Hashtable suitMap = new Hashtable(); // maps ints 2-13 to the face cards
	public static Hashtable cardMap = new Hashtable(); // maps ints 1-4 to the suits

	private static final int MIN_PLAYERS = 1;
	private static final int MAX_PLAYERS = 10;

	private Deck deck;
	private ArrayList<Player> players;
	private StringBuffer handsDisplayText; // should use stringbuffer instead of string b/c strings are immutable
	private int numPlayers; // input variable

	public Model()
	{
		setPrintMaps();
		deck = new Deck();
		players = new ArrayList<>();
	}

	private void setPrintMaps()
	{
		suitMap.put(CLUBS, "Clubs");
		suitMap.put(DIAMONDS, "Diamonds");
		suitMap.put(HEARTS, "Hearts");
		suitMap.put(SPADES, "Spades");
		cardMap.put(2, "2");
		cardMap.put(3, "3");
		cardMap.put(4, "4");
		cardMap.put(5, "5");
		cardMap.put(6, "6");
		cardMap.put(7, "7");
		cardMap.put(8, "8");
		cardMap.put(9, "9");
		cardMap.put(10, "10");
		cardMap.put(JACK, "Jack");
		cardMap.put(KING, "Queen");
		cardMap.put(QUEEN, "King");
		cardMap.put(ACE, "Ace");
	}
	
	protected void incrementDealNumber()
	{
		++dealCounter;
	}

	private int getDealCounter()
	{
		return dealCounter;
	}

	public Deck getDeck()
	{
		return deck;
	}

	protected boolean dealCards(int numPlayers)
	{
		// Cycle through the players
		for(int cntr = 1; cntr <= numPlayers; cntr++)
		{
			// Cycle through each player's cards
			for(int cntr2 = 0; cntr2 < 5; cntr2++)
			{
				try
				{
					players.get(cntr - 1).addCard(deck.dealCard());
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	protected void generatePlayers(int numPlayers)
	{
		players = new ArrayList<>();

		for(int counter = 1; counter <= numPlayers; counter++)
		{
			players.add(new Player(counter));
		}
	}

	protected void printHands()
	{
		Iterator<Player> playerIterator = players.iterator();
		Player tmpPlayer;
		if (null == handsDisplayText) // putting null first in comparison is good practice as it avoids accidental assignment
		{
			handsDisplayText = new StringBuffer("Deal Number " + getDealCounter() + ":\n");
		}
		else
		{
			handsDisplayText.append("\n\n\nDeal Number " + getDealCounter() + ":\n");
		}

		while (playerIterator.hasNext())
		{
			tmpPlayer= playerIterator.next();
			handsDisplayText.append("\n");
			tmpPlayer.printName();
			tmpPlayer.printHand();
		}

		setChanged();
		notifyObservers(handsDisplayText); // update the view
	}

	protected boolean parseNumPlayers(String players)
	{
		boolean parseErr = false;
		Integer num = 0;

		try
		{
			num = Integer.parseInt(players);
			if(num < MIN_PLAYERS || num > MAX_PLAYERS)
			{
				parseErr = true;
			}
		}
		catch(Exception e)
		{
			parseErr = true;
		}

		if(!parseErr)
		{
			setNumPlayers(num);
		}
		return parseErr;
	}

	private void setNumPlayers(int num)
	{
		numPlayers = num;
	}

	protected int getNumPlayers()
	{
		return numPlayers;
	}



	// Supporting classes for the model
	protected class Deck extends Object
	{
		private ArrayList<Card> availableCards = new ArrayList<Card>();

		Deck()
		{
			// Generate a deck of cards
			for(int suitInc = 1; suitInc <= SPADES; suitInc++)
			{
				for(int valInc = 2; valInc <= ACE; valInc++)
				{
					availableCards.add(new Card(suitInc, valInc));
				}
			}
		}

		protected void shuffleCards()
		{
			// Randomize the deck for each deal
			long seed = System.nanoTime();
			deck = new Deck();
			Collections.shuffle(deck.availableCards, new Random(seed));
		}

		protected Card dealCard()
		{
			try
			{
				return deck.availableCards.remove(0);
			}
			catch(IndexOutOfBoundsException e)
			{
				e.printStackTrace();
				throw e;
			}
		}
	}

	private class Card extends Object
	{
		private int val;
		private int suit;

		Card(int suitIn, int valIn)
		{
			// Generate a cards
			val = valIn;
			suit = suitIn;
		}
	}

	private class Player extends Object
	{
		private int playerNumber;
		private String playerName;
		private ArrayList<Card> hand;
		Player(int playerNumberIn)
		{
			playerNumber = playerNumberIn;
			playerName = "Player " + Integer.toString(playerNumber);
			hand = new ArrayList<Card>();
		}

		// For future development where players have names
		Player(int playerNumberIn, String playerNameIn)
		{
			playerName = playerNameIn;
			playerNumber = playerNumberIn;
		}

		private void addCard(Card card)
		{
			hand.add(card);
		}

		private void printHand()
		{
			Card tmpCard;
			Iterator<Card> cardIterator = hand.iterator();
			while (cardIterator.hasNext())
			{
				tmpCard = cardIterator.next();
				handsDisplayText.append(cardMap.get(tmpCard.val) + " of " + suitMap.get(tmpCard.suit) + "\n");
			}
		}

		private void printName()
		{
			handsDisplayText.append(playerName + "\n");
		}
	}
}
