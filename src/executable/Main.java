package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import elements.BuyingOrder;
import elements.Market;
import elements.SellingOrder;
import elements.Trader;
import elements.Wallet;

public class Main {
	public static Random myRandom;

	public static void main(String[] args) throws FileNotFoundException {

		ArrayList<Trader> traders = new ArrayList<>();
		File input = new File("C:\\Users\\User\\Desktop\\Yeni klasör (3)\\Marketplace\\src\\executable\\input_2.txt");
		File output = new File("C:\\Users\\User\\Desktop\\Yeni klasör (3)\\Marketplace\\src\\executable\\output_2.txt");
		Scanner in = new Scanner(input);
		PrintStream out = new PrintStream(output);

		int randomSeed = Integer.valueOf(in.next());
		myRandom = new Random(randomSeed);

		int numInvalidQuer = 0;
		int marketFee = Integer.valueOf(in.next());
		int numUsers = Integer.valueOf(in.next());
		int numQueries = Integer.valueOf(in.next());

		Market market = new Market(marketFee);

		for (int i = 0; i < numUsers; i++) {
			double dollarAmount = Double.valueOf(in.next());
			double coinAmount = Double.valueOf(in.next());
			Trader newTrader = new Trader(dollarAmount, coinAmount);
			traders.add(newTrader);
		}

		traders.get(0).getWallet().setCoins(Double.POSITIVE_INFINITY);
		traders.get(0).getWallet().setDollars(Double.POSITIVE_INFINITY);

		for (int i = 0; i <= numQueries; i++) {
			String line = in.nextLine();
			String[] linetokens = line.split(" ");

			int traderID, validationCheck;
			double amount, price;

			switch (linetokens[0]) {
				case "3":
					traderID = Integer.valueOf(linetokens[1]);
					amount = Double.valueOf(linetokens[2]);
					double newWalletDollars_3 = traders.get(traderID).getWallet().getDollars() + amount;
					traders.get(traderID).getWallet().setDollars(newWalletDollars_3);
					break;

				case "4":
					traderID = Integer.valueOf(linetokens[1]);
					amount = Double.valueOf(linetokens[2]);
					Wallet traderWallet_4 = traders.get(traderID).getWallet();
					if (amount <= traderWallet_4.getDollars()) {
						double newWalletDollars_4 = traderWallet_4.getDollars() - amount;
						traders.get(traderID).getWallet().setDollars(newWalletDollars_4);
					} else {
						numInvalidQuer++;
					}
					break;

				case "5":
					traderID = Integer.valueOf(linetokens[1]);
					Trader tempTrader_5 = traders.get(traderID);
					double dollars_5 = tempTrader_5.getWallet().getDollars();
					double coins_5 = tempTrader_5.getWallet().getCoins();
					String traderAssets_5 = String.format("Trader %d: %.5f$ %.5fPQ", traderID, dollars_5, coins_5);
					out.println(traderAssets_5);
					break;

				case "10":
					traderID = Integer.valueOf(linetokens[1]);
					price = Double.valueOf(linetokens[2]);
					amount = Double.valueOf(linetokens[3]);
					validationCheck = traders.get(traderID).buy(amount, price, market);
					numInvalidQuer += validationCheck;
					market.checkTransactions(traders);
					break;

				case "11":
					if (market.getSellingOrders().isEmpty()) {
						numInvalidQuer++;
					} else {
						traderID = Integer.valueOf(linetokens[1]);
						amount = Double.valueOf(linetokens[2]);
						price = market.getSellingOrders().peek().getPrice();
						Trader tempTrader = traders.get(traderID);
						validationCheck = tempTrader.buy(amount, price, market);
						numInvalidQuer += validationCheck;
						market.checkTransactions(traders);
					}
					break;

				case "20":
					traderID = Integer.valueOf(linetokens[1]);
					price = Double.valueOf(linetokens[2]);
					amount = Double.valueOf(linetokens[3]);
					validationCheck = traders.get(traderID).sell(amount, price, market);
					numInvalidQuer += validationCheck;
					market.checkTransactions(traders);
					break;

				case "21":
					if (market.getBuyingOrders().isEmpty()) {
						numInvalidQuer++;
					} else {
						traderID = Integer.valueOf(linetokens[1]);
						amount = Double.valueOf(linetokens[2]);
						price = market.getBuyingOrders().peek().getPrice();
						Trader tempTrader = traders.get(traderID);
						validationCheck = tempTrader.sell(amount, price, market);
						numInvalidQuer += validationCheck;
						market.checkTransactions(traders);
					}
					break;

				case "500":
					double currentDollarsMarketSize_500 = 0;
					double currentCoinsMarketSize_500 = 0;

					for (BuyingOrder order : market.getBuyingOrders()) {
						double orderDollars_500 = (order.getAmount() * order.getPrice());
						currentDollarsMarketSize_500 += orderDollars_500;
					}

					for (SellingOrder order : market.getSellingOrders()) {
						double orderCoins_500 = order.getAmount();
						currentCoinsMarketSize_500 += orderCoins_500;
					}

					String marketSize_500 = String.format("Current market size: %.5f %.5f", currentDollarsMarketSize_500,
							currentCoinsMarketSize_500);
					out.println(marketSize_500);
					break;

				case "501":
					String outline_501 = String.format("Number of successful transactions: %d",
							Market.numberOfSuccessTransactions);
					out.println(outline_501);
					break;

				case "502":
					out.println("Number of invalid queries: " + numInvalidQuer);
					break;

				case "505":
					double averageCurrentPrice_505 = 0;

					if (market.getBuyingOrders().isEmpty() && market.getSellingOrders().isEmpty()) {
						averageCurrentPrice_505 = 0.0;
					} else if (market.getBuyingOrders().isEmpty()) {
						averageCurrentPrice_505 = market.getSellingOrders().peek().getPrice();
					} else if (market.getSellingOrders().isEmpty()) {
						averageCurrentPrice_505 = market.getBuyingOrders().peek().getPrice();
					} else {
						double currentSellPrice_505 = market.getSellingOrders().peek().getPrice();
						double currentBuyPrice_505 = market.getBuyingOrders().peek().getPrice();
						averageCurrentPrice_505 = (currentBuyPrice_505 + currentSellPrice_505) / 2;
					}

					String averagePrices_505 = String.format("Current prices: %.5f %.5f %.5f",
							market.getBuyingOrders().isEmpty() ? 0.0 : market.getBuyingOrders().peek().getPrice(),
							market.getSellingOrders().isEmpty() ? 0.0 : market.getSellingOrders().peek().getPrice(),
							averageCurrentPrice_505);
					out.println(averagePrices_505);
					break;

				case "555":
					for (int k = 0; k < traders.size(); k++) {
						Wallet traderWallet_555 = traders.get(k).getWallet();
						double traderDollars_555 = traderWallet_555.getDollars();
						double traderBlockedDollars_555 = traderWallet_555.getBlockedDollars();
						double traderCoins_555 = traderWallet_555.getCoins();
						double traderBlockedCoins_555 = traderWallet_555.getBlockedCoins();
						double totalDollars_555 = traderDollars_555 + traderBlockedDollars_555;
						double totalCoins_555 = traderCoins_555 + traderBlockedCoins_555;
						String traderInfo_555 = String.format("Trader %d: %.5f$ %.5fPQ", k, totalDollars_555,
								totalCoins_555);
						out.println(traderInfo_555);
					}
					break;

				case "666":
					double openMarketOperationPrice_666 = Double.valueOf(linetokens[1]);
					market.makeOpenMarketOperation(openMarketOperationPrice_666, traders);
					break;

				case "777":
					for (Trader trader : traders) {
						double newCoins_777 = trader.getWallet().getCoins() + myRandom.nextDouble() * 10;
						trader.getWallet().setCoins(newCoins_777);
					}
					break;
			}
		}
	}
}
