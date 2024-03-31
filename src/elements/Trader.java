package elements;

public class Trader {
    private final int id;
    private final Wallet wallet;
    public static int numberOfUsers = 0;

    public Trader(double dollars, double coins) {
        this.wallet = new Wallet(dollars, coins);
        this.id = numberOfUsers++;
    }


    public int sell(double amount, double price, Market market) {
        if (hasSufficientCoins(amount)) {
            int traderID = this.getId();
            SellingOrder order = new SellingOrder(traderID, amount, price);
            market.giveSellOrder(order);

            updateWalletOnSell(amount);
            return 0;
        }
        return 1;
    }

    public int buy(double amount, double price, Market market) {
        double totalCost = amount * price;
        if (hasSufficientDollars(totalCost)) {
            int traderID = this.getId();
            BuyingOrder order = new BuyingOrder(traderID, amount, price);
            market.giveBuyOrder(order);

            updateWalletOnBuy(amount, totalCost);
            return 0;
        }
        return 1;
    }
    private boolean hasSufficientCoins(double amount) {
        return this.wallet.getCoins() >= amount;
    }

    private boolean hasSufficientDollars(double totalCost) {
        return this.wallet.getDollars() >= totalCost;
    }

    private void updateWalletOnSell(double amount) {
        this.wallet.setCoins(this.wallet.getCoins() - amount);
        this.wallet.setBlockedCoins(this.wallet.getBlockedCoins() + amount);
    }

    private void updateWalletOnBuy(double amount, double totalCost) {
        this.wallet.setDollars(this.wallet.getDollars() - totalCost);
        this.wallet.setBlockedDollars(this.wallet.getBlockedDollars() + totalCost);
    }


    public int getId() {
        return id;
    }

    public Wallet getWallet() {
        return this.wallet;
    }
}
