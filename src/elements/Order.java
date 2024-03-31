package elements;
public abstract class Order {
    int traderID;
    double amount,price;

    public Order(int traderID, double amount, double price) {
        this.traderID = traderID;
        this.amount = amount;
        this.price = price;
    }

    public int getTraderID() {
        return traderID;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
