package elements;
public class Wallet {
    private double dollars;
    private double coins;
    private double blockedDollars;
    private double blockedCoins;

    public Wallet(double dollars, double coins) {
        this.dollars = dollars;
        this.coins = coins;
        this.blockedDollars = 0.0;
        this.blockedCoins = 0.0;
    }

    public void blockDollars(double amount) {
        blockedDollars += amount;
        dollars -= amount;
    }

    public void blockCoins(double amount) {
        blockedCoins += amount;
        coins -= amount;
    }

    public double getDollars() {
        return (dollars == Double.POSITIVE_INFINITY) ? 0.0 : dollars;
    }

    public void setDollars(double dollars) {
		this.dollars = dollars;
	}

    public double getCoins() {
        return (coins == Double.POSITIVE_INFINITY) ? 0.0 : coins;
    }

    public void setCoins(double coins) {
		this.coins = coins;
	}

    public double getBlockedDollars() {
        return blockedDollars;
    }

    public void setBlockedDollars(double blockedDollars) {
		this.blockedDollars = blockedDollars;
	}

    public double getBlockedCoins() {
        return blockedCoins;
    }

    public void setBlockedCoins(double blockedCoins) {
		this.blockedCoins = blockedCoins;
	}
}
