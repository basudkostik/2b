package elements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Market {
	private PriorityQueue<SellingOrder> sellingOrders;
	private PriorityQueue<BuyingOrder> buyingOrders;
	private int fee;
	public static int numberOfSuccessTransactions = 0;

	public Market(int fee) {
		this.fee = fee;
		this.buyingOrders = new PriorityQueue<>(Comparator.reverseOrder());
		this.sellingOrders = new PriorityQueue<>();
	}

	public void giveSellOrder(SellingOrder order) {
		sellingOrders.add(order);
	}

	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.add(order);
	}

	public void makeOpenMarketOperation(double price, List<Trader> traders) {
		processOrders(price, buyingOrders, sellingOrders, traders);
		processOrders(price, sellingOrders, buyingOrders, traders);
	}

	private void processOrders(double price, PriorityQueue<? extends Order> sourceOrders,
							   PriorityQueue<? extends Order> targetOrders, List<Trader> traders) {
		while (!sourceOrders.isEmpty() && sourceOrders.peek().getPrice() >= price) {
			Order sourceOrder = sourceOrders.poll();
			Order newOrder = sourceOrder instanceof BuyingOrder ?
					new SellingOrder(0, sourceOrder.getAmount(), sourceOrder.getPrice()) :
					new BuyingOrder(0, sourceOrder.getAmount(), sourceOrder.getPrice());
			ArrayList<Order> targetOrder = new ArrayList<>();
			targetOrder.add(newOrder);
			checkTransactions(traders);
		}
	}

	public void checkTransactions(List<Trader> traders) {
		while (!buyingOrders.isEmpty() && !sellingOrders.isEmpty() &&
				buyingOrders.peek().getPrice() >= sellingOrders.peek().getPrice()) {
			BuyingOrder buyerOrder = buyingOrders.poll();
			SellingOrder sellerOrder = sellingOrders.poll();
			performTransaction(buyerOrder, sellerOrder, traders);
		}
	}

	private void performTransaction(BuyingOrder buyerOrder, SellingOrder sellerOrder, List<Trader> traders) {
		double currentPrice = sellerOrder.getPrice();
		double transactionAmount = Math.min(buyerOrder.getAmount(), sellerOrder.getAmount());

		Wallet buyerWallet = traders.get(buyerOrder.getTraderID()).getWallet();
		Wallet sellerWallet = traders.get(sellerOrder.getTraderID()).getWallet();

		double transactionDollars = currentPrice * transactionAmount * (1 - (double) fee / 1000);

		buyerWallet.setBlockedDollars(buyerWallet.getBlockedDollars() - (currentPrice * transactionAmount));
		buyerWallet.setCoins(buyerWallet.getCoins() + transactionAmount);

		sellerWallet.setBlockedCoins(sellerWallet.getBlockedCoins() - transactionAmount);
		sellerWallet.setDollars(sellerWallet.getDollars() + transactionDollars);

		numberOfSuccessTransactions++;
	}

	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}

	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}
}
